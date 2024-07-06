package spotify;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import spotify.api.ApiUtils;
import spotify.api.StatusCode;
import spotify.utils.FakerUtils;

import static Framework.Contracts.PostMan.getCreatePlayListJson;
import static Framework.JsonPathOperation.set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Listeners({AllureTestNg.class})
@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlaylistTests {

    @Story("Create PlayList <JIRA ID>")
    @Test
    public void shoulBeAbleToCreatePlayListFm() {
        String payLoad = getCreatePlayListJson(
                set("$.name", FakerUtils.generateName()),
                set("$.description", FakerUtils.generateDescription())
        );
        Response res = ApiUtils.post(payLoad, "/users/ubozpvuyznfqoqgjjeqqqgqyk/playlists");
        assertThat(res.statusCode(), equalTo(201));
    }

    @Story("Get PlayList <JIRA ID>")
    @Test(description = "should be able to get the playlist")
    @Description("this test is to get the Spotify Playlist")
    @Issue("123")
    @TmsLink("test-1")
    @Link("Link to <JIRA Story>")
    public void shoulBeAbleToGetPlayList() {
        Response res = ApiUtils.get("/playlists/5sKRJlU6CEMtFomHsCLgit");
        assertStatusCode(res.statusCode(), StatusCode.CODE_200);
//        assertEqual(res.statusCode(), StatusCode.CODE_200.getMsg());
    }

    @Step
    public void assertStatusCode(int A, StatusCode statusCode) {
        assertThat(A, equalTo(statusCode.code));
    }


//    @Test
//    public void shoulBeAbleToCreatePlayList() {
//        String payLoad = getCreatePlayListJson(
//                set("$.name", "NewPlayList1"),
//                set("$.description", "TestDescription")
//        );
//        given()
//                .body(payLoad)
//                .when()
//                .post("/users/ubozpvuyznfqoqgjjeqqqgqyk/playlists")
//                .then()
//                .assertThat()
//                .statusCode(201)
//                .body("name", equalTo("NewPlayList1"),
//                        "description", equalTo("TestDescription"));
//    }
}
