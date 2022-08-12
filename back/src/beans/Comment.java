package beans;

import javaxt.utils.Date;

public class Comment {
    private User username;
    private Date publishedDate;
    private Date changeDate;

    public Comment() {
    }

    public Comment(User username, Date publishedDate, Date changeDate) {
        this.username = username;
        this.publishedDate = publishedDate;
        this.changeDate = changeDate;
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
