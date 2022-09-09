package repo;

import beans.FriendshipRequest;
import beans.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import beans.User;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UsersRepo {
    private static ArrayList<User> users = new ArrayList<>();
    private final String path = "./files/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public UsersRepo(){
        readAll();
    }

    public ArrayList<User> getAll(){
        return this.users;
    }

    public void readAll(){
        try {
            users = objectMapper.readValue(new File(path), new TypeReference<ArrayList<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewUser(User user){

        users.add(user);
        saveAll();

    }

    public void saveAll(){
        makeFileIfNotExists(this.path);
        try {
            objectMapper.writeValue(new File(path), users);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public User getByUsername(String username){

        for(User u:users){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public User updateUser(User user){
        for (User u : users){
            if(u.getUsername().equals(user.getUsername())){
                u.setName(user.getName());
                u.setSurname(user.getSurname());
                u.setEmail(user.getEmail());
                u.setBirthDate( user.getBirthDate());
                u.setSex(user.getSex());
                u.setProfilePrivate(user.isProfilePrivate());
                saveAll();
                return u;
            }
        }
        return null;
    }

    public void addUser(User user){
        this.users.add(user);
        saveAll();
    }

    public static void makeFileIfNotExists(String path){
        File myObj = new File(path);
        try {
            if(!myObj.exists())
                myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDirectoryIfNotExists(String path){
        File myObj = new File(path);
        if(!myObj.exists())
            myObj.mkdir();
    }

    public void changePassword(String username, String new_password) {
        for (User u : users){
            if(u.getUsername().equals(username)){
                u.setPassword(new_password);
                saveAll();
                return;
            }
        }
    }

    public void changeProfilePhoto(String username, String picture) {
        for (User u : users){
            if(u.getUsername().equals(username)){
                u.setProfilePicture(picture);
                saveAll();
                return;
            }
        }
    }

    public void addFriendRequest(FriendshipRequest request) {
        for (User u : users){
            if(u.getUsername().equals(request.getSender().getUsername())) {
                u.addRequest(request.getReceiver().getUsername());
            }

        }
        saveAll();
        return;
    }

    public void removeFriendRequest(FriendshipRequest request) {
        for (User u : users) {
            if (u.getUsername().equals(request.getSender().getUsername())) {
                u.removeRequest(request.getReceiver().getUsername());
            }
        }
        saveAll();
    }


    public void acceptFriendRequest(FriendshipRequest request) {
        for (User u : users) {
            if (u.getUsername().equals(request.getSender().getUsername())) {
                u.removeRequest(request.getReceiver().getUsername());
                u.addFriend(request.getReceiver().getUsername());
            }

        }
        for (User u : users) {
            if (u.getUsername().equals(request.getReceiver().getUsername())) {

                u.addFriend(request.getSender().getUsername());
            }

        }
        saveAll();
        return;
    }

    public void rejectFriendRequest(FriendshipRequest request) {
        for (User u : users) {
            if (u.getUsername().equals(request.getSender().getUsername())) {
                u.removeRequest(request.getReceiver().getUsername());

            }

        }
        saveAll();
        return;
    }

    public void removeFriend(User loggedUser, User u) {
        for (User user : users) {
            if (user.getUsername().equals(u.getUsername())) {
                user.removeFriend(loggedUser.getUsername());
            }
            if (user.getUsername().equals(loggedUser.getUsername())) {
                user.removeFriend(u.getUsername());
            }
        }
        saveAll();
    }

    public User blockUser(User u) {
        User blockedUser = null;
        for(User user : users){
            if(user.getUsername().equals(u.getUsername())){
                user.setBlocked(true);
                blockedUser = user;
            }
        }
        saveAll();
        return blockedUser;
    }

    public User unblockUser(User u) {
        User blockedUser = null;
        for(User user : users){
            if(user.getUsername().equals(u.getUsername())){
                user.setBlocked(false);
                blockedUser = user;
            }
        }
        saveAll();
        return blockedUser;
    }
}
