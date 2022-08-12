package service;

import beans.User;
import exceptions.NotFound;
import exceptions.WrongPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repo.UsersRepo;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class UserService {
    private static UsersRepo usersRepo = new UsersRepo();
    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public User login(Map<String, String> userData) throws NotFound, WrongPassword {
        usersRepo.init();
        User u = usersRepo.getByUsername(userData.get("username"));
        if(u == null) throw new NotFound("user not found");
        if(!u.getPassword().equals(userData.get("password"))) throw new WrongPassword("password wrong");
        String jws = Jwts.builder().setSubject(u.getUsername()).setExpiration(new Date(new Date().getTime() + 1000*60*60*5L)).setIssuedAt(new java.util.Date()).signWith(key).compact();
        u.setToken(jws);
        return u;
    }
}
