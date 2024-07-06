package spotify.api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static spotify.api.Route.API;
import static spotify.api.Route.TOKEN;


public class ApiUtils {
    @Step
    public static Response post(String request, String endpoint) {
        try {
            return given(SpecBuilder.getRequestSpec())
                    .body(request)
                    .when()
                    .post(endpoint)
                    .then()
                    .spec(SpecBuilder.getResponseSpec())
                    .extract()
                    .response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param formParams
     * @return
     */
    @Step
    public static Response postAccount(HashMap<String, String> formParams) {
        return given(SpecBuilder.getAccountRequestSpec())
                .formParams(formParams)
                .when().post(API + TOKEN)
                .then().spec(SpecBuilder.getResponseSpec())
                .extract()
                .response();
    }

    @Step
    public static Response get(String endpoint) {
        try {
            return given(SpecBuilder.getRequestSpec())
                    .when()
                    .get(endpoint)
                    .then()
                    .spec(SpecBuilder.getResponseSpec())
                    .extract()
                    .response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Response put(String request, String endpoint) {
        try {
            return given(SpecBuilder.getRequestSpec())
                    .body(request)
                    .when()
                    .get(endpoint)
                    .then()
                    .spec(SpecBuilder.getResponseSpec())
                    .extract()
                    .response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
