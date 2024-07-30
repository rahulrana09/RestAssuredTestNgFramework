package framework.gui.features;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
//import framework.pageObjects.HomePage;
//import framework.utility.common.Assertion;

import java.io.IOException;

public class Navigation {
    private static ExtentTest pNode;

    public static Navigation init(ExtentTest t1) {
        pNode = t1;
        return new Navigation();
    }

    /**
     * Navigate the left navigation bar
     *
     * @param linkName
     * @throws IOException
     */
    public void leftNavigation(String linkName, String ExpectedPageTitle) throws Exception {
        Markup m = MarkupHelper.createLabel("Left Navigation: " + linkName, ExtentColor.BLUE);
        pNode.info(m);
//        try {
//            HomePage hPage = new HomePage(pNode);
//            hPage.clickOnLeftMenu(linkName);
//            Assertion.verifyEqual(hPage.getCurrentPageName(),
//                    ExpectedPageTitle,
//                    "Verify successful left Navigation",
//                    pNode);
//        } catch (Exception e) {
//            Assertion.raiseExceptionAndStop(e, pNode);
//        }
    }


}
