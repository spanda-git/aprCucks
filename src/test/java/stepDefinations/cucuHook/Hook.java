package stepDefinations.cucuHook;

import org.apache.log4j.Logger;
import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import stepDefinations.stepDefs.Login;
import utils.ReportLogManager.LogHelper;

public class Hook {
	private static final Logger l = LogHelper.getLogger(Login.class);

	@Before
	public void before(Scenario scenario) {
		l.info("******STARTING OF SCENARIO-" + scenario.getName() + "******");
		for (String tag : scenario.getSourceTagNames()) {
			if (tag.toLowerCase().contains("author")) {
				String tempAuthor = tag.split("=")[1];
				Reporter.assignAuthor(tempAuthor);
				break;
			}
		}
	}

	@After
	public void after(Scenario scenario) {
		l.info("$$$$$$ENDING OF SCENARIO-[STATUS:" + scenario.getStatus().toUpperCase() + "]$$$$$$");
	}

}
