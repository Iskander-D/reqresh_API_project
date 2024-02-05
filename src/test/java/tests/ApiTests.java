package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.TestSpec.*;


@Owner("Alexander Drozenko")
@Epic("Api test")
public class ApiTests extends TestBase {
    @Test
    @DisplayName("Список пользователей")
    void getListUsersTest() {
        UserDataList list = step("Делаем запрос списка пользователей", () -> given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(response200)
                .extract().as(UserDataList.class));
        step("Проверяем количество страниц", () ->
                assertThat(list.getTotalPages()).isEqualTo(2));
    }

    @Test
    @DisplayName("Пользователь не найден")
    void userNotFoundTest() {
        step("Делаем запрос о пользователе", () -> given()
                .spec(requestSpec)
                .when()
                .get("/users/23")
                .then()
                .spec(responseNotFound404));

    }

    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        CreateUser user = new CreateUser();
        user.setName("morpheus");
        user.setJob("leader");
        CreateUserResponse userResponse = step("Делаем запрос на создание пользователя   ", () -> given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/users")
                .then()
                .spec(responseUser201)
                .extract().as(CreateUserResponse.class));
        step("Проверяем параметр JOB у пользователя ", () ->
                assertThat(userResponse.getJob()).isEqualTo("leader"));
        step("Проверяем параметр  Name у пользователя ", () ->
                assertThat(userResponse.getName()).isEqualTo("morpheus"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {
        step("Делаем запрос на удаление пользователя ", () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(responseDelete204));
    }

    @Test
    @DisplayName("Успешная регистрауия пользователяL")
    void successfulLoginTest() {
        LoginSuccessful authData = new LoginSuccessful();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        LoginResponse loginResponse = step("Проходим регистарцию нового пользователяя", () -> given()
                .spec(requestSpec)
                .body(authData)
                .when()
                .post("/register")
                .then()
                .spec(loginResponseSpec200)
                .extract().as(LoginResponse.class));
        step("Проверяем Token", () ->
                assertThat(loginResponse.getToken()).isNotNull());

    }

}






