package sample.Controllers;

import HelperClasses.ListGenerationHelper;
import HelperClasses.TableViewHelper;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

import java.util.List;

public class DisplayStudentResponseController extends BaseController {
/*
    @FXML
    private JFXButton back_btn;*/

    @FXML
    private JFXToggleButton binaryToggle;

    @FXML
    private Label studentResponseLabel;

    @FXML
    private VBox tableVbox;
/*
    @FXML
    private StackPane stack_pane;*/



    int count = 0;
    TestDetails testDetails;

    public void initialize() {
        testDetails = Bus.getInstance();

        //Create table
        /*ListGenerationHelper.questionList();
        ListGenerationHelper.studentResponseInList();
        ListGenerationHelper.studentRollNumberList();
        ListGenerationHelper.rollNumberAndResponseList();
        ListGenerationHelper.allStudentResponseForEachOptionList();
        ListGenerationHelper.studentEvaluationInList();*/


        //Adding Columns
        List<String> questionLabels = ListGenerationHelper.questionList();
        questionLabels.add(0, "RollNo.");
        TableViewHelper.insertDataInColumn(tableView, questionLabels);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        //Adding rows
        insertRow(ListGenerationHelper.rollNumberAndResponseList());
    }

    private void insertRow(List<List<String>> displayList) {

        // Inserting rows
        tableView.getItems().clear();
        TableViewHelper.insertDataInRow(tableView, displayList);

    }

    @FXML
    void displayBasicTestInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, testDetails);
    }

    @FXML
    void closeEvent(MouseEvent event) {
        DialogPopUp.closeAlert(stackPane);
    }

    @FXML
    void toggleActionEvent(ActionEvent event) {

        if (binaryToggle.isSelected()) {
            insertRow(ListGenerationHelper.rollNumberAndEvaluationList());
        } else {
            insertRow(ListGenerationHelper.rollNumberAndResponseList());
        }

    }
}
