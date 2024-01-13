import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Owner("Alexander Drozenko")
@Epic("Api test")
@Feature("USER TESTS")
public class ApiTests extends TestBase {
    @Test
    @DisplayName("LIST USERS")
    void getListUsersTest() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("total_pages", is(2));
    }

    @Test
    @DisplayName("SINGLE USER NOT FOUND")
    void userNotFoundTest() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("/api/users/23")
                .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    @DisplayName("CREATE USER")
    void createUserTest() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\"}")
                .when()
                .post("/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"), "job", is("leader"));

    }

    @Test
    @DisplayName("DELETE USER")
    void deleteUserTest() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .delete("/api/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("SINGLE RESOURCE")
    void listSingleResourceTest() {
        String expectedColor = "#C74375";
        String actualColor = given()
                .log().body()
                .when()
                .get("/api/unknown/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("data.color");
        assertEquals(expectedColor, actualColor);
    }

    @Test
    @DisplayName("REGISTER - SUCCESSFUL")
    void successfulLoginTest() {
        String email = "eve.holt@reqres.in";
        String password = "pistol";
        String authData = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    @DisplayName("SINGLE RESOURCE")
    void listResourceTest() {
        String pageID = "3";
        String expectedname = "true red";
        String actualColor = given()
                .log().body()
                .when()
                .get("/api/unknown?id=" + pageID)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("data.name");
        assertEquals(expectedname, actualColor);
    }


}

