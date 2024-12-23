package component.sheetview.left.range;

import component.sheetview.left.LeftController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RangeController {

    @FXML
    private TextField coordinatesTextField;

    @FXML
    private TextField rangeNameTextField;

    @FXML
    private Button saveRangeButton;

    private LeftController leftController;

    public void setLeftController(LeftController leftController) {
        this.leftController = leftController;
    }

    @FXML
    void SaveRangeListener(ActionEvent event) {
        String rangeName = rangeNameTextField.getText();
        String coordinates = coordinatesTextField.getText();

        if (rangeName.isEmpty() || coordinates.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        if (leftController != null) {
            leftController.pushRangeToSheet(rangeName,coordinates);
        }

        Stage stage = (Stage) saveRangeButton.getScene().getWindow();
        stage.close();
    }
}
