package beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Comment {
    private User username;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date publishedDate;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date changeDate;
    private String text;
    private int id;
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment() {
    }

    public Comment(User username, Date publishedDate, String text) {
        this.username = username;
        this.publishedDate = publishedDate;
        this.text =text;
    }

    public Comment(User username, Date publishedDate, Date changeDate, String text, int id, boolean deleted) {
        this.username = username;
        this.publishedDate = publishedDate;
        this.changeDate = changeDate;
        this.text = text;
        this.id = id;
        this.deleted = deleted;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}
