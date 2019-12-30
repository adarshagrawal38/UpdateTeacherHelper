package sample.Controllers;

import HelperClasses.Constants;
import HelperClasses.ListHelper;
import HelperClasses.RadioButtonHelper;
import HelperClasses.UploadFileHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.BaseClasses.MainBaseController;
import sample.DataClasses.Bus;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StudentsRecordsController extends MainBaseController {


    @FXML
    private JFXTextField studentNameTxt;


    @FXML
    private JFXTextField rollNumberTxt;

    @FXML
    private GridPane keymap_gridpane;

    @FXML
    private JFXButton keymap_save_button;


    @FXML
    private JFXListView<VBox> student_listView;
    @FXML
    private JFXButton openKey_btn;

    @FXML
    private JFXButton openTest_btn;

    @FXML
    private JFXButton openReport_btn;

    @FXML
    private JFXButton newTest_btn;

    @FXML
    private  JFXListView listView;

    TestDetails testDetails;
    ToggleGroup[] toggleGroups;

    int studentIndexCount = 0;
    int sub_ques;
    boolean dataSaved = true;



    private void loadStudentDetails(int index) {
        displayBasicStudentInfo(index);
    }

    public void initialize() {
        checkMaximize();
        setTollTip();
        //Getting data from bus
        testDetails = Bus.getInstance();

        //Restrict invalid input in radio button
//        keymap_gridpane.setDisable(true);

        //Setting up tooltip text

        final Tooltip openStudent_btn_tooltip = new Tooltip();
        openStudent_btn_tooltip.setText("Key");
        openKey_btn.setTooltip(openStudent_btn_tooltip);


        final Tooltip openTest_btn_toltip = new Tooltip();
        openTest_btn_toltip.setText("Open Test");
        openTest_btn.setTooltip(openTest_btn_toltip);

        final Tooltip newTest_btn_tooltip = new Tooltip();
        newTest_btn_tooltip.setText("New Test");
        newTest_btn.setTooltip(newTest_btn_tooltip);


        final Tooltip openReport_btn_tooltip = new Tooltip();
        openReport_btn_tooltip.setText("Open report");
        openReport_btn.setTooltip(openReport_btn_tooltip);


/*
        RadioButtonHelper.gridConstraints(keymap_gridpane, 85);
       keymap_save_button.setDisable(true);*/
        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        Image image = null;
        try {
            image = new Image(new FileInputStream("src//icons//error.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        validator1.setMessage(Validation.EMPTY_FEILD);
        validator2.setMessage(Validation.EMPTY_FEILD);
        validator1.setIcon(new ImageView(image));
        validator2.setIcon(new ImageView(image));

        studentNameTxt.getValidators().add(validator1);
        rollNumberTxt.getValidators().add(validator2);


        loadStudentDetails(0);

        /*Label name = new Label("test1");
        Label rollNumber = new Label("1");
        rollNumber.setAlignment(Pos.BASELINE_RIGHT);
        rollNumber.setMaxWidth(Double.MAX_VALUE);
        VBox vBox = new VBox(name, rollNumber);
        vBox.setSpacing(20);
        listView.getItems().add(vBox);*/

        init_StudentList();


    }

    @FXML
    public void uploadStudentDetailsFromFile() {

        ArrayList<String> dataList = UploadFileHelper.openFileChooserAndReadFile();
        ArrayList<StudentDetails> studentDetails = UploadFileHelper.getStudentDetailsFromDataList(dataList);
        testDetails.setStudentDetails(studentDetails);
        //Save Data
        DataBaseCommunication.convertJavaToJSON(testDetails);

        System.out.println(studentDetails);

    }

    private void init_StudentList() {

        student_listView.getItems().clear();
        if (testDetails != null && testDetails.getStudentDetails() != null) {
            for (int i = 0; i < testDetails.getStudentDetails().size(); i++) {
                StudentDetails studentDetails = testDetails.getStudentDetails().get(i);
                if (studentDetails != null && studentDetails.getRollNo() != null && studentDetails.getName() != null) {
                    try {



                        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                        snackbar.show("Name : " + studentDetails.getName(), Constants.TOAST_DURATION);

                        Label name = new Label(studentDetails.getName());
                        //name.setGraphic(new ImageView(new Image(new FileInputStream("src//icons//student.png"))));
                        Label rollNumber = new Label(studentDetails.getRollNo());
                        rollNumber.setAlignment(Pos.BASELINE_RIGHT);
                        rollNumber.setMaxWidth(Double.MAX_VALUE);

                        /*Label name = new Label("test : " + i);
                        name.setGraphic(new ImageView(new Image(new FileInputStream("src//icons//student.png"))));
                        Label rollNumber = new Label(""+i);
                        rollNumber.setAlignment(Pos.BASELINE_RIGHT);
                        rollNumber.setMaxWidth(Double.MAX_VALUE);*/


                        VBox vBox = new VBox(name, rollNumber);
                        vBox.setSpacing(20);
                        student_listView.getItems().add(vBox);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    student_listView.setExpanded(true);
                    student_listView.depthProperty().set(3);

                }
            }
        }

        /*for (int i=0;i<3;i++) {
            Label name = new Label("test : " + i);
            try {
                name.setGraphic(new ImageView(new Image(new FileInputStream("src//icons//student.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Label rollNumber = new Label(""+i);
            rollNumber.setAlignment(Pos.BASELINE_RIGHT);
            rollNumber.setMaxWidth(Double.MAX_VALUE);


            VBox vBox = new VBox(name, rollNumber);
            vBox.setSpacing(20);
            student_listView.getItems().add(vBox);
        }*/


/*
        Label name = new Label("Test 1");
        VBox vBox = new VBox(name);

        student_listView.getItems().add(vBox);*/
    }

    // Proceed button code
    @FXML
    void generateRadioButton(ActionEvent event) {

        if ( studentNameTxt.validate() && rollNumberTxt.validate()) {
            int selectedIndex = 0;
            sub_ques = testDetails.getSubQuestionList().get(selectedIndex);
            setRadioButton(sub_ques, selectedIndex);

            dataSaved = false;
            keymap_save_button.setDisable(false);

            //Allow user to edit user response
            keymap_gridpane.setDisable(false);

        }
    }


    private void setRadioButton(int sub_ques, int selectedIndex) {

        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        keymap_gridpane.getChildren().clear();

        try {
            toggleGroups = new ToggleGroup[sub_ques];
            if (testDetails.getStudentDetails().get(studentIndexCount).getResponse() != null) {
                toggleGroups = RadioButtonHelper.generateRadioButton(keymap_gridpane, sub_ques, toggleGroups, testDetails.getStudentDetails().get(studentIndexCount).getResponse().get(selectedIndex));

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void saveRadioData(ActionEvent event) {

        String studentName = studentNameTxt.getText();
        String rollno = rollNumberTxt.getText();

        // Saving data
        testDetails.getStudentDetails().get(studentIndexCount).setName(studentName);
        testDetails.getStudentDetails().get(studentIndexCount).setRollNo(rollno);
        saveDataOperations(0);

        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
        snackbar.show(Constants.SAVE_SUCCESSFULL, Constants.TOAST_DURATION);

        keymap_save_button.setDisable(true);

        //init_StudentList();
    }


    private void saveDataOperations(int selectedIndex) {
        ArrayList<Integer> selectedToggles = RadioButtonHelper.getSelectedToggles(toggleGroups);
        if (selectedToggles != null) {
            ArrayList<Character> response = RadioButtonHelper.getSelectedRadio(toggleGroups, selectedToggles);
            ArrayList<Integer> evaluation;
            ArrayList<Character> key = testDetails.getKey().get(selectedIndex);
            testDetails.getStudentDetails().get(studentIndexCount).getResponse().set(selectedIndex, response);
            //Evaluation will be done inside KeyToStudents
            if (testDetails.getSubQuestionList().get(selectedIndex) != null) {
                testDetails.setStudentDetails(ListHelper.keyToStudents(key, testDetails.getStudentDetails(), selectedIndex, testDetails.getSubQuestionList().get(selectedIndex)));
            }
            DataBaseCommunication.convertJavaToJSON(testDetails);

            RadioButtonHelper.deselectRadioButton(keymap_gridpane);
            keymap_gridpane.getChildren().clear();

            dataSaved = true;

            init_StudentList();

        }

    }


    @FXML
    void nextStudent(ActionEvent event) {

        checkDataSave();
        if (studentIndexCount < testDetails.getNumberOfStudent() - 1) {
            studentIndexCount++;
            displayBasicStudentInfo(studentIndexCount);
        } else {
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("No more students are available", Constants.TOAST_DURATION);
            //studentIndexCount = 0;
        }


        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        keymap_gridpane.getChildren().clear();
    }

    private void displayBasicStudentInfo(int index) {

        studentNameTxt.setText("");
        rollNumberTxt.setText("");


        if (testDetails.getStudentDetails().get(index).getName() != null) {
            studentNameTxt.setText(testDetails.getStudentDetails().get(index).getName());
        }
        if (testDetails.getStudentDetails().get(index).getRollNo() != null) {
            rollNumberTxt.setText(testDetails.getStudentDetails().get(index).getRollNo());
        }
        if (testDetails.getSubQuestionList() != null && testDetails.getSubQuestionList().get(0) != null) {
            setRadioButton(testDetails.getSubQuestionList().get(0), 0);
            //generateRadioButton(new ActionEvent());
        }


    }



    @FXML
    void getStudentDetails(MouseEvent event) {
        checkDataSave();
        int i = student_listView.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            studentIndexCount = i;
            loadStudentDetails(i);
        }
    }

    @FXML
    void showBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, testDetails);
    }


    private void checkDataSave() {
        if (!dataSaved) {
            System.out.println("Something not saved");
            saveRadioData(new ActionEvent());
        }
    }



}
