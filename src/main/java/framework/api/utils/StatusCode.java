package framework.api.utils;

public enum StatusCode {
    CODE_200(200, ""),
    CODE_201(201, ""),
    CODE_400(401, "Invalid access token");

    public final int code;
    public final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
