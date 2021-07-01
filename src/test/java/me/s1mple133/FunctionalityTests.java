package me.s1mple133;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class FunctionalityTests {
    private static String token;

    @BeforeAll
    public static void init() {
        JSONObject requestParams = new JSONObject();
        RequestSpecification requestSpecification = RestAssured.given();

        requestParams.put("username", "test");
        requestParams.put("password", "test");
        requestParams.put("email", "test@gmail.com");
        requestSpecification.header("Content-Type", "application/json");

        requestSpecification.body(requestParams.toString());

        assertEquals(requestSpecification.post("/auth/register").getStatusCode(), 200);

        requestParams.remove("email");

        token = requestSpecification.post("/auth/login").getBody().toString();
    }

    @Test
    public void overviewTest() throws URISyntaxException {
        RequestSpecification requestSpecification = RestAssured.given();

        requestSpecification.header("Token", token);

        Response res = requestSpecification.post("/overview");

        assertEquals(res.getStatusCode(), 200);
    }
}
