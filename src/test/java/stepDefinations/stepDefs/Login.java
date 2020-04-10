package stepDefinations.stepDefs;

import org.apache.log4j.Logger;
import com.cucumber.listener.Reporter;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pageObjects.LoginPage_POM;
import utils.ReportLogManager.LogHelper;

public class Login extends LoginPage_POM {
	private Logger l = LogHelper.getLogger(Login.class);

	@Given("User launches application")
	public void user_launches_application() throws Exception {
		l.info("step starts");
		launchApp();
	}

	@Given("veryify home page displayed")
	public void veryify_home_page_displayed() throws Exception {

		boolean dis = ws.verifyElementIsDisplayed(edtloginEmail);
		if (dis) {
			l.error("page not displayed");
			Reporter.addScreenCaptureFromPath(ws.takeScreenshot());
			throw new AssertionError("page not displayed");
		} else {
			l.error("page displayed");
		}
	}

	@Then("^user enters User name and password$")
	public void user_enters_User_name_and_password() throws Throwable {
		ws.setData(edtloginEmail, "Satyajit");
		ws.setData(edtloginPass, "demo");
		ws.click(btnLogin);
	}

	@Then("^just click on nothing$")
	public void justClickOnNothing() throws Throwable {

	}
	
	@Then("^user closes driver$")
	public void userClosesDriver() throws Throwable {
		closeDriverEngine();
		Thread.sleep(5000);
	}
	

}
