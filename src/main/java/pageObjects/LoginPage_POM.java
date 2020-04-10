package pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.Architecture;
import utils.ReportLogManager.LogHelper;
import utils.common.WebUtils;

public class LoginPage_POM extends Architecture{
	private Logger l = LogHelper.getLogger(LoginPage_POM.class);
	public WebUtils ws=new WebUtils();
	
	public LoginPage_POM() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="email") public WebElement edtloginEmail;
	@FindBy(name="password") public WebElement edtloginPass;
	@FindBy(xpath="//div[text()='Login']") public WebElement btnLogin;
	
	public void launchApp() throws Exception
	{
		System.out.println(propConfig.get("url"));
		ws.launchURL(propConfig.get("url").toString());
		Thread.sleep(10000);
		l.info("Logging into app");
	}

}
