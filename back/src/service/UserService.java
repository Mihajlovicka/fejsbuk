package service;

import beans.FriendshipRequest;
import beans.RequestState;
import beans.User;
import exceptions.NotFound;
import exceptions.WrongPassword;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repo.FriendshipRequestsRepo;
import repo.UsersRepo;
import spark.utils.IOUtils;

import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserService {
    private static UsersRepo usersRepo = new UsersRepo();
    private static FriendshipRequestsRepo friendshipRequestsRepo = new FriendshipRequestsRepo();
    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public User getLoggedInUser(String auth) throws NotFound , Exception{
        System.out.println("Authorization: " + auth);
        if ((auth != null) && (auth.contains("Bearer "))) {
            String jwt = auth.substring(auth.indexOf("Bearer ") + 7);

            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            // ako nije bacio izuzetak, onda je OK
            System.out.println(claims.getBody().getSubject());
            return getUser(claims.getBody().getSubject());

        }
        throw new NotFound("no token");
    }

    public User registerUser(User user){
        if(usersRepo.getByUsername(user.getUsername()) != null){
            return null;
        }
        user.setRole("user");
        user.setProfilePicture("no_image.jpg");
        String jws = Jwts.builder().setSubject(user.getUsername()).setExpiration(new Date(new Date().getTime() + 1000*60*60*5L)).setIssuedAt(new java.util.Date()).signWith(key).compact();
        user.setToken(jws);
        usersRepo.addNewUser(user);
        UsersRepo.makeDirectoryIfNotExists("./front/src/assets/pictures/" + user.getUsername());
        return user;
    }
    public User login(Map<String, String> userData) throws NotFound, WrongPassword {
        User u = usersRepo.getByUsername(userData.get("username"));
        if(u == null) throw new NotFound("user not found");
        if(!u.getPassword().equals(userData.get("password"))) throw new WrongPassword("password wrong");
        String jws = Jwts.builder().setSubject(u.getUsername()).setExpiration(new Date(new Date().getTime() + 1000*60*60*5L)).setIssuedAt(new java.util.Date()).signWith(key).compact();
        u.setToken(jws);
        return u;
    }

    public ArrayList<User> getAll(){
        return usersRepo.getAll();
    }

    public ArrayList<User> search(String startDate, String endDate, String search) throws ParseException {

        if(search.equals("") && (startDate.equals("") || endDate.equals(""))) { return usersRepo.getAll(); }
        if(search.equals("")) {
            Date start = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
            Date end = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
            return searchDate(start, end);
        }
        if(startDate.equals("") || endDate.equals("")){
            return searchName(search);
        }
        Date start = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        Date end = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
        return searchAll(start, end, search);
    }

    private ArrayList<User> searchAll(Date start, Date end, String search) {
        ArrayList<User> found = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for(User u: searchName(search)){
            if((u.getBirthDate().after(start) && u.getBirthDate().before(end)) ||
                    (sdf.format(u.getBirthDate()).equals(sdf.format(start)) || sdf.format(u.getBirthDate()).equals(sdf.format(end)) )){
                found.add(u);
            }
        }
        return found;
    }

    private ArrayList<User> searchName(String search) {
        ArrayList<User> found = new ArrayList<>();
        List<String> searchWords = new ArrayList<>();
        if(search.contains(" ")){
            searchWords = List.of(search.split("\\s+"));
        }
        else{
            searchWords.add(search.toLowerCase());
        }
        for(User u: usersRepo.getAll()){
            searchWords.forEach(el -> {
                if(u.getName().toLowerCase().contains(el) || el.contains(u.getName().toLowerCase())||
                        u.getSurname().toLowerCase().contains(el) || el.contains(u.getSurname().toLowerCase())){
                    found.add(u);
                }
            });
        }
        return found;
    }

    private ArrayList<User> searchDate(Date start, Date end) {
        ArrayList<User> found = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for(User u: usersRepo.getAll()){
            if((u.getBirthDate().after(start) && u.getBirthDate().before(end)) ||
                    (sdf.format(u.getBirthDate()).equals(sdf.format(start)) || sdf.format(u.getBirthDate()).equals(sdf.format(end)) )){
                found.add(u);
            }
        }
        return found;
    }

    public User getUser(String username) throws NotFound {
        User u = usersRepo.getByUsername(username);
        if(u == null) throw new NotFound("user not found");
        return u;
    }

    public User updateUser(User user, String auth) throws Exception {
        User u = getLoggedInUser(auth);
        if(u == null) throw new NotFound("token not valid");
        u =  usersRepo.updateUser(user);
        return u;
    }

    public void changePassword(Map<String, String> userData, String auth) throws NotFound, WrongPassword , Exception{
        User u = getLoggedInUser(auth);
        if(u == null) throw new NotFound("token not valid");
        if(!u.getPassword().equals(userData.get("old_password"))) throw new WrongPassword("password wrong");
        usersRepo.changePassword(u.getUsername(), userData.get("new_password"));
    }

    public void changeProfilePhoto(String picture, String auth) throws Exception {
        User u = getLoggedInUser(auth);
        if(u == null) throw new NotFound("token not valid");
        usersRepo.changeProfilePhoto(u.getUsername(), picture);
    }


    public void removeProfilePhoto(String auth) throws Exception {
        User u = getLoggedInUser(auth);
        if(u == null) throw new NotFound("token not valid");
        usersRepo.changeProfilePhoto(u.getUsername(), "no_image.jpg");
    }

    public User addFriend(User u, String auth) throws Exception {
        User sender = getLoggedInUser(auth);
        if(sender == null) throw new NotFound("token not valid");
        User reciever = getUser(u.getUsername());
        if(reciever == null) throw new NotFound("User doesnt exist");
        FriendshipRequest request = new FriendshipRequest();
        request.setSender(sender);
        request.setReceiver(reciever);
        request.setState(RequestState.OnHold);
        request.setDate(new java.util.Date());
        friendshipRequestsRepo.addNewRequest(request);
        usersRepo.addFriendRequest(request);
        return getUser(sender.getUsername());
    }

    public User cancelFriendRequest(User u, String auth) throws Exception {
        User sender = getLoggedInUser(auth);
        if(sender == null) throw new NotFound("token not valid");
        User reciever = getUser(u.getUsername());
        if(reciever == null) throw new NotFound("User doesnt exist");
        FriendshipRequest request = new FriendshipRequest();
        request.setSender(sender);
        request.setReceiver(reciever);
        request.setState(RequestState.OnHold);
        request.setDate(new java.util.Date());
        friendshipRequestsRepo.removeRequest(request);
        usersRepo.removeFriendRequest(request);
        return getUser(sender.getUsername());
    }

    public ArrayList<User> getRecievedFriendRequests(String auth)  throws Exception  {
        User loggedUser = this.getLoggedInUser(auth);
        ArrayList<User> foundUsers = new ArrayList<>();
        if (loggedUser==null)
        {
            return  null;
        }
        ArrayList<FriendshipRequest> recievedrequests = friendshipRequestsRepo.getRecievedRequestForUser(loggedUser.getUsername());
        for( FriendshipRequest req : recievedrequests){
            foundUsers.add(req.getSender());
        }
        return foundUsers;
    }

    public User acceptFriend(User u, String auth) throws Exception {
        User reciever = getLoggedInUser(auth);
        if(reciever == null) throw new NotFound("token not valid");
        User sender = getUser(u.getUsername());
        if(sender == null) throw new NotFound("User doesnt exist");
        FriendshipRequest request = new FriendshipRequest();
        request.setSender(sender);
        request.setReceiver(reciever);
        request.setState(RequestState.OnHold);
        request.setDate(new java.util.Date());
        friendshipRequestsRepo.acceptRequest(request);
        usersRepo.acceptFriendRequest(request);
        return getUser(reciever.getUsername());
    }

    public User rejectFriend(User u, String auth) throws Exception {
        User reciever = getLoggedInUser(auth);
        if(reciever == null) throw new NotFound("token not valid");
        User sender = getUser(u.getUsername());
        if(sender == null) throw new NotFound("User doesnt exist");
        FriendshipRequest request = new FriendshipRequest();
        request.setSender(sender);
        request.setReceiver(reciever);
        request.setState(RequestState.OnHold);
        request.setDate(new java.util.Date());
        friendshipRequestsRepo.rejectRequest(request);
        usersRepo.rejectFriendRequest(request);
        return getUser(reciever.getUsername());
    }

    public ArrayList<User> getFriends(String username) throws Exception{
        User user = this.getUser(username);
        ArrayList<User> foundUsers = new ArrayList<>();
        for(String friend : user.getFriendships()) {
            for (User u : usersRepo.getAll()) {
                if (u.getUsername().equals(friend)){
                    foundUsers.add(u);
                }
            }
        }
        return foundUsers;
    }

    public User removeFriend(User u, String auth) throws Exception {
        User loggedUser = this.getLoggedInUser(auth);

        usersRepo.removeFriend(loggedUser, u);
        return loggedUser;
    }
}
