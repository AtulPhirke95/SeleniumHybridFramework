package util.readexcel;

public class FetchDataFromExcel {
	//FetchDataFromExcel objFetchDataFromExcel = new FetchDataFromExcel();
	Xls_Reader objXLSReaderOne = new Xls_Reader(new ReadPropertiesFile().getValue("ExcelPathForFetchingData"));
	String tempCellValue;
	public String fetchingData(String S){
		if(S.startsWith("(")){
			int indexOfstartOfOpeningBracket = S.indexOf("(");
			int indexOfstartOfComma = S.indexOf(",");
			int indexOfstartOfEndingBracket = S.indexOf(")");
			int tempColumn =Integer.parseInt(S.substring((indexOfstartOfOpeningBracket+1), indexOfstartOfComma));
			int tempRow = Integer.parseInt(S.substring((indexOfstartOfComma+1), indexOfstartOfEndingBracket));
			tempCellValue = objXLSReaderOne.getCellData("Sheet1",tempColumn ,tempRow);
		}
		return tempCellValue;
		
	}
}