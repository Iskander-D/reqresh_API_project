import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Owner("Alexander Drozenko")
@Epic("Api test")
@Feature("USER TESTS")
public class ApiTests extends TestBase {
    @Test
    @DisplayName("LIST USERS")
    void getListUsers() {
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
    void userNotFound() {
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
    void createUser() {
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
    void deleteUser() {
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
    void listResource() {
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


}

