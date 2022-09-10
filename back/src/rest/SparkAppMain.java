package rest;

import beans.FriendshipRequest;
import beans.Post;
import beans.PostDTO;
import beans.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import exceptions.NotFound;
import exceptions.WrongPassword;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.eclipse.jetty.websocket.api.Session;
import repo.UsersRepo;
import service.PostService;
import service.UserService;
import spark.Filter;
import spark.utils.IOUtils;
import ws.WsHandler;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.*;


public class SparkAppMain {

    static ObjectMapper objectMapper = new ObjectMapper();
    static UserService userService = new UserService();
    static PostService postService = new PostService();
    public static Map<String, ArrayList<Session>> userUsernameMap = new ConcurrentHashMap<>();
    public static int nextUserNumber = 1; //Used for creating the next username

    public static void main(String[] args) throws Exception {
        port(8080);

        webSocket("/ws", WsHandler.class);
        enableCORS("*", "*", "*");



        get("/getAllUsers", (req, res) -> {
            res.status(200);
            return objectMapper.writeValueAsString(userService.getAll());
        });

        get("/getUser", (req, res) -> {
            String username = req.queryParams("username");
            try {
                res.status(200);
                User u = userService.getUser(username);
                return objectMapper.writeValueAsString(u);
            } catch (NotFound e) {
                e.printStackTrace();
                res.status(404);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        get("/getLoggedInUser", (req, res) -> {
            String auth = req.headers("Authorization");
            try {
                res.status(200);
                User u = userService.getLoggedInUser(auth);
                return objectMapper.writeValueAsString(u);
            } catch (NotFound e) {
                e.printStackTrace();
                res.status(404);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Nema tokena.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        get("/searchUsers", (req, res) -> {
            res.status(200);
            String startDate = req.queryParams("start");
            String endDate = req.queryParams("end");
            String search = req.queryParams("search");
            String auth = req.headers("Authorization");
            ArrayList<User> users = userService.search(startDate, endDate, search == null ? "" : search);
            ArrayList<User> new_users = (ArrayList<User>) users.clone();
            try {
                User u = userService.getLoggedInUser(auth);
                if (u != null)
                    new_users.remove(u);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return objectMapper.writeValueAsString(new_users);
        });



        get("/adminSearchUsers", (req, res) -> {
            res.status(200);
            String name = req.queryParams("name");
            String surname = req.queryParams("surname");
            String email = req.queryParams("email");
            //String auth = req.headers("Authorization");
            ArrayList<User> users = userService.adminSearch(name, surname, email == null ? "" : email);
            /*ArrayList<User> new_users = (ArrayList<User>) users.clone();
            try {
                User u = userService.getLoggedInUser(auth);
                if (u != null)
                    new_users.remove(u);

            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return objectMapper.writeValueAsString(users);
        });

        get("/getFriendRequestsList", (req, res) -> {
            res.status(200);

            String auth = req.headers("Authorization");
            ArrayList<User> users = null;
            try {
                users = userService.getRecievedFriendRequests(auth);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return objectMapper.writeValueAsString(users);
        });
        post("/blockUser", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.blockUser(u);
                if(user != null)
                    return objectMapper.writeValueAsString(user);
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/unblockUser", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.unblockUser(u);
                if(user != null)
                    return objectMapper.writeValueAsString(user);
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        get("/getFriendRequestsList", (req, res) -> {
            res.status(200);

            String auth = req.headers("Authorization");
            ArrayList<User> users = null;
            try {
                users = userService.getRecievedFriendRequests(auth);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return objectMapper.writeValueAsString(users);
        });

        get("/getFriendsList", (req, res) -> {
            res.status(200);

            String auth = req.headers("Authorization");
            String username = req.queryParams("username");
            ArrayList<User> users = null;
            try {
                users = userService.getFriends(username);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return objectMapper.writeValueAsString(users);
        });

        post("/login", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            Map<String, String> map
                    = objectMapper.readValue(payload, new TypeReference<Map<String, String>>() {
            });
            try {
                res.status(200);
                return objectMapper.writeValueAsString(userService.login(map));
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            } catch (WrongPassword e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Lozinka je pogresna.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            } catch (Exception e){
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik je blokiran od strane admina");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });
        post("/register", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.registerUser(u);
                if(user != null)
                    return objectMapper.writeValueAsString(userService.registerUser(u));
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Korisnicko ime vec postoji. Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });
        post("/addFriend", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.addFriend(u, req.headers("Authorization"));
                if(user != null)
                    return objectMapper.writeValueAsString(user);
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });
        post("/acceptFriendRequest", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.acceptFriend(u, req.headers("Authorization"));
                if(user != null)
                    return objectMapper.writeValueAsString(user);
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/rejectFriendRequest", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.rejectFriend(u, req.headers("Authorization"));
                if(user != null)
                    return objectMapper.writeValueAsString(user);
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });


        post("/cancelFriendRequest", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.cancelFriendRequest(u, req.headers("Authorization"));
                if(user != null)
                    return objectMapper.writeValueAsString(user);
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/removeFriend", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                User user = userService.removeFriend(u, req.headers("Authorization"));
                if(user != null)
                    return objectMapper.writeValueAsString(user);
                else{
                    res.status(404);
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Doslo je do greske.");
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                }
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/changePassword", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            Map<String, String> map
                    = objectMapper.readValue(payload, new TypeReference<Map<String, String>>() {
            });
            try {
                res.status(200);
                userService.changePassword(map, req.headers("Authorization"));
                ObjectNode error = objectMapper.createObjectNode();
                error.put("succes", "Lozinka uspesno izmenjena.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            } catch (WrongPassword e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Stara lozinka je pogresna.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/updateUser", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            User u = objectMapper.readValue(payload, User.class);
            try {
                res.status(200);
                return objectMapper.writeValueAsString(userService.updateUser(u, req.headers("Authorization")));
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen. Doslo je do greske.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/changeProfilePhoto", (req, res) -> {
            res.type("application/json");
            String picture = req.body();
            try {
                res.status(200);
                userService.changeProfilePhoto(picture, req.headers("Authorization"));
                ObjectNode error = objectMapper.createObjectNode();
                error.put("success", "Uspesna izmena.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen. Doslo je do greske.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/newPost", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("./front/src/assets/pictures/"));
            Part filePart = req.raw().getPart("file");
            String text = req.raw().getParameter("text");
            try {
                res.status(200);
                postService.newPost(filePart, text, req.headers("Authorization"));
                ObjectNode error = objectMapper.createObjectNode();
                error.put("success", "Uspesno dodata nova objava.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            } catch (IOException e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Doslo je do greske prilikom cuvanja slike.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Doslo je do greske. Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/deletePost", (req, res) -> {
            res.type("application/json");
            String id = req.body();

            try {
                res.status(200);
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postService.deletePost(id, req.headers("Authorization")));
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Doslo je do greske. Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Token ne vazi.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });
        post("/deletePostAdmin", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            Map<String, String> deletingDesc
                    = objectMapper.readValue(payload, Map.class);
            try {
                res.status(200);
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postService.addDeletingDecriptionPost(deletingDesc));
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Doslo je do greske. Post nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });


        get("/getPosts", (req, res) -> {
            String username = req.queryParams("username");
            try {
                res.status(200);
                ArrayList<PostDTO> u = postService.getPostsDTO(username);
                return objectMapper.writeValueAsString(u);
            } catch (NotFound e) {
                e.printStackTrace();
                res.status(404);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }

        });

        get("/getPostsAdmin", (req, res) -> {
            String username = req.queryParams("username");
            try {
                res.status(200);
                ArrayList<PostDTO> u = postService.getPostsDTOAdmin();
                return objectMapper.writeValueAsString(u);
            } catch (NotFound e) {
                e.printStackTrace();
                res.status(404);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }

        });

        get("/getPost", (req, res) -> {
            String id = req.queryParams("id");
            try {
                res.status(200);

                return objectMapper.writeValueAsString(postService.getPost(id));
            } catch (NotFound e) {
                e.printStackTrace();
                res.status(404);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }

        });

        get("/getPostByPicture", (req, res) -> {
            String username = req.queryParams("username");
            String picture = req.queryParams("picture");
            try {
                res.status(200);

                return objectMapper.writeValueAsString(postService.getPostByPicture(username, picture));
            } catch (NotFound e) {
                e.printStackTrace();
                res.status(404);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }

        });

        post("/updatePost", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            Map<String, String> map
                    = objectMapper.readValue(payload, new TypeReference<Map<String, String>>() {
            });
            try {
                postService.updatePost(map, req.headers("Authorization"));
                res.status(200);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("succes", "Uspeh.");
                return objectMapper.writeValueAsString(error);
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/changeComment", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            Map<String, String> map
                    = objectMapper.readValue(payload, new TypeReference<Map<String, String>>() {
            });
            try {
                postService.changeComment(map);
                res.status(200);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("succes", "Uspeh.");
                return objectMapper.writeValueAsString(error);
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        post("/deleteComment", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            Map<String, String> map
                    = objectMapper.readValue(payload, new TypeReference<Map<String, String>>() {
            });
            try {
                postService.deleteComment(map);
                res.status(200);
                ObjectNode error = objectMapper.createObjectNode();
                error.put("succes", "Uspeh.");
                return objectMapper.writeValueAsString(error);
            } catch (NotFound e) {
                res.status(404);
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Korisnik nije pronadjen.");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });

        get("/getMessages", (req, res) -> {
            res.status(200);
            String sender = req.queryParams("sender");
            String receiver = req.queryParams("receiver");
            try {

                return objectMapper.writeValueAsString(userService.getMessages(sender, receiver));
            } catch (Exception e) {
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Nesto se razjebalo");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
            }
        });
    }

    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json; charset=utf-8");
        });
    }
    public static void broadcastMessage(String sender,String receiver, String message) {
        ArrayList<Session> s = new ArrayList<>();
        for (String user : userUsernameMap.keySet()) {
            if (user.contains(sender) && user.contains(receiver)) {
                s = userUsernameMap.get(user);
            }
        }
        userService.addMessage(sender, receiver, message);
        s.forEach( session -> {
            try {


                session.getRemote().sendString(String.valueOf(sender + " :" + message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
