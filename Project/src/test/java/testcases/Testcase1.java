package testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import utils.Exception_Screenshot;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;
//import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.LoggerHandler;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import pages.Tricentis_Login;
import pages.Tricetis_Register;
import utils.EventHandler;
import utils.Reporter;
import utils.Screenshot;
public class Testcase1 extends Base {
    java.util.logging.Logger log =  LoggerHandler.getLogger();
    
    ExtentReports reporter = Reporter.generateExtentReport();;
    
    @Test(priority = 1)
    public void Register() throws IOException {
        try {
            ExtentTest test = reporter.createTest("Registeration Page", "Execution for registeration");
            //e = new EventHandler();
            driver.get(prop.getProperty("url") + "/Register");
            log.info("Browser Navigated to the Register Page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIME));
            Tricetis_Register register = PageFactory.initElements(driver, Tricetis_Register.class);
            register.gen_female();
            log.info("Female Button clicked in the Register Page");
            register.FirstName("AAAAAA");
            log.info("First Name Entered in the Register Page");
            register.LastName("BBBBBB");
            log.info("Last Name Entered in the Register Page");
            register.Email("test@demo.com");
            log.info("Email Entered in the Register Page");
            register.Password("Test123$");
            log.info("Password Entered in the Register Page");
            register.ConfirmPassword("Test123$");
            log.info("Confirm Password Entered in the Register Page");
            Screenshot.getScreenShot("Register_Screenshot");
            log.info("Screenshot taken in the Register Page");
            register.Register_Btn();
            log.info("Register Button Clicked");
            test.log(Status.PASS, "Registeration Success");
        } catch (Exception ex) {
            Exception_Screenshot screenshotHandler = new Exception_Screenshot();
            screenshotHandler.captureScreenshot(driver, "Registeration_Exception");
            log.info("Exception occurred: " + ex.getMessage());
            
            ex.printStackTrace();
        }
        WebDriverListener listener = new EventHandler();
		driver = new EventFiringDecorator<>(listener).decorate(driver);
		return;
    }

    @Test(priority = 2, dataProvider = "testData")
    public void Valid_Login_TC(String uid, String pwd) throws IOException {
        try {
            ExtentTest test = reporter.createTest("Login Page", "Execution for Login");
            driver.get(prop.getProperty("url") + "/Login");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIME));
            Tricentis_Login login = PageFactory.initElements(driver, Tricentis_Login.class);
            login.username(uid);
            log.info("username read from excel and entered in the field");
            login.password(pwd);
            log.info("password read from excel and entered in the field");
            login.Login();
            log.info("Login Button Clicked");
            test.log(Status.PASS, "Login Success");
            Screenshot.getScreenShot("Login_Screenshot");
            log.info("Screenshot taken");
        } catch (Exception ex) {
            // Handle any exceptions here
            log.info("Exception occurred: " + ex.getMessage());
            Exception_Screenshot screenshotHandler = new Exception_Screenshot();
            screenshotHandler.captureScreenshot(driver, "Login_Exception");
            ex.printStackTrace();
        }
    }
    @Test(priority = 3)

public void TC_Vote_003() throws InterruptedException, IOException

{
try{
ExtentTest test = reporter.createTest("Vote Page", "Execution for Vote ");

log.info("Test Started");

driver.get("https://demowebshop.tricentis.com/");

driver.findElement(By.id("pollanswers-2")).click();

driver.findElement(By.id("vote-poll-1")).click();

Thread.sleep(500);

String actualResult =driver.findElement(By.id("block-poll-vote-error-1")).getText();

Assert.assertEquals(actualResult, "Only registered users can vote.");

Screenshot.getScreenShot(("VoteTest"));
test.log(Status.PASS, "Vote Success");
}
catch(Exception ex){
    Exception_Screenshot screenshotHandler = new Exception_Screenshot();
    screenshotHandler.captureScreenshot(driver, "Vote_without_Login_Exception");
}

}

    @DataProvider(name = "testData")
    public Object[][] readTestData() throws IOException {
        String excelFilePath = System.getProperty("user.dir") + "/src/test/java/resources/Testdata.xlsx";
        String sheetName = "Sheet1";

        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                data[i - 1][j] = cell.getStringCellValue();
            }
        }

        return data;
    }

    @BeforeMethod
    public void beforeMethod() throws MalformedURLException {
        openBrowser();
        WebDriverListener listener = new EventHandler();
        driver = new EventFiringDecorator<>(listener).decorate(driver);

    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
        reporter.flush();
        log.info("Browser closed");
    }
}
