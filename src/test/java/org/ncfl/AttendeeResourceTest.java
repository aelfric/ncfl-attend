package org.ncfl;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AttendeeResourceTest {

    @Order(1)
    @Test
    void returnsEmptyList() {
        given()
                .when()
                .get("/attendees")
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Order(2)
    @Test
    void registerAnAttendee() {
        given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("name", "John Doe")
                .formParam("lunchType", "STUDENT")
                .when()
                .post("/attendees")
                .then()
                .statusCode(200)
                .body("name",equalTo("John Doe"));
    }

    @Order(3)
    @Test
    void registerInvalidAttendee() {
        given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("name", "John Doe")
                .formParam("lunchType", "UNKNOWN")
                .when()
                .post("/attendees")
                .then()
                .statusCode(400);
    }

    @Order(4)
    @Test
    void claimALunch() {
        String id = given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("name", "John Doe")
                .formParam("lunchType", "STUDENT")
                .when()
                .post("/attendees")
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("status", "CLAIMED")
                .when()
                .post("/attendees/{id}", id)
                .then()
                .statusCode(200)
                .body(containsString("CLAIMED"))
                .body(containsString("John Doe"));
    }
}