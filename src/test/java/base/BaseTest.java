package base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static String token;
    protected static int bookingId;

    @BeforeClass
    public void setup() throws Exception {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        prop.load(fis);

        RestAssured.baseURI = prop.getProperty("base.uri");
        System.out.println("Base URI = " + RestAssured.baseURI);

        // Generate Token
        String authBody = "{ \"username\": \"" + prop.getProperty("username") + "\", " +
                          "\"password\": \"" + prop.getProperty("password") + "\" }";

        Response response =
                given()
                    .header("Content-Type", "application/json")
                    .body(authBody)
                .when()
                    .post("/auth");

        token = response.jsonPath().getString("token");
        System.out.println("Auth Token: " + token);
    }
}
