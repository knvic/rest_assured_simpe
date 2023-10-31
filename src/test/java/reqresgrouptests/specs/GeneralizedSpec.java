package reqresgrouptests.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import reqresgrouptests.tests.BaseTest;

import static reqresgrouptests.helpers.CustomAllureListener.withCustomTemplates;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class GeneralizedSpec extends BaseTest {

    public static RequestSpecification generalizedRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON)
            .baseUri(config.getBaseUrl())
            .basePath(config.getBasePath());

    public static ResponseSpecification generalizedResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification missingPasswordResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .build();


}
