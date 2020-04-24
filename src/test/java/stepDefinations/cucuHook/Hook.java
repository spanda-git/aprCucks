package stepDefinations.cucuHook;

import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.testng.Assert;
import base.Architecture;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import utils.DataDriver.ConfigFileReader;
import utils.DataDriver.ExcelReader;
import utils.ReportLogManager.LogHelper;

public class Hook extends Architecture {
	private Logger l = LogHelper.getLogger(Hook.class);
	private static Set<String> strSet = new HashSet<String>();

	@Before
	public void before(Scenario scenario) {

		l.info("******STARTING OF SCENARIO-" + scenario.getName() + "******");
		propConfig = ConfigFileReader.ConfigReader();
		startDriverEngine(propConfig.getProperty("browser"));

		for (String tag : scenario.getSourceTagNames()) {
			if (tag.toLowerCase().contains("instance")) {
				if (!strSet.contains(tag)) {
					strSet.add(tag);
					l.info("New Instance found-"+tag);
					try {
						dictObj = ExcelReader.readXLTestDataSet(Architecture.dataSheetPath, "Sheet1",
								tag.replace("@", ""));
						l.info(dictObj.entrySet());
					} catch (Exception e) {
						l.error(e.getMessage());
						Assert.fail(e.getMessage());
					}
					break;
				} 
			}
		}
	}

	@After
	public void after(Scenario scenario) {
		l.info("$$$$$$ENDING OF SCENARIO-[STATUS:" + scenario.getStatus().toUpperCase() + "]$$$$$$");
	}

}
