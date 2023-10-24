package rest_tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RestAssuredTests extends BaseTest{



        @Test
        void successfulLoginTest() {
            String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

            given()
                    .log().uri()
                    .log().method()
                    .log().body()
                    .contentType(JSON)
                    .body(authData)
                    .when()
                    .post("/login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("token", is("QpwL5tke4Pnpja7X4"));
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
    void successfulDeleteUser() {
               given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

        @Test
        void successfulRegisterUser() {
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
    void usuccessfulRegisterUser() {
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
        void unsuccessfulRegisterUser() {
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
