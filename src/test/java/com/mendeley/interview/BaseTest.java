package com.mendeley.interview;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reports.ExtentReportsImp;

import java.lang.reflect.Method;

public class BaseTest {

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        // Extent Report Initialization
        ExtentReportsImp.initializeReport();

    }

    @BeforeMethod
    public void setup(Method method) {
        // Extent Report Initialization
        String testName = method.getName();
        String testDescription = method.getAnnotation(Test.class).testName();
        ExtentReportsImp.startTestExecution(testName, testDescription);

    }

    @AfterMethod
    public void tearDown(ITestResult result, Method method) {
        ExtentReportsImp.addDetails(method);
        if (ITestResult.FAILURE == result.getStatus()) {
            String testName = result.getName();
            ExtentReportsImp.failTest(testName);

        } else if (ITestResult.SUCCESS == result.getStatus()) {
            String testName = result.getName();
            ExtentReportsImp.passTest(testName);

        } else if (ITestResult.SKIP == result.getStatus()) {
            String testName = result.getName();
            ExtentReportsImp.skipTest(testName);
        }
    }

    @AfterSuite()
    public void afterSuite() {
        ExtentReportsImp.flushReports();
    }
}
