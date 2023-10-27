package reqresgrouptests.simpleold;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;
import reqresgrouptests.models.LoginBodyModel;
import reqresgrouptests.models.LoginResponseModel;
import reqresgrouptests.tests.BaseTest;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static reqresgrouptests.specs.LoginSpec.loginRequestSpec;


public class RestAssuredTests extends BaseTest {

   // LoginResponseLombokModel response = step("Make login request", () ->);



    @Test
    void successfulLoginWithAllureTest() {
       LoginBodyModel authData= new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Make login request", () ->
                given()
                        .spec(loginRequestSpec)
                        .filter(new AllureRestAssured())
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(LoginResponseModel.class));

        step("Verify response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }



        @Test
        void successfulGetUserTest() {
            given()
                    .log().uri()
                    .log().method()
                    .log().body()
                    .when()
                    .get("/users?page=8")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("page", is(8));
        }


    @Test
    void successfulGetListUsersTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
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
