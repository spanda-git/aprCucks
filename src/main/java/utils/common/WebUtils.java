package utils.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import base.Architecture;
import utils.ReportLogManager.LogHelper;

public class WebUtils extends Architecture {
	private Logger l = LogHelper.getLogger(Architecture.class);

	public void launchURL(String url) {
		driver.get(url);
		l.info("Launching Application url-" + url);
	}

	public void setData(WebElement ele, String dataToEnter) throws Exception {
		try {
			ele.clear();
			ele.sendKeys(dataToEnter);
			if (ele.getAttribute("value").equals(dataToEnter)) {
				l.info("Enter data [" + dataToEnter + "] in " + ele.toString());
			} else {
				l.error("Not able to enter data [" + dataToEnter + "] in " + ele.getAttribute("name"));
			}
		} catch (Exception e) {
			l.error("Exception while entering data in -" + ele.toString());
			throw new Exception(e.getMessage());
		}
	}

	public void click(WebElement ele) throws Exception {
		try {
			ele.click();
			l.info("clicked on-" + ele.toString());
		} catch (Exception e) {
			l.error("Exception while clicking on-" + ele.getText());
			throw new Exception(e.getMessage());
		}
	}

	public boolean verifyElementIsDisplayed(WebElement ele) {
		return ele.isDisplayed();
	}

	public String takeScreenshot() {
		String fullScreenshtPath = "";
		try {
			String dt = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
			File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			File directory = new File(Architecture.ReportPath + "\\Screenshot");
			if (!directory.exists()) {
				directory.mkdir();
				// If you require it to make the entire directory path including
				// parents,
				// use directory.mkdirs(); here instead.
				System.out.println(321);
			} else {
				System.out.println(123);
			}
			File destinationPath = new File(directory.getPath() +"\\"+ dt + ".png");

			FileHandler.copy(sourcePath, destinationPath);
			fullScreenshtPath = destinationPath.getAbsolutePath();
		} catch (Exception e) {
			l.error(e.getMessage());
		}
		return fullScreenshtPath;
	}

}
