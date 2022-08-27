package service;

import beans.User;
import exceptions.NotFound;
import exceptions.WrongPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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

    public User updateUser(User user) throws NotFound {
        User u =  usersRepo.updateUser(user);
        if(u == null) throw new NotFound("user not found");
        return u;
    }

    public void changePassword(Map<String, String> userData) throws NotFound, WrongPassword {
        User u = usersRepo.getByUsername(userData.get("username"));
        if(u == null) throw new NotFound("user not found");
        if(!u.getPassword().equals(userData.get("old_password"))) throw new WrongPassword("password wrong");
        usersRepo.changePassword(u.getUsername(), userData.get("new_password"));
    }

    public void changeProfilePhoto(Map<String, String> userData) throws NotFound {
        User u = usersRepo.getByUsername(userData.get("username"));
        if(u == null) throw new NotFound("user not found");
        usersRepo.changeProfilePhoto(u.getUsername(), userData.get("picture"));
    }


    public void removeProfilePhoto(String username) throws NotFound {
        User u = usersRepo.getByUsername(username);
        if(u == null) throw new NotFound("user not found");
        usersRepo.changeProfilePhoto(u.getUsername(), "no_image.jpg");
    }
}
