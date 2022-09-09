package repo;

import beans.FriendshipRequest;
import beans.RequestState;
import beans.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FriendshipRequestsRepo {
    private static ArrayList<FriendshipRequest> requests = new ArrayList<>();
    private final String path = "./files/requests.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    public FriendshipRequestsRepo(){

        readAll();
    }

    public ArrayList<FriendshipRequest> getAll(){
        return this.requests;
    }

    public void readAll(){
        try {
            requests = objectMapper.readValue(new File(path), new TypeReference<ArrayList<FriendshipRequest>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewRequest(FriendshipRequest request){

        requests.add(request);
        saveAll();

    }

    public void saveAll(){
        makeFileIfNotExists(this.path);
        try {
            objectMapper.writeValue(new File(path), requests);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void makeFileIfNotExists(String path){
        File myObj = new File(path);
        try {
            if(!myObj.exists())
                myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDirectoryIfNotExists(String path){
        File myObj = new File(path);
        if(!myObj.exists())
            myObj.mkdir();
    }

    public void removeRequest(FriendshipRequest request) {

        for(FriendshipRequest req : requests){
            if(req.getSender().getUsername().equals(request.getSender().getUsername()) &&
                    req.getState() == RequestState.OnHold &&
                    req.getReceiver().getUsername().equals(request.getReceiver().getUsername()))
                requests.remove(req);
            break;
        }
        saveAll();
    }

    public ArrayList<FriendshipRequest> getRecievedRequestForUser(String username) {
        ArrayList<FriendshipRequest> foundRequests = new ArrayList<>();
        for(FriendshipRequest req : requests){
            if(req.getReceiver().getUsername().equals(username) && req.getState()==RequestState.OnHold)
                foundRequests.add(req);
        }
        return foundRequests;
    }

    public void acceptRequest(FriendshipRequest request) {
        for(FriendshipRequest req : requests){
            if(req.getSender().getUsername().equals(request.getSender().getUsername()) &&
                    req.getReceiver().getUsername().equals(request.getReceiver().getUsername()))
                req.setState(RequestState.Accepted);
        }
        saveAll();
    }
    public void rejectRequest(FriendshipRequest request) {
        for(FriendshipRequest req : requests){
            if(req.getSender().getUsername().equals(request.getSender().getUsername()) &&
                    req.getReceiver().getUsername().equals(request.getReceiver().getUsername()))
                req.setState(RequestState.Rejected);
        }
        saveAll();
    }
}
