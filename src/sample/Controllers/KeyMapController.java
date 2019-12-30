package sample.Controllers;

import HelperClasses.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sample.BaseClasses.MainBaseController;
import sample.DataClasses.Bus;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.TestDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

//import sample.DataClasses.DataInstance;

public class KeyMapController extends MainBaseController {



    boolean saveData = true;



    @FXML
    private GridPane keymap_gridpane;


    @FXML
    private JFXButton keymap_save_button;



    /*@FXML
    private JFXButton test_student;*/


    int subQuestions;
    ToggleGroup[] toggleGroups;
    int testIndex;
    TestDetails testDetails;
    int selectedIndex = 0;


    public void initialize() throws FileNotFoundException {

        //To set anchorPaneSize to maximum
        checkMaximize();
        setTollTip();

        final Tooltip openStudent_btn_tooltip = new Tooltip();
        openStudent_btn_tooltip.setText("Students");

        openStudents_btn.setTooltip(openStudent_btn_tooltip);


        final Tooltip openTest_btn_toltip = new Tooltip();
        openTest_btn_toltip.setText("Open Test");
        openTest_btn.setTooltip(openTest_btn_toltip);

        final Tooltip newTest_btn_tooltip = new Tooltip();
        newTest_btn_tooltip.setText("New Test");
        newTest_btn.setTooltip(newTest_btn_tooltip);


        final Tooltip openReport_btn_tooltip = new Tooltip();
        openReport_btn_tooltip.setText("Open report");
        openReport_btn.setTooltip(openReport_btn_tooltip);

        RadioButtonHelper.gridConstraints(keymap_gridpane, 85);
        keymap_save_button.setDisable(true);



        // Setting up various validation on feilds
        Image image = new Image(new FileInputStream("src//icons//error.png"));



        //Doing basic initialization activity
        testDetails = Bus.getInstance();
        if (testDetails.getSubQuestionList().get(0) != null) {
            generateRadioButtonOperation(0);
        }

        //Restrict user to enter in radio button without validation
        //keymap_gridpane.setDisable(true);
    }

    @FXML
    private void uploadTestKeyFromFile(ActionEvent event) {

        ArrayList<String> dataList = UploadFileHelper.openFileChooserAndReadFile();
        ArrayList<ArrayList<Character>> keyRowSet = UploadFileHelper.getKeyDetailsFromDataList(dataList);
        System.out.println(keyRowSet);
        testDetails.setKey(keyRowSet);
        //Saving Data
        DataBaseCommunication.convertJavaToJSON(testDetails);
    }



    private void generateRadioButtonOperation(int selectedIndex) {
        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        keymap_gridpane.getChildren().clear();
        int subQuestions = testDetails.getSubQuestionList().get(selectedIndex);
        toggleGroups = new ToggleGroup[subQuestions];
        try {
            toggleGroups = RadioButtonHelper.generateRadioButton(keymap_gridpane, subQuestions, toggleGroups, testDetails.getKey().get(selectedIndex));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        saveData = false;
        keymap_save_button.setDisable(false);

    }


    @FXML
    void saveRadioData(ActionEvent event) {
        //getting selected toggle


        saveDataOperations(selectedIndex);
        generateRadioButtonOperation(selectedIndex);
    }

    private void saveDataOperations(int selectedIndex) {
        ArrayList<Integer> selectedToggles = RadioButtonHelper.getSelectedToggles(toggleGroups);
        ArrayList<Character> key = RadioButtonHelper.getSelectedRadio(toggleGroups, selectedToggles);

        testDetails.getKey().set(selectedIndex, key);

        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        //Updating Student Evaluation
        testDetails.setStudentDetails(ListHelper.keyToStudents(key, testDetails.getStudentDetails(), selectedIndex, testDetails.getSubQuestionList().get(selectedIndex)));
        //Saving data
        DataBaseCommunication.convertJavaToJSON(testDetails);
        keymap_gridpane.getChildren().clear();

        saveData = true;
        keymap_save_button.setDisable(true);



        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
        snackbar.show(Constants.SAVE_SUCCESSFULL, Constants.TOAST_DURATION);

    }




    @FXML
    public void showBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, testDetails);
    }



}



