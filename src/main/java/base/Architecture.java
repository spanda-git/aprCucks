package base;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.cucumber.listener.Reporter;

import utils.ReportLogManager.LogHelper;

public class Architecture {
	public static Properties propConfig;
	public static WebDriver driver;
	private Logger l = LogHelper.getLogger(Architecture.class);
	private static final String driverPath = "drivers";
	private static final String htmlConfigPath = "src\\test\\resources\\configFiles\\extent-config.xml";
	public static Map<String, String> dictObj = new HashMap<String, String>();
	public static String failedScreenshotPath = "test-output\\Screenshots\\";

	public void startDriverEngine(String browserType) {
		try {
			if (driver == null) {
				l.info("Initializing [" + browserType + "] driver server..");
				if (browserType.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",
							driverPath + "\\chromedriver_win32\\chromedriver.exe");
					ChromeOptions options = new ChromeOptions();
					options.setPageLoadStrategy(PageLoadStrategy.NONE);
					driver = new ChromeDriver(options);
				} else if (browserType.equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver",
							driverPath + "\\iedriverserver_win32\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
				} else {
					l.error(browserType + "-browser type is invalid");
					throw new Exception(browserType + "-browser type is invalid");
				}
				driver.manage().window().maximize();
			} else {
				l.info("Working on existing [" + browserType + "] driver server..");
			}
		} catch (Exception e) {
			l.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	public void closeDriverEngine() {
		try {
			driver.quit();
			driver = null;
			l.info("Closed all driver processes");
		} catch (Exception e) {
			l.error("Exception while closing driver processes." + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	public void closeDriver() {
		try {
			driver.close();
			l.info("Closed current focccused driver");
		} catch (Exception e) {
			l.error("Exception while closing driver processes." + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	public void reportGeneration() {
		try {
			l.info("Loading Extend Report Configuration...");
			File f = new File(htmlConfigPath);
			Reporter.loadXMLConfig(f);
			Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
			Reporter.setSystemInfo("Application Name", "DEMO");
			Reporter.setSystemInfo("Operating System Type", System.getProperty("os.name"));
			Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
			Reporter.setSystemInfo("Environment", propConfig.getProperty("environment"));
			Reporter.setSystemInfo("Selenium", "3.7.0");
			Reporter.setSystemInfo("Maven", "3.5.2");
			Reporter.setSystemInfo("Java Version", "1.8.0_151");
			Reporter.setTestRunnerOutput("Test Execution Cucumber Report");
			l.info("Generated HTML report in test-output folder");
		} catch (Exception e) {
			l.error("Exception while generating html reports." + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	public void popupReport() {
		try {
			System.out.println(propConfig.getProperty("displayReportAfterExec"));
			if (propConfig.getProperty("displayReportAfterExec").trim().equalsIgnoreCase("YES")) {
				l.info("Trigger HTML report in Browser is set to-YES");
				Thread.sleep(5000);
				String extReportPath = System.getProperty("user.dir") + File.separator+ "test-output\\ExtentReport.html";
				File htmlFile = new File(extReportPath);
				Desktop.getDesktop().browse(htmlFile.toURI().normalize());
			} else {
				l.info("Trigger HTML report in Browser is set to-NO");
			}
		} catch (Exception e) {
			l.error("Exception in triggring report-" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}