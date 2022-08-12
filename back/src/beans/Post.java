package beans;

import java.util.ArrayList;

public class Post {
    private User user;
    private String picture;
    private String description;
    private ArrayList<Comment> comments;

    public Post() {
    }

    public Post(User user,String picture, String description, ArrayList<Comment> comments) {
        this.user = user;
        this.picture = picture;
        this.description = description;
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
