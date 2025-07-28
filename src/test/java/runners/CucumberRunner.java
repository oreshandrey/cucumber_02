package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)

@CucumberOptions(
        tags = "@quick",
        features = "src/test/resources",
        glue = "stepDefs",
        stepNotifications = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {"pretty",
                  "html:target/built-in-report/built-in-report.html",
                  "json:target/cucumber.json",
                  "rerun:target/failed.txt"
                }
//        ,dryRun = true

)


public class CucumberRunner {

}
