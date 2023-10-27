package ckapitest.filin;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class FilinRestTests {

    @Test
    void getAddressEndpointFilinTest() {
        String authData = "{ \"app\": \"AppName\" }";

        given()
                .when()
                .get("https://sta-filin.oduyu.so/api/public/core/v2.0/addresses")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);;


    }


    @Test
    void successfulbasicAuthenticationFilinTest() {
        String authData = "{ \"app\": \"AppName\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .auth()
                .basic("", "")
                .post("https://sta-filin.oduyu.so:9443/auth/app/token")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);;
    }


    @Test
    void getPostNegoTokenTest() {
        String authData = "{ \"app\": \"AppName\" }";
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("")
                .when()
                .post("https://sta-ck11web.oduyu.so:9443/auth/nego/token")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);


    }



    @Test
    void getPostBasicTokenTest() {
        String authData = "{ \"app\": \"\" }";
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData )
                .when()
                .post("https://sta-ck11web.oduyu.so:9443/auth/app/token")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);


    }

    @Test
    void checkWebEl() {
        given()
                .when()
                .get(" https://webelement.click")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
    void check() {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://webelement.click").openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Test failed with actual status code: " + responseCode);
            } else {
                System.out.println("Test passed");
            }
        } catch (IOException e) {
            System.out.println("Test failed with exception: " + e.getMessage());

        }


    }

    @Test
    void checkCK11() {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://sta-ck11web.oduyu.so/api/public/core/v2.0/addresses").openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Test failed with actual status code: " + responseCode);
            } else {
                System.out.println("Test passed");
            }
        } catch (IOException e) {
            System.out.println("Test failed with exception: " + e.getMessage());

        }


    }

}
