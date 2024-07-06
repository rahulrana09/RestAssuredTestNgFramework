package com.archive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static Framework.Contracts.PostMan.getCreateCollectionJson;
import static Framework.Contracts.PostMan.getCreateWorkSpaceJson;
import static Framework.JsonPathOperation.delete;
import static Framework.JsonPathOperation.set;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TestPostOperations {
    private RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {

        Set<String> headerMask = new HashSet<String>();
        headerMask.add("x-api-key");
        headerMask.add("accept");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-mock-match-request-headers", "headerName");
        headers.put("x-api-key", "");

        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com")
                .addHeaders(headers)
                .setContentType(ContentType.JSON)
                .setConfig(config.logConfig(LogConfig.logConfig().blacklistHeaders(headerMask)))
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = specBuilder.build();
        // using static variable in RestAssured class whose type is RequestSpecification

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }


    @Test
    public void testOneWithStringBody() {
        String payLoad = "{\n" +
                "    \"workspace\":{\n" +
                "            \"name\": \"ScriptWS-007\",\n" +
                "            \"type\": \"personal\",\n" +
                "            \"description\": \"Created New Workspace\"\n" +
                "        }\n" +
                "}";
        given().
                body(payLoad).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", is(equalTo("ScriptWS-007")),
                        "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void testOneWithStringBodyNonBDD() {
        String payLoad = "{\n" +
                "    \"workspace\":{\n" +
                "            \"name\": \"ScriptWS-007\",\n" +
                "            \"type\": \"personal\",\n" +
                "            \"description\": \"Created New Workspace\"\n" +
                "        }\n" +
                "}";
        Response response = with().
                body(payLoad).
                post("/workspaces");

        assertThat(response.path("woskspace.name").toString(), is(equalTo("ScriptWS-007")));
        assertThat(response.path("woskspace.id"), Matchers.matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void testPut() {
        String wsId = "983a989f-dfeb-4155-8051-09dd8cd9e629";
        String payLoad = "{\n" +
                "    \"workspace\":{\n" +
                "            \"name\": \"ScriptWS-007\",\n" +
                "            \"type\": \"personal\",\n" +
                "            \"description\": \"Edited New Workspace\"\n" +
                "        }\n" +
                "}";
        // remove above for delete, no payload is required
        given().
                body(payLoad).
                pathParam("workspaceId", wsId).
                when().
                put("/workspaces/{workspaceId}"). // similar for delete
                then().
                assertThat().
                body("workspace.name", is(equalTo("ScriptWS-007")), // remove this in case of delete
                        "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", wsId);
    }

    @Test
    public void postUsingNestedObject() {
        HashMap<String, Object> mainBody = new HashMap<>();
        HashMap<String, String> nested = new HashMap<>();
        nested.put("name", "ScriptWS-007");
        nested.put("type", "personal");
        nested.put("description", "Test Description");
        mainBody.put("workspace", nested);

        given().
                body(mainBody).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", is(equalTo("ScriptWS-007")),
                        "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void postUsingJSONArrayList() {
        HashMap<String, Object> mainBody = new HashMap<>();
        HashMap<String, String> nested = new HashMap<>();
        nested.put("name", "ScriptWS-007");
        nested.put("type", "personal");
        nested.put("description", "Test Description");
        mainBody.put("workspace", nested);

        given().
                body(mainBody).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", is(equalTo("ScriptWS-007")),
                        "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void post_payload_as_map_using_object_mapper() throws JsonProcessingException {
        HashMap<String, Object> mainBody = new HashMap<>();
        HashMap<String, String> nested = new HashMap<>();
        nested.put("name", "ScriptWS-007");
        nested.put("type", "personal");
        nested.put("description", "Test Description");
        mainBody.put("workspace", nested);
        // could be used for POJO or list
        ObjectMapper objMapper = new ObjectMapper();
        String mainBodyS = objMapper.writeValueAsString(mainBody);
//         a node obj of objMapper could also be used for creating the Payload
// todo   ObjectNode objNode = objMapper.createObjectNode(); use for Object MAP
//        objNode.put("name", "ScriptWS-007")
//        ArrayNode objNode = objMapper.createArrayNode(); use this for ArrayList
        given().
                body(mainBodyS).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", is(equalTo("ScriptWS-007")),
                        "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"));
    }


    @Test
    public void postUsingMultiLineString() {
        String payLoad = getCreateWorkSpaceJson(
                set("$.workspace.name", "WS_MultiLine"));
        given().
                body(payLoad).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", is(equalTo("ScriptWS-007")),
                        "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void post_add_collection_using_multi_line_string() {
        String payLoad = getCreateCollectionJson(
                set("$.collection.info.name", "Collection_MultiLine"),
                delete("$.collection.info.uid"),
                set("collection.item[0].name", "Folder_MultiLine"));

        given().
                body(payLoad).
                when().
                post("/collections");
    }

}
