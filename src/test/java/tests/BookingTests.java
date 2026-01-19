package tests;

import base.BaseTest;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingTests extends BaseTest {

    // 1️⃣ POST - Create Booking
    @Test(priority = 1)
    public void createBooking() {

        JSONObject body = new JSONObject();
        body.put("firstname", "Sai");
        body.put("lastname", "Pavan");
        body.put("totalprice", 1000);
        body.put("depositpaid", true);

        JSONObject dates = new JSONObject();
        dates.put("checkin", "2024-01-01");
        dates.put("checkout", "2024-01-05");

        body.put("bookingdates", dates);
        body.put("additionalneeds", "Breakfast");

        Response response =
                given()
                    .header("Content-Type", "application/json")
                    .body(body.toString())
                .when()
                    .post("/booking")
                .then()
                    .statusCode(200)
                    .extract()
                    .response();

        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("✅ Created Booking Response: " + response.asPrettyString());
        Assert.assertTrue(bookingId > 0);
    }

    // 2️⃣ GET - Fetch Booking
    @Test(priority = 2)
    public void getBooking() {

        Response response =
                given()
                .when()
                    .get("/booking/" + bookingId)
                .then()
                    .statusCode(200)
                    .extract()
                    .response();

        System.out.println("✅ Fetched Booking Response: " + response.asPrettyString());
    }

    // 3️⃣ PATCH - Update Booking
    @Test(priority = 3)
    public void updateBooking() {

    	String dynamicName = "User_" + System.currentTimeMillis();

        JSONObject body = new JSONObject();
        body.put("firstname", dynamicName);
        Response response =
                given()
                    .header("Content-Type", "application/json")
                    .header("Cookie", "token=" + token)
                    .body(body.toString())
                .when()
                    .patch("/booking/" + bookingId)
                .then()
                    .statusCode(200)
                    .extract()
                    .response();

        System.out.println("✅ Updated Booking Response: " + response.asPrettyString());
    }

    // 4️⃣ DELETE - Delete Booking
    @Test(priority = 4)
    public void deleteBooking() {

        Response response =
                given()
                    .header("Cookie", "token=" + token)
                .when()
                    .delete("/booking/" + bookingId)
                .then()
                    .statusCode(201)
                    .extract()
                    .response();

        System.out.println("✅ Deleted Booking Response: " + response.asPrettyString());
    }
}
