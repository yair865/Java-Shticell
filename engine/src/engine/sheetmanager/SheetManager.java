package engine.sheetmanager;

import dto.dtoPackage.CellDTO;
import dto.dtoPackage.SpreadsheetDTO;
import dto.dtoPackage.coordinate.Coordinate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface SheetManager {

    void loadSpreadsheet(String filePath) throws Exception;

    String loadSpreadsheet(InputStream fileContent);

    SpreadsheetDTO getSpreadsheetState();

    SpreadsheetDTO pokeCellAndReturnSheet(String cellId);

    CellDTO getCellInfo(String cellId);

    List<CellDTO> updateCell(String cellId, String newValue , String userName);

    void exitProgram();

    void loadSystemFromFile(String fileName) throws IOException, ClassNotFoundException;

   void saveSystemToFile(String fileName) throws IOException;

    Map<Integer, SpreadsheetDTO> getSpreadSheetVersionHistory();

    SpreadsheetDTO getSpreadSheetByVersion(int version);

    Integer getCurrentVersion();

    SpreadsheetDTO getSpreadsheetByVersion(int version);

    void setSingleCellTextColor(String cellId, String textColor);

    void setSingleCellBackGroundColor(String cellId, String backGroundColor);

    void addRangeToSheet(String rangeName, String rangeDefinition);

    void removeRangeFromSheet(String name);

    List<Coordinate> getRangeByName(String rangeName);
    SpreadsheetDTO sort(String cellsRange, List<Character> selectedColumns);

    SpreadsheetDTO filterSheet(Character selectedColumn, String filterArea, List<String> selectedValues);

    List<String> getUniqueValuesFromColumn(char columnNumber);

    String getUserName();

    void setUserName(String userName);

    String getSheetTitle();

    int getSheetNumberOfRows();

    int getSheetNumberOfColumns();

    List<CellDTO> getCellsThatHaveChanged();

    SpreadsheetDTO getExpectedValue(Coordinate coordinate, String value);
}
