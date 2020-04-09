package testRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},features = "src/main/resources/features", glue = {
		"stepDefs" }, dryRun = false, monochrome = true, snippets = SnippetType.CAMELCASE,
				)

public class TestRunner {

}
