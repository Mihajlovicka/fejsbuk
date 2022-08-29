package beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import java.util.ArrayList;

public class User {

     private String username;
     private String password;
     private String email;
     private String name;
     private String surname;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
     private Date birthDate;
      private String sex;
     private String role;
      private String profilePicture; //putanja
    private ArrayList<Post> posts;//ne znam sta ce ovo biti koji tip
     private ArrayList<String> pictures;
    private ArrayList<String> friendshipRequests;//lista usernema
     private ArrayList<String> friendships;
     private boolean profilePrivate;
    private String token;

    public User() {
    }

    public User(String username, String password, String email, String name, String surname, Date birthDate, String sex, String role, String profilePicture, ArrayList<Post> posts, ArrayList<String> pictures, ArrayList<String> friendshipRequests, ArrayList<String> friendships, boolean profilePrivate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.role = role;
        this.profilePicture = profilePicture;
        this.pictures = pictures;
        this.friendshipRequests = friendshipRequests;
        this.friendships = friendships;
        this.posts = posts;
        this.profilePrivate = profilePrivate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public ArrayList<String> getFriendshipRequests() {
        return friendshipRequests;
    }

    public void setFriendshipRequests(ArrayList<String> friendshipRequests) {
        this.friendshipRequests = friendshipRequests;
    }

    public ArrayList<String> getFriendships() {
        return friendships;
    }

    public void setFriendships(ArrayList<String> friendships) {
        this.friendships = friendships;
    }

    public boolean isProfilePrivate() {
        return profilePrivate;
    }

    public void setProfilePrivate(boolean profilePrivate) {
        this.profilePrivate = profilePrivate;
    }

    public void addRequest(String username){
        if(this.friendshipRequests == null)
            this.friendshipRequests = new ArrayList<String>();
        this.friendshipRequests.add(username);
    }
}
