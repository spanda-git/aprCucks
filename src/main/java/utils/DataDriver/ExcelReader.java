package utils.DataDriver;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	private static Workbook workbook;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;
	private static short lastColNo;
	private static short startColNo;

	private static void createXlObj(String xlFilePath, String sheetName) {
		try {
			String fileType = FilenameUtils.getExtension(xlFilePath);
			fis = new FileInputStream(xlFilePath);

			if (fileType.equals("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileType.equals("xls")) {
				workbook = new HSSFWorkbook(fis);
			}
			sheet = workbook.getSheet(sheetName);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getRow(String instanceName) {
		try {
			int rowCnt = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for (int rNum = 0; rNum <= rowCnt; rNum++) {
				try {
					if (sheet.getRow(rNum).getCell(0).getStringCellValue().trim()
							.equalsIgnoreCase(instanceName.trim())) {
						row = sheet.getRow(rNum);
						lastColNo = row.getLastCellNum();
						startColNo = row.getFirstCellNum();
						break;
					}
				} catch (NullPointerException e) {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> readXLTestDataSet(String xlSheetPath, String xlSheetName, String instanceNo) {
		Map<String, String> xlObj = new HashMap<String, String>();
		try {
			createXlObj(xlSheetPath, xlSheetName);
			getRow(instanceNo);
			DataFormatter formatter = new DataFormatter();

			for (int cNum = startColNo; cNum < lastColNo; cNum++) {
				// Column Name & Value
				String cellName = sheet.getRow(row.getRowNum() - 1).getCell(cNum).getStringCellValue();
				cell = row.getCell(cNum);
				String cellData = formatter.formatCellValue(cell);
				xlObj.put(cellName, cellData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xlObj;
	}

	public static void main(String[] args) {
		Map<String, String> data = readXLTestDataSet("C:\\Users\\Satya\\Desktop\\DataTest.xls", "Sheet1", "instance2");

		for (Entry<String, String> e : data.entrySet()) {
			System.out.println(e.getKey() + ":" + e.getValue());
		}

	}

}
