package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)

@CucumberOptions(

        features = "@target/failed.txt",
        glue = "stepDefs",
        stepNotifications = true,
        plugin = {"pretty",
                "html:target/built-in-report/rerun-built-in-report.html",
        }

)


public class FailedScenariosRunner {

}
