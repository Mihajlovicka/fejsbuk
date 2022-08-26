package service;

import beans.Post;
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

    public void newPost(Part filePart, String username, String text) throws IOException, NotFound {
        userService.getUser(username);
        Post newPost = null;
        UsersRepo.makeDirectoryIfNotExists("./front/src/assets/pictures/" + username);
        if (filePart.getSubmittedFileName() != null) {
            try (InputStream inputStream = filePart.getInputStream()) {
                OutputStream outputStream = new FileOutputStream("./front/src/assets/pictures/" + username + "/" + filePart.getSubmittedFileName());
                IOUtils.copy(inputStream, outputStream);
                outputStream.close();
            }
            newPost = new Post(filePart.getSubmittedFileName(), text, new ArrayList<>());
        } else {
            newPost = new Post("", text, new ArrayList<>());
        }

        postRepo.addPost(newPost, username);
    }

    public ArrayList<Post> getPosts(String username) throws NotFound {
        userService.getUser(username);
        return postRepo.getByUsername(username);
    }

    public ArrayList<Post> deletePost(String username, Post post) throws NotFound {
        userService.getUser(username);
        postRepo.deletePost(username, post);
        return postRepo.getByUsername(username);
    }
}
