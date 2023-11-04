package reqresgrouptests.tests;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static reqresgrouptests.specs.GeneralizedSpec.generalizedRequestSpec;
import static reqresgrouptests.specs.GeneralizedSpec.generalizedResponseSpec;
import reqresgrouptests.models.*;

@Tag("api")
public class RestAssuredTests extends BaseTest {

    @Test
    void successfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Посылаем запрос с данными на авторизацию", () ->
                given()
                        .spec(generalizedRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(generalizedResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("В ответе присутствует токен авторизации", () ->
                assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void successfulGetUserTest() {
        UserModel userModel = step("Посылаем зпрос на вывод страницы 3, зная что страниц с данными 2", () ->
                given()
                        .spec(generalizedRequestSpec)
                        .when()
                        .get("/users?page=3")
                        .then()
                        .spec(generalizedResponseSpec)
                        .extract().as(UserModel.class));
        step("Проверяем, что на странице 3 масиив с данными отсутствует и размер массива 0", () ->
                assertThat(userModel.getDataList()).hasSize(0));

        step("Проверяем наличие поля  URL https://reqres.in/#support-heading", () ->
                assertThat(userModel.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading"));

        step("Проверяем наличие поля Text To keep ReqRes free, contributions towards server costs are appreciated!", () ->
                assertThat(userModel.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!"));


    }


    @Test
    void successfulGetListUsersTest() {
        UserModel userModel = step("Посылаем зпрос на вывод страницы 2 где есть данные", () ->
                given()
                        .spec(generalizedRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(generalizedResponseSpec)
                        .extract().as(UserModel.class));

        step("В поле total значение 12", () ->
                assertThat(userModel.getTotal().equals(12)));

        step("В поле per_page значение 6  ", () ->
                assertThat(userModel.getPer_page().equals(6)));

        step("Количество объектов с данными в массиве равно 6  ", () ->
                assertThat(userModel.getDataList().size()).isEqualTo(6));

        step("В объекте 3 с данными из массива в поле first_name значение Tobias ", () ->
                assertThat(userModel.getDataList().get(2).getFirstName()).isEqualTo("Tobias"));

    }


    @Test
    void successfulCreateUserTest() {
        User user = new User();
        user.setName("vasya");
        user.setJob("boss");
        step("Посылаем запрос на создание пользователя с данными класса User. Проверяем в ответе статус 201 и подтверждение созданных данных", () ->
                given()
                        .spec(generalizedRequestSpec)
                        .body(user)
                        .when()
                        .post("/users")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(201)
                        .body("name", is("vasya"))
                        .body("job", is("boss")));

    }

    @Test
    void successfulCreateUserResponseTest() {
        User user = new User();
        user.setName("vasya");
        user.setJob("boss");
        Response response = step("Посылаем запрос на создание пользователя. Получаем объект Response", () ->
                given()
                        .spec(generalizedRequestSpec)
                        .body(user)
                        .when()
                        .post("/users"));

        step("Проверяем созданный мользователь имеет имя vasya", () ->
                Assertions.assertThat(response.getBody().path("name").toString()).isEqualTo("vasya"));
        step("Проверяем, что id пользоватея не пустой", () ->
                Assertions.assertThat(response.getBody().path("id").toString()).isNotEmpty());
        step("Проверяем код ответа 201", () ->
                Assertions.assertThat(response.getStatusCode()).isEqualTo(201));
    }


    @Test
    void successfulUpdateUserTest() {
        User user = new User();
        user.setName("vasya");
        user.setJob("bigboss");
        Response response = step("Обновляем данные объекта", () -> given()
                .spec(generalizedRequestSpec)
                .body(user)
                .when()
                .patch("/users/2"));
        step("Проверяем обновенное поле job : bigboss", () ->
                Assertions.assertThat(response.getBody().path("job").toString()).isEqualTo("bigboss"));
        step("Проверяем код ответа 200", () ->
                Assertions.assertThat(response.getStatusCode()).isEqualTo(200));


    }

    @Test
    void successfulDeleteUserTest() {
        Response response = step("Удаляем объект", () ->
                given()
                        .spec(generalizedRequestSpec)
                        .log().uri()
                        .log().method()
                        .when()
                        .delete("/users/2"));
        step("Проверяем код ответа 204", () ->
                Assertions.assertThat(response.getStatusCode()).isEqualTo(204));

    }


    @Test
    void unsuccessfulRegisterNotValidUserTest() {

        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("qa@reqres.in");
        authData.setPassword("pistol1");
        Response response = step("Отправка запроса на регистрацию с несуществующим именем qa@reqres.in ", () ->
                given()
                .spec(generalizedRequestSpec)
                .body(authData)
                .when()
                .post("/register"));
        step("В поле error появляет предопределенный текст Note: Only defined users succeed registration", () ->
                Assertions.assertThat(response.getBody().path("error").toString()).isEqualTo("Note: Only defined users succeed registration"));
        step("Проверяем код ответа 400", () ->
                Assertions.assertThat(response.getStatusCode()).isEqualTo(400));


    }

    @Test
    void unsuccessfulRegisterUserMissingPasswordTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("asd@qa.ru");


        Response response = step("Отправка запроса на регистрацию с неправильным паролем", () ->
                given()
                        .spec(generalizedRequestSpec)
                        .body(authData)
                        .when()
                        .post("/register"));
        step("В поле error появляет предопределенный текст Missing password", () ->
                Assertions.assertThat(response.getBody().path("error").toString()).isEqualTo("Missing password"));
        step("Проверяем код ответа 400", () ->
                Assertions.assertThat(response.getStatusCode()).isEqualTo(400));


    }
}
