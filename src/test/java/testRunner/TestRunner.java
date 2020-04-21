package testRunner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import base.Architecture;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = {
		"stepDefinations" }, dryRun = false, monochrome = true, snippets = SnippetType.CAMELCASE, plugin = { "pretty",
				"com.cucumber.listener.ExtentCucumberFormatter:" + Architecture.ReportPath + "/ExtentReport.html",
				"json:" + Architecture.ReportPath + "/cucuJson.json" })

public class TestRunner {

	@AfterClass
	public static void tearDown() {
		new Architecture().reportGeneration();
		new Architecture().closeDriverEngine();
		new Architecture().popupReport();
	}
}
