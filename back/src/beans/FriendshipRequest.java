package beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import javaxt.utils.Date;

public class FriendshipRequest {
    private User sender;
    private User receiver;
    private RequestState state;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date date;

    public FriendshipRequest() {
    }

    public FriendshipRequest(User sender, User receiver, RequestState state, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.state = state;
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
