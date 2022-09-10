package repo;

import beans.Message;
import beans.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Authentication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagesRepo {
    private static Map<String,ArrayList<Message>> messages = new HashMap<>();
    private final String path = "./files/messages.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public MessagesRepo() {
        readAll();
    }

    public Map<String,ArrayList<Message>> getAll() {
        return this.messages;
    }

    public void readAll() {
        try {
            messages = objectMapper.readValue(new File(path), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll() {
        UsersRepo.makeFileIfNotExists(this.path);
        try {
            objectMapper.writeValue(new File(path), messages);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Message> getByUsername(String username) {
        if(messages.containsKey(username)) return messages.get(username);
        return new ArrayList<>();
    }

    public void addMessage(String sender, Message message) {
        if(messages.containsKey(sender)){
            messages.get(sender).add(message);
        }
        else{
            ArrayList<Message> msg = new ArrayList<>();
            msg.add(message);
            messages.put(sender, msg);
        }
        saveAll();
    }

}
