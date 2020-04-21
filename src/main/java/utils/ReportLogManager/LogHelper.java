package utils.ReportLogManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import base.Architecture;

public class LogHelper extends Architecture{
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
