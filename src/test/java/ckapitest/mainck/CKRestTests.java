package ckapitest.mainck;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;



public class CKRestTests {






    @Test
    void getAddressEndpointTest() {
        String authData = "{ \"app\": \"AppName\" }";

        given()
                .when()
                .get("https://sta-ck11web.oduyu.so/api/public/core/v2.0/addresses")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);;


    }


    @Test
    void successfulbasicAuthenticationTest() {
        String authData = "{ \"app\": \"AppName\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .auth()
                .basic("", "")
                .post("https://sta-ck11web.oduyu.so:9443/auth/app/token")
                .then()
                .log().status()
                .log().body();
    }


// myUrl1 = "https://sta-ck11web.oduyu.so/api/public/measurement-values/v2.1/numeric/61983A21-AC91-4195-B1E3-B495791177BE/data/snapshot"

    @Test
    void getNumericPostTest() {

        String ID ="057d2ed0-90e2-4774-8b37-230688d0fb4e";
        String myUrl1 = "https://sta-ck11web.oduyu.so/api/public/measurement-values/v2.1/numeric/"+ID+"/data/snapshot";
        String bearerToken="N1D7-1NyI3pAl-fshXMlTe34iaAw8YL7lUUp31nn9vDEEdG0UGisLMuHUXBiu85Jxa54xuptXp_EKOEXoL_8F8D1p6oYXuI4jQksWvLy_avzxeNDC7peKsMcN-w86V-2VVIvdsa57xNDyMQtz8w1iwzFSzXiMAUBuJcXN7uZ9eL6ldWrdU4h1Y9ccnijZM3BXt_frsBJSGboh9EIePYfRFW85B9F9KGmTCPzX2Pd82akZYG5BMdflK8dORsnN37xnxcrV2YR2-2zfK-gNLNjmH_lwaBajhhkUdQMhMoyxXLAPoXfoyqkt89uzfG7K1-B2k99FVdXRNghbioxOjXOTw";
        Response response =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + bearerToken,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON)
                        .when()
                        .get(myUrl1)
                        .then()
                        .contentType(ContentType.JSON)
                        .log().status()
                        .log().body()
                        .extract()
                        .response();


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
