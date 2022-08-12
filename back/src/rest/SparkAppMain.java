package rest;

import beans.FriendshipRequest;
import beans.Post;
import beans.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import exceptions.NotFound;
import exceptions.WrongPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repo.UsersRepo;
import service.UserService;
import spark.Filter;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static spark.Spark.*;


public class SparkAppMain {

	static ObjectMapper objectMapper = new ObjectMapper();
	static UserService userService = new UserService();

	public static void main(String[] args) throws Exception {
		port(8080);
		enableCORS("*", "*", "*");


		post("/login", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Map<String, String> map
					= objectMapper.readValue(payload, new TypeReference<Map<String,String>>(){});
			try {
				res.status(200);
				return objectMapper.writeValueAsString(userService.login(map));
			}catch (NotFound e){
				res.status(404);
				e.printStackTrace();
				ObjectNode error = objectMapper.createObjectNode();
				error.put("error", "Korisnik nije pronadjen.");
				return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
			}catch (WrongPassword e){
				res.status(404);
				e.printStackTrace();
				ObjectNode error = objectMapper.createObjectNode();
				error.put("error", "Lozinka je pogresna.");
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
			response.type("application/json");
		});
	}

}