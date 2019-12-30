package sample.Controllers;

import LocationHelper.ScenesLocations;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DialogPopUp {

    public static void basicInfoDialog(StackPane stackPane, TestDetails testDetails) {


        String displayInfo[] = {"Teacher Name\t: " + testDetails.getTeacherName(),
                "Test Name\t: " + testDetails.getTestName(),
                "Institute\t\t: " + testDetails.getInstitute(),
                "Date\t\t\t: " + testDetails.getDateTime()};

        Label label[] = new Label[displayInfo.length];

        for (int i = 0; i < displayInfo.length; i++) {
            label[i] = new Label(displayInfo[i]);
            label[i].setFont(new Font("Segoi UI", 15));
        }

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text("Basic Info"));

        VBox vBox = new VBox(label);
        vBox.setSpacing(20);
        layout.setBody(vBox);

        JFXButton button = new JFXButton("Okay");
        button.setPrefWidth(100);
        button.getStyleClass().add("btn-dialog");

        layout.setActions(button);

        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });


        dialog.show();
    }

    public void openTestDialog(StackPane stackPane, TestDetails testDetails) {


        String displayInfo[] = {"Teacher Name\t: " + testDetails.getTeacherName(),
                "Test Name\t: " + testDetails.getTestName(),
                "Institute\t\t: " + testDetails.getInstitute(),
                "Date\t\t\t: " + testDetails.getDateTime()};

        Label label[] = new Label[displayInfo.length];

        for (int i = 0; i < displayInfo.length; i++) {
            label[i] = new Label(displayInfo[i]);
            label[i].setFont(new Font("Segoi UI", 15));
        }

        JFXDialogLayout layout = new JFXDialogLayout();

        VBox vBox = new VBox(label);
        vBox.setSpacing(20);
        layout.setBody(vBox);

        JFXButton student_btn = new JFXButton("Student");
        student_btn.setPrefWidth(100);
        student_btn.getStyleClass().add("btn-dialog");

        JFXButton key_btn = new JFXButton("Key");
        key_btn.setPrefWidth(100);
        key_btn.getStyleClass().add("btn-dialog");

        JFXButton report_btn = new JFXButton("Report");
        report_btn.setPrefWidth(100);
        report_btn.getStyleClass().add("btn-dialog");


        layout.setActions(student_btn, key_btn, report_btn);

        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
        student_btn.setOnAction(e -> openNewFrame(e, student_btn, key_btn, report_btn, testDetails));
        key_btn.setOnAction(e -> openNewFrame(e, student_btn, key_btn, report_btn, testDetails));
        report_btn.setOnAction(e -> openNewFrame(e, student_btn, key_btn, report_btn, testDetails));


        dialog.show();
    }

    public static void closeAlert(StackPane stackPane) {


        Label label = new Label("Are you sure you want to exit");
        label.setFont(new Font("Segoi UI", 15));

        JFXDialogLayout layout = new JFXDialogLayout();

        VBox vBox = new VBox(label);
        vBox.setSpacing(20);
        layout.setBody(vBox);

        JFXButton yes_btn = new JFXButton("Yes");
        yes_btn.setPrefWidth(100);
        yes_btn.getStyleClass().add("btn-dialog");

        JFXButton cancel_btn = new JFXButton("Cancel");
        cancel_btn.setPrefWidth(100);
        cancel_btn.getStyleClass().add("btn-dialog");

        layout.setActions(yes_btn, cancel_btn);

        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
        yes_btn.setOnAction(e -> System.exit(0));
        cancel_btn.setOnAction(e -> dialog.close());


        dialog.show();

    }

    private void openNewFrame(ActionEvent e, JFXButton student_btn, JFXButton key_btn, JFXButton report_btn, TestDetails testDetails) {

        // Sending about file to be open in bus
        Bus.setInstance(testDetails);
        FXMLLoader loader = new FXMLLoader();
        Parent parent = null;
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        if (e.getSource() == student_btn) {
            //location changed test src
            System.out.println("Trying to open student details");
            System.out.println("Working Directory = " +
                    System.getProperty("user.dir"));
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);
            loader.setLocation(getClass().getResource(ScenesLocations.OPEN_STUDENT_SCENE));
            try {
                parent = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        }
        if (e.getSource() == key_btn) {
            loader.setLocation(getClass().getResource(ScenesLocations.KEY_SCENE));
            try {
                parent = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        }
        if (e.getSource() == report_btn) {
            //Open report frame
            loader.setLocation(getClass().getResource(ScenesLocations.REPORT_OPTION_SCENE));
            try {
                parent = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        }
    }


}
