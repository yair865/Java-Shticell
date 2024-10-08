package engine.engineimpl;

import dto.dtoPackage.CellDTO;
import dto.dtoPackage.SpreadsheetDTO;
import engine.api.Coordinate;
import engine.api.EffectiveValue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Engine {

    void loadSpreadsheet(String filePath) throws Exception;

    SpreadsheetDTO getSpreadsheetState();

    SpreadsheetDTO pokeCellAndReturnSheet(String cellId);

    CellDTO getCellInfo(String cellId);

    void updateCell(String cellId, String newValue);

    void exitProgram();

    void loadSystemFromFile(String fileName) throws IOException, ClassNotFoundException;

   void saveSystemToFile(String fileName) throws IOException;

    Map<Integer, SpreadsheetDTO> getSpreadSheetVersionHistory();

    SpreadsheetDTO getSpreadSheetByVersion(int version);

    Integer getCurrentVersion();

    void setSingleCellTextColor(String cellId, String textColor);

    void setSingleCellBackGroundColor(String cellId, String backGroundColor);

    void addRangeToSheet(String rangeName, String rangeDefinition);

    void removeRangeFromSheet(String name);

    List<Coordinate> getRangeByName(String rangeName);
    SpreadsheetDTO sort(String cellsRange, List<Character> selectedColumns);

    SpreadsheetDTO filterSheet(Character selectedColumn, String filterArea, List<String> selectedValues);

    List<String> getUniqueValuesFromColumn(char columnNumber);
}
