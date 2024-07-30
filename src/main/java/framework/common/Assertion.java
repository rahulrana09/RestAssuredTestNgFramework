package framework.common;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


public class Assertion {
    private static SoftAssert sAssert;

    public static void markAsFailure(String message) {
        getSoftAssert().fail(message);
    }

    public static SoftAssert getSoftAssert() {
        if (sAssert == null) {
            sAssert = new SoftAssert();
        }
        return sAssert;
    }

    public static void finalizeSoftAsserts() {
        try {
            getSoftAssert().assertAll();
        } catch (Exception e) {
            ;
        } finally {
            resetSoftAsserts();
        }
    }

    public static void resetSoftAsserts() {
        sAssert = null;
    }

    public static void failAndStopTest(String message) {
        markAsFailure(message);
        Assert.fail(message);
    }


    @Step
    public static boolean verifyEqual(Object actual, Object expected, String message, boolean... takeScreenShot) throws IOException {
        boolean isEqual = false;
        boolean takeSnap = takeScreenShot.length > 0 ? takeScreenShot[0] : true;
        if (actual instanceof BigDecimal && expected instanceof BigDecimal) {
            isEqual = ((BigDecimal) actual).compareTo((BigDecimal) expected) == 0;
        } else if (actual instanceof Integer || actual instanceof Boolean) {
            isEqual = actual.equals(expected);
        } else if (actual instanceof String && expected instanceof String) {
            isEqual = actual.equals(expected);
        } else if (actual instanceof List && expected instanceof List) {
            isEqual = actual.equals(expected);
        } else if (actual instanceof Double && expected instanceof Double) {
            isEqual = Double.compare((Double) actual, (Double) expected) == 0;
        }
        if (takeSnap) {
            ScreenShot.captureScreen();
        }
        if (isEqual) {
            return true;
        } else {
            markAsFailure("Failed to verify for equality!");
        }
        return false;
    }

    @Step
    public static boolean verifyContains(String actual, String expected, String message, boolean... takeScreenShot) throws IOException {
        boolean takeSnap = takeScreenShot.length > 0 ? takeScreenShot[0] : true;

        if (takeSnap) {
            ScreenShot.captureScreen();
        }

        if (actual.contains(expected)) {
            return true;
        } else {
            markAsFailure("Failure: Verify Action message Contain:- " + expected);
        }
        return false;
    }

    public static void raiseExceptionAndStop(Exception e) throws IOException {
        ScreenShot.captureScreen();
        e.printStackTrace();
        System.err.print("Stopping the execution, as an Exception has been Raised, Refer Reports for More Details!");
        Assertion.finalizeSoftAsserts();
        Assert.fail(e.getMessage());
    }


}
