package utils.DataDriver;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private static FileInputStream fis;
	private static Workbook workbook = null;
	private static Sheet sheet = null;
	private static Row row = null;
	private static Cell cell = null;
	private static short lastColNo = 0;
	private static short startColNo = 0;

	private static void createXlObj(String xlFilePath, String sheetName) {
		try {
			String fileType = FilenameUtils.getExtension(xlFilePath);
			fis = new FileInputStream(xlFilePath);
			System.out.println("Loading Excel Test Data file");
			if (fileType.equals("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileType.equals("xls")) {
				workbook = new HSSFWorkbook(fis);
			}
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			try {
				throw new Exception(e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void getRow(String instanceName) throws Exception {
		try {
			int rowCnt = sheet.getLastRowNum() - sheet.getFirstRowNum();
			boolean rowMatch = false;
			for (int rNum = 0; rNum <= rowCnt; rNum++) {
				try {
					if (sheet.getRow(rNum).getCell(0).getStringCellValue().trim()
							.equalsIgnoreCase(instanceName.trim())) {
						rowMatch = true;
						row = sheet.getRow(rNum);
						lastColNo = row.getLastCellNum();
						startColNo = row.getFirstCellNum();
						break;
					}
				} catch (NullPointerException e) {
					continue;
				}
			}
			if (!rowMatch) {
				throw new Exception("No test Data row found with Label=" + instanceName);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @param xlSheetPath
	 * @param xlSheetName
	 * @param instanceNo
	 * @return Map<String, String>
	 * @throws Exception
	 */

	public static Map<String, String> readXLTestDataSet(String xlSheetPath, String xlSheetName, String instanceNo) {
		Map<String, String> xlObj = null;
		try {
			xlObj = new HashMap<String, String>();
			createXlObj(xlSheetPath, xlSheetName);
			getRow(instanceNo);
			DataFormatter formatter = new DataFormatter();
			for (int cNum = startColNo; cNum < lastColNo; cNum++) {
				// Column [Name:Value]
				String cellName = sheet.getRow(row.getRowNum() - 1).getCell(cNum).getStringCellValue();
				cell = row.getCell(cNum);
				String cellData = formatter.formatCellValue(cell);
				xlObj.put(cellName, cellData);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return xlObj;
	}

	/*
	 * public static void main(String[] args) { Map<String, String> data =
	 * readXLTestDataSet("C:\\Users\\Satya\\Desktop\\DataTest.xls", "Sheet1",
	 * "instance2");
	 * 
	 * for (Entry<String, String> e : data.entrySet()) {
	 * System.out.println(e.getKey() + ":" + e.getValue()); }
	 * 
	 * }
	 */
}
