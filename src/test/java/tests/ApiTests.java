package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


import static org.assertj.core.api.Assertions.assertThat;

import static specs.TestSpec.loginResponseSpec;
import static specs.TestSpec.requestSpec;
import static specs.TestSpec.response;
import static specs.TestSpec.responseDelete;
import static specs.TestSpec.responseNotFound;
import static specs.TestSpec.responseUser;


@Owner("Alexander Drozenko")
@Epic("Api test")
@Feature("USER TESTS")
public class ApiTests extends TestBase {
    @Test
    @DisplayName("LIST USERS")
    void getListUsersTest() {
        UserDataList list = step("Делаем запрос списка пользователей", () -> given()
                .spec(requestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(response)
                .extract().as(UserDataList.class));
        step("Проверяем количество страниц", () ->
                assertThat(list.getTotalPages()).isEqualTo(2));
    }

    @Test
    @DisplayName("SINGLE USER NOT FOUND")
    void userNotFoundTest() {
        step("Делаем запрос о пользователе", () -> given()
                .spec(requestSpec)
                .when()
                .get("/api/users/23")
                .then()
                .spec(responseNotFound));

    }

    @Test
    @DisplayName("CREATE USER")
    void createUserTest() {
        CreateUser user = new CreateUser();
        user.setName("morpheus");
        user.setJob("leader");
        CreateUserResponse userResponse = step("Делаем запрос на создание пользователя   ", () -> given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/users")
                .then()
                .spec(responseUser)
                .extract().as(CreateUserResponse.class));
        step("Проверяем параметр JOB у пользователя ", () ->
                assertThat(userResponse.getJob()).isEqualTo("leader"));
        step("Проверяем параметр  Name у пользователя ", () ->
                assertThat(userResponse.getName()).isEqualTo("morpheus"));
    }

    @Test
    @DisplayName("DELETE USER")
    void deleteUserTest() {
        step("Делаем запрос на удаление пользователя ", () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .post("/api/users/2")
                        .then()
                        .spec(responseDelete)
                        .extract()
                        .response());


    }

    @Test
    @DisplayName("REGISTER - SUCCESSFUL")
    void successfulLoginTest() {
        LoginSuccessful authData = new LoginSuccessful();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        LoginResponse loginResponse = step("Делаем запрос", () -> given()
                .spec(requestSpec)
                .body(authData)
                .when()
                .post("/api/register")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponse.class));
        step("Проверяем Token", () ->
                assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));

    }
}






