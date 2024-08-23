package engineimpl;

import api.Cell;
import api.Engine;
import api.Spreadsheet;
import dtoPackage.CellDTO;
import dtoPackage.SpreadsheetDTO;
import generated.STLSheet;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import sheetimpl.SpreadsheetImpl;
import sheetimpl.cellimpl.coordinate.Coordinate;
import sheetimpl.cellimpl.coordinate.CoordinateFactory;

import java.io.File;
import java.lang.reflect.InaccessibleObjectException;
import java.util.HashMap;
import java.util.Map;

import static converter.SheetConverter.convertSheetToDTO;
import static sheetimpl.cellimpl.coordinate.CoordinateFactory.createCoordinate;


public class EngineImpl implements Engine {

    public static final int MAX_ROWS = 50;
    public static final int MAX_COLUMNS = 20;
    public static final int LOAD_VERSION = 1;

    private Map<Integer, Spreadsheet> spreadsheetsByVersions;
    int currentSpreadSheetVersion = LOAD_VERSION;


    @Override
    public void loadSpreadsheet(String filePath) throws Exception {
        validateXmlFile(filePath);
        STLSheet loadedSheetFromXML = loadSheetFromXmlFile(filePath);
        validateSTLSheet(loadedSheetFromXML);
        spreadsheetsByVersions = new HashMap<>();
        Spreadsheet loadedSpreadSheet = convertSTLSheet2SpreadSheet(loadedSheetFromXML);
        loadedSpreadSheet.setSheetVersion(LOAD_VERSION);
        spreadsheetsByVersions.put(LOAD_VERSION, convertSTLSheet2SpreadSheet(loadedSheetFromXML));
    }

    private Spreadsheet convertSTLSheet2SpreadSheet(STLSheet loadedSheetFromXML) {
        Spreadsheet spreadsheet = new SpreadsheetImpl();

        try {
            spreadsheet.init(loadedSheetFromXML);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("In file: " + loadedSheetFromXML.getName() + " - " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return spreadsheet;
    }

    private void validateSTLSheet(STLSheet loadedSheetFromXML) {
        int rows = loadedSheetFromXML.getSTLLayout().getRows();
        int columns = loadedSheetFromXML.getSTLLayout().getColumns();
        validateSheetLimits(rows, columns);
    }

    private void validateSheetLimits(int rows, int columns) {
        if (rows < 1 || rows > MAX_ROWS) {
            throw new IllegalArgumentException("Invalid number of rows: " + rows + ". Rows must be between 1 and 50.");
        }

        if (columns < 1 || columns > MAX_COLUMNS) {
            throw new IllegalArgumentException("Invalid number of columns: " + columns + ". Columns must be between 1 and 20.");
        }
    }

    private void validateXmlFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("File not found.");
        }
        if (!filePath.endsWith(".xml")) {
            throw new Exception(file.getName() + " is not an XML file.\n");
        }
    }

    private STLSheet loadSheetFromXmlFile(String filePath) {
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(STLSheet.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (STLSheet) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to load the data from the XML file."); // consider creating special Exception to throw.
        }
    }

    @Override
    public SpreadsheetDTO getSpreadsheetState() {
        validateSheetIsLoaded();
        return convertSheetToDTO(spreadsheetsByVersions.get(currentSpreadSheetVersion));
    }

    @Override
    public CellDTO getCellInfo(String cellId) {
        validateSheetIsLoaded();
        Coordinate cellCoordinate = createCoordinate(cellId);
        Cell cellToDTO = spreadsheetsByVersions.get(currentSpreadSheetVersion).getCell(cellCoordinate);

        return new CellDTO(cellToDTO.getOriginalValue(),
                cellToDTO.getEffectiveValue(),
                cellToDTO.getLastModifiedVersionVersion());
    }

    @Override
    public void updateCell(String cellId, String newValue) {
        validateSheetIsLoaded();
        Coordinate coordinate = CoordinateFactory.createCoordinate(cellId);
        Spreadsheet currentSpreadsheet = spreadsheetsByVersions.get(currentSpreadSheetVersion).copySheet();

        try {
            currentSpreadSheetVersion++;
            currentSpreadsheet.setSheetVersion(currentSpreadSheetVersion);
            spreadsheetsByVersions.put(currentSpreadSheetVersion,currentSpreadsheet);
            spreadsheetsByVersions.get(currentSpreadSheetVersion).setCell(coordinate, newValue);

        } catch (Exception e) {
            spreadsheetsByVersions.remove(currentSpreadSheetVersion);
            currentSpreadSheetVersion--;
            throw e;
        }
    }

    @Override
    public void exitProgram() {
        System.exit(0);
    }

    @Override
    public int getCurrentVersion() {
        validateSheetIsLoaded();
        //TODO
        return 0;
    }

    private void validateSheetIsLoaded() {
        if (spreadsheetsByVersions.get(LOAD_VERSION) == null) {
            throw new InaccessibleObjectException("File is not loaded yet.\n");
        }
    }

    @Override
    public Map<Integer, SpreadsheetDTO> getSpreadSheetVersionHistory() {
        validateSheetIsLoaded();
        Map<Integer, SpreadsheetDTO> spreadSheetByVersionDTO = new HashMap<>();

        for (Map.Entry<Integer, Spreadsheet> entry : spreadsheetsByVersions.entrySet()) {
            spreadSheetByVersionDTO.put(entry.getKey(), convertSheetToDTO(spreadsheetsByVersions.get(entry.getKey())));
        }

        return spreadSheetByVersionDTO;
    }

    @Override
    public Map<Integer, Spreadsheet> getSpreadsheetsByVersions() {
        return spreadsheetsByVersions;
    }
}
