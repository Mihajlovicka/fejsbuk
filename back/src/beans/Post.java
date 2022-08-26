package beans;

import java.util.ArrayList;

public class Post {
    private String picture;
    private String description;
    private ArrayList<Comment> comments;
    private boolean deleted = false;

    public Post() {
    }

    public Post(String picture, String description, ArrayList<Comment> comments) {
        this.picture = picture;
        this.description = description;
        this.comments = comments;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean equals(Post post) {
        return this.picture.equals(post.getPicture()) &&
                this.description.equals(post.getDescription()) &&
                this.deleted == post.isDeleted();
    }
}
