package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.google.gson.Gson;


public class SparkAppMain {

	private static Gson g = new Gson();

	public static void main(String[] args) throws Exception {
		port(8080);

	}

}
