package service;

import beans.Post;
import beans.User;
import exceptions.NotFound;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repo.PostRepo;
import repo.UsersRepo;
import spark.utils.IOUtils;

import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.util.ArrayList;

public class PostService {
    private static PostRepo postRepo = new PostRepo();
    private static UserService userService = new UserService();

    public void newPost(Part filePart, String text, String auth) throws IOException, NotFound, Exception {
        User u = userService.getLoggedInUser(auth);
        if(u == null) throw new NotFound("token not valid");
        userService.getUser(u.getUsername());
        Post newPost = null;
        UsersRepo.makeDirectoryIfNotExists("./front/src/assets/pictures/" + u.getUsername());
        if (filePart.getSubmittedFileName() != null) {
            try (InputStream inputStream = filePart.getInputStream()) {
                OutputStream outputStream = new FileOutputStream("./front/src/assets/pictures/" + u.getUsername() + "/" + filePart.getSubmittedFileName());
                IOUtils.copy(inputStream, outputStream);
                outputStream.close();
            }
            newPost = new Post(filePart.getSubmittedFileName(), text, new ArrayList<>());
        } else {
            newPost = new Post("", text, new ArrayList<>());
        }

        postRepo.addPost(newPost, u.getUsername());
    }

    public ArrayList<Post> getPosts(String username) throws NotFound {
        userService.getUser(username);
        return postRepo.getByUsername(username);
    }

    public ArrayList<Post> deletePost( Post post, String auth) throws NotFound , Exception{
        User u = userService.getLoggedInUser(auth);
        if(u == null) throw new NotFound("token not valid");
        String username = u.getUsername();
        userService.getUser(username);
        postRepo.deletePost(username, post);
        if(userService.getUser(username).getProfilePicture().equals(post.getPicture())){
            userService.removeProfilePhoto(auth);
        }
        return postRepo.getByUsername(username);
    }

    public Post getPost(String username, String picture) throws NotFound {
        for(Post p : getPosts(username)){
            if(p.getPicture().equals(picture))
                return p;
        }
        throw new NotFound("Objava nije pronadjena.");
    }
}
