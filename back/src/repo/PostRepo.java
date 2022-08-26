package repo;

import beans.FriendshipRequest;
import beans.Post;
import beans.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostRepo {
    private Map<String, ArrayList<Post>> posts = new HashMap<>();
    private final String path = "./files/posts.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public PostRepo(){
        readAll();
    }

    public Map<String, ArrayList<Post>> getAll(){
        return this.posts;
    }

    public void readAll(){
        try {
            posts = objectMapper.readValue(new File(path), new TypeReference<Map<String, ArrayList<Post>>>(){});
            for(String username: posts.keySet()){
                ArrayList<Post> p = posts.get(username);
                for(int i = 0; i < p.size(); i++){
                    if(p.get(i).isDeleted()){
                        posts.get(username).remove(i);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(){
        UsersRepo.makeFileIfNotExists(this.path);
        try {
            objectMapper.writeValue(new File(path), posts);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Post> getByUsername(String username){
        if(this.posts.containsKey(username)){
            return this.posts.get(username);
        }
        return new ArrayList<>();
    }

    public void addPost(Post post, String username){
        ArrayList<Post> old;
        if(this.posts.containsKey(username)){
            old = this.posts.get(username);
        }
        else{
            old = new ArrayList<>();
        }
        old.add(post);
        this.posts.put(username, old);
        saveAll();
    }


    public void deletePost(String username, Post post) {
        if(this.posts.containsKey(username)){
            ArrayList<Post> p = this.posts.get(username);
            for(int i = 0; i < p.size(); i++){
                if(p.get(i).equals(post)){
                    post.setDeleted(true);
                    post.setComments(p.get(i).getComments());
                    p.remove(i);
                    p.add(post);
                    this.posts.put(username, p);
                }
            }
        }
        saveAll();
        ArrayList<Post> p = posts.get(username);
        for(int i = 0; i < p.size(); i++){
            if(p.get(i).isDeleted()){
                posts.get(username).remove(i);
            }
        }

    }
}
