package spotify.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import spotify.utils.ConfigLoader;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager {
    private static String accessToken = null;
    private static Instant expiry_time;

    @Step
    public synchronized static String getToken() {
        try {
            if (accessToken == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("Renewing token...");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryTimeFromRes = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryTimeFromRes - 300);
            } else {
                System.out.println("Token is good to use");
            }
        } catch (Exception e) {
            throw new RuntimeException("ABORT! Failed to get Token!");
        }
        return accessToken;
    }

    @Step
    public static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        Response response = ApiUtils.postAccount(formParams);
        if (response.statusCode() != 200) {
            throw new RuntimeException("ABORT! Renew Token Failure!");
        }
        return response;
    }
}
