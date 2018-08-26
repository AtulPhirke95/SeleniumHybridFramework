package util.readexcel;

import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * This is the execution class which will execute all test cases in Chrome.
 *
*/
public class ExecuteTestCaseTestNG extends Keyword {
	public WebDriver webDriverObject;

	/**
	 * Getter for WebDriver object.
	 *
	*/
	public WebDriver getWebDriverObject() {
		return webDriverObject;
	}

	/**
	 * Setter for WebDriver object.
	 *
	*/
	public void setWebDriverObject(WebDriver webDriverObject) {
		this.webDriverObject = webDriverObject;
	}

	/**
	 * Constructor for ExecuteTestCaseTestNG class. Sets system chrome webdriver path.
	 *
	*/
	/*ExecuteTestCaseTestNG() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/User/Downloads/accenture_testing/HybridSelenium-master/HybridFramework_V2.0/lib/drivers/chromedriver.exe");
	}*/	
	
	/**
	 * Setter for WebDriver object. WebDriver will be initialized with url String passed.
	 *
	*/
	public void setWebDriverObject(WebDriver webDriverObject, String url) {
		this.webDriverObject.get(url);
		this.webDriverObject.manage().timeouts().pageLoadTimeout(-100, TimeUnit.SECONDS);
		this.webDriverObject = webDriverObject;
	}

	/**
	 * TestNG Data Provider for Test Cases
	 *
	 * @name = "Passing List of TestCases"
	 * @return Iterator to the Linked List of Test Cases created in Test Suite
	 */
	
	

	@DataProvider(name = "Passing List of TestCases")
	public Iterator<Object[]> createDataProvider() {
		// Initialize test suite
		CreateTestSuite objCreateTestSuite = new CreateTestSuite();
		LinkedList<TestCaseClass> testSuite = objCreateTestSuite.createTestSuite();
		objCreateTestSuite.displayTuple(testSuite);
		LinkedList<Object[]> dpObject = new LinkedList<Object[]>();
		for (TestCaseClass TC : testSuite) {
			dpObject.add(new Object[] { TC });
		}
		return dpObject.iterator();
	}

	/**
	 * This is the Test Class, which runs all test cases present in Test Suite
	 *
	 * dataProvider = "Passing List of TestCases"
	 */

	@Test(dataProvider = "Passing List of TestCases")
	public void test(TestCaseClass testCase) {
		TestStepClass testStep;

		ExecuteTestCaseTestNG exeTCObject = new ExecuteTestCaseTestNG();
		ReadPropertiesFile readPropertiedFile = new ReadPropertiesFile();
		Reporter.log("\n***Test Case : " + testCase.getTestCaseName());
		for (int j = 0; j < testCase.getTestCase().size(); j++) {
			System.out.println("test step " + (j + 1));
			testStep = testCase.getTestStep(j);

			if (j == 0) {
				if(testStep.getData().equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver", readPropertiedFile.getValue("ChromeDriverPath"));
					webDriverObject = new ChromeDriver();
				}
				else if(testStep.getData().equalsIgnoreCase("firefox")){
					System.setProperty("webdriver.gecko.driver", readPropertiedFile.getValue("GeckoDriverPath"));
					webDriverObject = new FirefoxDriver();
				}
				else if(testStep.getData().equalsIgnoreCase("ie")){
					System.setProperty("webdriver.ie.driver", readPropertiedFile.getValue("IEDriverPath"));
					webDriverObject =new InternetExplorerDriver();
				}
			}else if(j == 1){
				exeTCObject.setWDObj(webDriverObject,testStep.getData());
				Reporter.log("\n**Step : " + (j+1) + "Initialize chrome browser with url : " + testStep.getData());
				// wdObj.get(testStep.getData());
			} else {
				try {
					exeTCObject.performAction(testStep, exeTCObject.getWDObj());
					Reporter.log("\n**Step : " + j+1 + "--Step Executed : " + testStep + "**");
				} catch (NumberFormatException | InterruptedException e) {
					// TODO Auto-generated catch block
					Reporter.log("\n**Error at step " + j+1 + "**");
					e.printStackTrace();
				}
			}
		}
		//exeTCObject.getWDObj().close();
	}

	/**
	 * afterSuite() will contain code to be executed after entire test suite is
	 * executed
	 *
	 */

	@AfterSuite
	public void afterSuite() {
		//webDriverObject.close();
	}

}
