package framework.api.utils;

import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.jayway.jsonpath.internal.JsonFormatter.prettyPrint;

public class Utils {
    public static Map jsonToMap(String json) {
        return new Gson().fromJson(json, Map.class);
    }

    public static <T> T clone(T t) {
        Gson gson = new Gson();
        String json = gson.toJson(t);
        return (T) gson.fromJson(json, t.getClass());
    }

    public static <T> List<T> list(T... values) {
        return Arrays.asList(values);
    }

    public static <T> List<T> list(int numberOfElements, T value) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < numberOfElements; i++) {
            list.add(clone(value));
        }
        return list;
    }

    public static String json(String json, JsonPathOperation... operations) {
        DocumentContext context = JsonPath.parse(json);
        for (JsonPathOperation operation : operations) {
            context = operation.perform(context);
        }
        return prettyPrint(context.jsonString());
    }
}
