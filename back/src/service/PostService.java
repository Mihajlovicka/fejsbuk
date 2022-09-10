package service;

import beans.Comment;
import beans.Post;
import beans.PostDTO;
import beans.User;
import exceptions.NotFound;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
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
import java.util.Map;

public class PostService {
    private static PostRepo postRepo = new PostRepo();
    private static UserService userService = new UserService();

    public void newPost(Part filePart, String text, String auth) throws Exception {
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

    public ArrayList<Post> deletePost( String id, String auth) throws NotFound , Exception{
        User u = userService.getLoggedInUser(auth);
        if(u == null) throw new NotFound("token not valid");
        if(userService.getUser(u.getUsername()).getProfilePicture().equals(postRepo.getById(id).getPicture())){
            userService.removeProfilePhoto(auth);
        }
        postRepo.deletePost(id);

        return postRepo.getByUsername(u.getUsername());
    }

    public Post getPost(String id) throws NotFound {
        Post p = postRepo.getById(id);
        if(p != null) return p;
        throw new NotFound("Objava nije pronadjena.");
    }

    public Post getPostByPicture(String username, String picture) throws NotFound {
        for(Post p: getPosts(username)){
            if(p.getPicture().equals(picture)) return p;
        }
        throw new NotFound("Objava nije pronadjna.");
    }

    public ArrayList<PostDTO> getPostsDTO(String username) throws NotFound {
        User r = userService.getUser(username);
        ArrayList<PostDTO> list = new ArrayList<>();
        for(Post p : postRepo.getByUsername(username)){
            list.add(new PostDTO(username, r.getName() + " " +  r.getSurname(), r.getProfilePicture(),p.getId(), p.getPicture(), p.getDescription(), p.getComments()));
        }
        return list;
    }

    public ArrayList<PostDTO> getPostsDTOAdmin() throws NotFound {
        ArrayList<PostDTO> list = new ArrayList<>();
        for(User r : userService.getAll()) {
             for (Post p : postRepo.getByUsername(r.getUsername())) {
                list.add(new PostDTO(r.getUsername(), r.getName() + " " + r.getSurname(), r.getProfilePicture(), p.getId(), p.getPicture(), p.getDescription(), p.getComments()));
            }
        }
        return list;
    }

    public Post addDeletingDecriptionPost(Map<String,String> deletingDesc) {
        String username = deletingDesc.get("username");
        String id = deletingDesc.get("id");
        String desc = deletingDesc.get("description");
        return postRepo.addDeletingDescription(username, id, desc);
    }

    public void updatePost(Map<String, String> map, String auth) throws Exception {
        User loged = userService.getLoggedInUser(auth);
        String id = map.get("id");
        String text = map.get("text");
        Comment c = new Comment(loged, new Date(), text);
        postRepo.addComment(id, c);
    }

    public void deleteComment(Map<String, String> map) throws NotFound {
        String id = map.get("id");
        getPost(id);
        String commentId = map.get("commentId");
        postRepo.deleteComment(id, commentId);
    }

    public void changeComment(Map<String, String> map) throws NotFound {
        String id = map.get("id");
        getPost(id);
        String commentId = map.get("commentId");
        String text = map.get("text");
        postRepo.changeComment(id, commentId, text);
    }
}
