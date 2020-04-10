package stepDefinations.cucuHook;

import org.apache.log4j.Logger;
import com.cucumber.listener.Reporter;

import base.Architecture;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import stepDefinations.stepDefs.Login;
import utils.DataDriver.ConfigFileReader;
import utils.ReportLogManager.LogHelper;

public class Hook extends Architecture{
	private Logger l = LogHelper.getLogger(Login.class);

	@Before
	public void before(Scenario scenario){
		l.info("******STARTING OF SCENARIO-" + scenario.getName() + "******");
		propConfig = ConfigFileReader.ConfigReader();
		startDriverEngine(propConfig.getProperty("browser").toString());
		System.out.println("driver out"+driver);
		
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
