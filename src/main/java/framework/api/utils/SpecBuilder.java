package framework.api.utils;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SpecBuilder {

    @Step
    public static RequestSpecification getRequestSpec() {
        Set<String> headerMask = new HashSet<String>();
        headerMask.add("x-api-key");
        headerMask.add("accept");
        headerMask.add("Authorization");
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Authorization", "Bearer " + TokenManager.getToken());

        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath(Route.BASE_PATH)
                .setAuth(RestAssured.oauth2(TokenManager.getToken()))
                .addHeaders(headers)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .setConfig(RestAssured.config.logConfig(LogConfig.logConfig().blacklistHeaders(headerMask)))
                .log(LogDetail.ALL)
                .build();
    }

    @Step
    public static RequestSpecification getAccountRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://accounts.spotify.com")
                .setContentType(ContentType.URLENC)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    @Step
    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
