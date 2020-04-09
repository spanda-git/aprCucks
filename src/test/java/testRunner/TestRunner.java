package testRunner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import mercy.base.Architecture ;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = {
		"stepDefinations" }, dryRun = false, monochrome = true, snippets = SnippetType.CAMELCASE, plugin = { "pretty",
				"com.cucumber.listener.ExtentCucumberFormatter:test-output/ExtentReport.html", "json:test-output/cucuJson.json"})

public class TestRunner {

	@AfterClass
	public static void tearDown() {
		new Architecture().reportGeneration();
		new Architecture().tearDown();
	}
}
