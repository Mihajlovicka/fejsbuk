package repo;

import beans.FriendshipRequest;
import beans.Post;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import beans.User;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UsersRepo {
    private static ArrayList<User> users = new ArrayList<>();
    private final String path = "./webprog2022/files/users.json";

    public ArrayList<User> getAll(){
        return this.users;
    }

    public void readAll(){
        try {
            String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            users = new Gson().fromJson(text, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(){
        makeFileIfNotExists();
        try {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setDateFormat(DateFormat.FULL).create();
            String text = gson.toJson(users);
            FileWriter f = new FileWriter(path);
            f.write(text);
            f.flush();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void init(){
        users.clear();
        User u = new User("msara","123","saram","sara","mihajlovic",new java.util.Date(),"z","admin","",new ArrayList<Post>(),new ArrayList<String>(),new ArrayList<FriendshipRequest>(),new ArrayList<String>(), true);
        users.add(u);
        User u1 = new User("saram@gmail.com","123","saram","sara","mihajlovic",new Date(),"z","admin","",new ArrayList<Post>(),new ArrayList<String>(),new ArrayList<FriendshipRequest>(),new ArrayList<String>(), true);
        users.add(u1);
    }

    public User getByUsername(String username){

        for(User u:users){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public void addUser(User user){
        this.users.add(user);
        saveAll();
    }

    private void makeFileIfNotExists(){
        File myObj = new File(path);
        try {
            if(!myObj.exists())
                myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
