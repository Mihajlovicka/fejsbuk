package beans;

import java.util.ArrayList;

public class PostDTO {
    private String username;
    private String nameSurname;
    private String profilePic;
    private String id;
    private String picture;
    private String description;
    private ArrayList<Comment> comments;

    public PostDTO() {
    }

    public PostDTO(String username, String nameSurname, String profilePic, String id, String picture, String description, ArrayList<Comment> comments) {
        this.username = username;
        this.nameSurname = nameSurname;
        this.profilePic = profilePic;
        this.id = id;
        this.picture = picture;
        this.description = description;
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
