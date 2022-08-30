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
    public void init(){
        users.clear();
        User u = new User("msara","123","saram","sara","mihajlovic",new Date(),"z","admin","",new ArrayList<Post>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(), true);
        users.add(u);
        User u1 = new User("saram@gmail.com","123","saram","mira","mihajlovic",new Date(),"z","admin","",new ArrayList<Post>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(), true);
        users.add(u1);
        User u2 = new User("saram@gmail.com","123","saram","nika","mihajlovic",new Date(),"z","admin","",new ArrayList<Post>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(), true);
        users.add(u2);
        User u3 = new User("saram@gmail.com","123","dajana","dajana","mihajlovic",new Date(),"z","admin","",new ArrayList<Post>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(), true);
        users.add(u3);
        User u4 = new User("saram@gmail.com","123","dajana","dajana","ristic",new Date(),"z","admin","",new ArrayList<Post>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(), true);
        users.add(u4);
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
}
