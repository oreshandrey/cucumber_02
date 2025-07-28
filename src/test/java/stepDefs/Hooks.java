package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.DBUtility;
import utilities.Driver;

import java.time.Duration;

public class Hooks {

    @Before
    public void setup(){
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Driver.getDriver().manage().window().maximize();
//        Driver.getDriver().manage().deleteAllCookies();
    }

    @Before("@db")
    public void setupDB(){
        DBUtility.createConnection();
    }

    @After
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            byte[] screenshotAs = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotAs,"image/png","failed");
        }

        Driver.closeDriver();
    }

    @After("@db")
    public void tearDownDB(){
        DBUtility.closeConnection();
    }


}
