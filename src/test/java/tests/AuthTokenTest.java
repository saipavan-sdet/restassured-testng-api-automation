package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class AuthTokenTest extends BaseTest {

    public static String token;

    @Test
    public void generateToken() {

        JSONObject body = new JSONObject();
        body.put("username", "admin");
        body.put("password", "password123");

        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .body(body.toString())
                .when()
                    .post("/auth")
                .then()
                    .statusCode(200)
                    .extract().response();

        token = response.jsonPath().getString("token");

        Assert.assertNotNull(token);
        System.out.println("Auth Token: " + token);
    }
}
