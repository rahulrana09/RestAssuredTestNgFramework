package framework.api.utils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;


public class ApiUtils {
    @Step
    public static Response post(String request, String endpoint) {
        try {
            return RestAssured.given(SpecBuilder.getRequestSpec())
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
        return RestAssured.given(SpecBuilder.getAccountRequestSpec())
                .formParams(formParams)
                .when().post(Route.API + Route.TOKEN)
                .then().spec(SpecBuilder.getResponseSpec())
                .extract()
                .response();
    }

    @Step
    public static Response get(String endpoint) {
        try {
            return RestAssured.given(SpecBuilder.getRequestSpec())
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
            return RestAssured.given(SpecBuilder.getRequestSpec())
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
