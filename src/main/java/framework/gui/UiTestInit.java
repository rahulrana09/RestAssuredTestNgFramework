package framework.gui;

import com.aventstack.extentreports.ExtentTest;
import framework.common.Assertion;
import framework.common.DataFactory;
import framework.globalConstants.FilePath;
import framework.gui.utils.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class UiTestInit {
    public static String reportDirPath, screenShotDir, screenshotPath;

    @Step
    @BeforeClass
    public void setup() {
        // load users
        DataFactory.loadAutUser();

        // reports
        String tDate = java.time.LocalDate.now().toString();
        reportDirPath = FilePath.dirReports + tDate;
        screenShotDir = reportDirPath + "/screenshots";
        screenshotPath = "../" + tDate + "/screenshots";

        File reportDir = new File(reportDirPath);
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }

        File screenShotDir = new File(reportDirPath + "/screenshots");
        if (!screenShotDir.exists()) {
            screenShotDir.mkdir();
        }
    }

    @AfterClass
    public void afterClass() {
        DriverFactory.getInstance().quitDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        System.out.print("START TEST: " + method.getName() + "\n");
        try {
            Assertion.resetSoftAsserts();
        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult
                                    result, Method method) throws IOException {
        try {
            String dependentOnMethod = method.getDeclaredAnnotations()[0].toString().split("dependsOnMethods=")[1].split(",")[0].replace("[", "").replace("]", "");
            if (result.getStatus() == ITestResult.SUCCESS) {
                Allure.step("TEST         >---> " + result.getName() + " - Executed Successfully");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                Allure.step("TEST         >---> " + result.getName() + " - Execution Failed");

                // update the Execution log files
//                updateFailExecutionLog(result.getInstanceName(), result.getName(), dependentOnMethod);

            } else if (result.getStatus() == ITestResult.SKIP) {
                Allure.step("TEST         >---> " + result.getName() + " - Execution Skipped");
             //   ExtentTest test = pNode.createNode(result.getName() + ": Test Case Execution Skipped!");
               // test.skip("Test Case Skipped");

                // update the Execution log files
               // updateFailExecutionLog(result.getInstanceName(), result.getName(), dependentOnMethod);
            } else {
//                ExtentTest test = pNode.createNode(result.getName() + ": Add Test Case Execution Type in TestInit class");
//                test.skip("Test Case type");
            }

            Allure.step("END TEST     >---> " + result.getName());
            Allure.step("---/");
//            extent.flush();
            Assertion.resetSoftAsserts();
//            ConfigInput.isAssert = true;
//            ConfigInput.isConfirm = true;
        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
        }

    }

    public void markTestAsFailure(Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}
