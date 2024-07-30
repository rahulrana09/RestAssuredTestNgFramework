package framework;

import com.github.javafaker.Faker;

public class FakerUtils {
    public static String generateName(){
        return "PlayList" + new Faker().regexify("[A-Za-z0-9,_]{10}");
    }

    public static String generateDescription(){
        return "Description" + new Faker().regexify("[A-Za-z0-9,_]{15}");
    }
}
