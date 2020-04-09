package utils.ReportLogManager;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogHelper {
	private static final String loggerFilePath = ".\\src\\test\\resources\\configFiles" + File.separator
			+ "log4j.properties";
	private static boolean root = false;

	public static Logger getLogger(Class<?> cls) {
		try {
			if (root) {
				return Logger.getLogger(cls.getName());
			}
			PropertyConfigurator.configure(loggerFilePath);
			root = true;
			return Logger.getLogger(cls);
		} catch (Exception e) {
			throw new RuntimeException("Logger Class error:" + e.getMessage());
		}
	}

}
