package beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    public Message() {
    }

    public Message(String sender, String receiver, String content, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
