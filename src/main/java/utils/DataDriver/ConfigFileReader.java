package utils.DataDriver;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

import utils.ReportLogManager.LogHelper;

public class ConfigFileReader {
	private static  Logger l = LogHelper.getLogger(ConfigFileReader.class);
	private static Properties prop;
	private static final String propertyFilePath = ".\\src\\test\\resources\\configFiles\\Config.properties";

	/**
	 * @author Satya
	 * @return
	 * 
	 */
	public static Properties ConfigReader() {
		try {
			if (prop == null) {
				l.info("Loading Configuration Propeties file..");
				FileInputStream file = new FileInputStream(propertyFilePath);
				prop = new Properties();
				prop.load(file);
				file.close();
				return prop;
			}
		} catch (Exception e) {
			l.info("Loading Configuration Propeties file..Configuration file load issue: " + e.getMessage());
			throw new RuntimeException("Configuration file load issue: " + e.getMessage());
		}
		return prop;
	}

	/**
	 * @author Satya
	 * @param propKey
	 * @return String
	 */
	public static String getConfigProperty(String propKey) {
		String retrunVal = "";
		try {
			ConfigReader();
			retrunVal = prop.getProperty(propKey);
		} catch (Exception e) {
			throw new RuntimeException("Exception in reading properties for key:" + propKey + e.getMessage());
		}
		return retrunVal;
	}

	/**
	 * @author Satya
	 * @param propKey
	 * @param propVal
	 */
	public void setConfigProperty(String propKey, String propVal) {
		try {
			prop.setProperty(propKey, propVal);
		} catch (Exception e) {
			throw new RuntimeException("Exception in setting property.Key[" + propKey + "]:Value[" + propVal + "]");
		}
	}

}
