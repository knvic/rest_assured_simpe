package reqresgrouptests.tests;

import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static reqresgrouptests.specs.LoginSpec.loginRequestSpec;
import static reqresgrouptests.specs.LoginSpec.loginResponseSpec;

import reqresgrouptests.models.*;


public class RestAssuredTests extends BaseTest {

    @Test
    void successfulLoginWithSpecsAllureTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Make login request", () ->
                given()
                        .spec(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("Verify token exist", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }


    @Test
    void successfulGetUserTest() {
        UserModel userModel = given()
                .spec(loginRequestSpec)
                .when()
                .get("/users?page=8")
                .then()
                .spec(loginResponseSpec)
                .extract().as(UserModel.class);
        step("Verify url", () ->
                assertThat(userModel.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading"));

        step("Verify text", () ->
                assertThat(userModel.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!"));


    }


    @Test
    void successfulGetListUsersTest() {
        UserModel userModel =given()
                .spec(loginRequestSpec)
                .when()
                .get("/users")
                .then()
                .spec(loginResponseSpec)
                .extract().as(UserModel.class);

System.out.println();

    }


    @Test
    void successfulCreateUserTest() {
        String authData = "{\"name\": \"vasya\", \"job\": \"boss\"}";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("vasya"))
                .body("job", is("boss"));
    }


    @Test
    void successfulUpdateUserTest() {
        String authData = "{\"name\": \"vasya\", \"job\": \"bigboss\"}";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("vasya"))
                .body("job", is("bigboss"));
    }

    @Test
    void successfulDeleteUserTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    void successfulRegisterUserTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"qwerty\"}";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                // .body("email", is("asd@qa.ru"));
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void unsuccessfulRegisterNotValidUserTest() {
        String authData = "{\"email\": \"qa@reqres.in\", \"password\": \"pistol1\"}";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                // .body("email", is("asd@qa.ru"));
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void unsuccessfulRegisterUserMissingPasswordTest() {
        String authData = "{\"email\": \"asd@qa.ru\"}";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
