package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    @FXML
    private TextField average;

    @FXML
    private DatePicker birth_date;

    @FXML
    private Button button_add;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_edit;

    @FXML
    private Button button_save;

    @FXML
    private TextArea comments;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> gender_box;

    @FXML
    private Label label_average;

    @FXML
    private Label label_birth_date;

    @FXML
    private Label label_comments;

    @FXML
    private Label label_email;

    @FXML
    private Label label_gender;

    @FXML
    private Label label_list_student;

    @FXML
    private Label label_mark;

    @FXML
    private Label label_name;

    @FXML
    private TextField path;

    @FXML
    private Label label_photo;

    @FXML
    private Label label_student_details;

    @FXML
    private ListView<Student> list_student;

    @FXML
    private TextField mark;

    @FXML
    private TextField name;

    @FXML
    private ImageView photo;

    DBManager manager;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> gvalues = new ArrayList<String>();
        gvalues.add("Male");
        gvalues.add("Female");
        ObservableList<String> gender = FXCollections.observableArrayList(gvalues);
        gender_box.setItems(gender);

        manager = new DBManager();
        fetchStudents();

        list_student.getSelectionModel().selectedItemProperty().addListener(e->
                displayStudentDetails(list_student.getSelectionModel().getSelectedItem()));

        displayAverageMark();
    }
    public void displayAverageMark() {
        average.setText(String.valueOf(df.format(manager.averageMark())));
    }
    private void displayStudentDetails(Student selectedStudent) {
        try {
            if(selectedStudent!=null){
                name.setText(selectedStudent.getName());
                gender_box.setValue(selectedStudent.getGender());
                email.setText(selectedStudent.getEmail());
                birth_date.setValue(selectedStudent.getBirth_date());
                if (new Double(String.valueOf(selectedStudent.getMark())) >= 0.0) mark.setText(String.valueOf(selectedStudent.getMark()));
                else mark.setText("");
                comments.setText(selectedStudent.getComments());
                path.setText(selectedStudent.getPhoto());
                try {
                    Image image = new Image(selectedStudent.getPhoto());
                    photo.setImage(image);
                } catch (Exception e) {
                    //Image image = new Image("https://www.salonlfc.com/wp-content/uploads/2018/01/image-not-found-scaled.png");
                    //photo.setImage(image);
                    System.out.println(e);
                }
                changeBtn(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void fetchStudents() {
        List<Student> listStudents = manager.loadStudents();
        if (listStudents != null) {
            ObservableList<Student> students;
            students = FXCollections.observableArrayList(listStudents);
            list_student.setItems(students);
        }
        changeBtn(true);
    }
    public void onNew(){
        list_student.getSelectionModel().clearSelection();
        this.name.setText("");
        this.gender_box.setValue(null);
        this.email.setText("");
        this.birth_date.setValue(null);
        this.mark.setText("");
        this.comments.setText("");
        this.path.setText("");
        displayAverageMark();
        changeBtn(false);
    }
    public void onSave() {
        Student s = new Student(name.getText(),gender_box.getValue(), email.getText(), birth_date.getValue(), path.getText(), (mark.getText().trim().isEmpty()) ? -1.0 : new Double(mark.getText()), comments.getText());
        System.out.println(path.getText());
        manager.addStudent(s);
        fetchStudents();
        displayAverageMark();
        changeBtn(true);
    }
    public void onCancel(){
        list_student.getSelectionModel().selectFirst();
        changeBtn(true);
    }
    public void onDelete(){
        if(list_student.getSelectionModel().getSelectedItem()!=null){
            manager = new DBManager();
            manager.deleteStudent(list_student.getSelectionModel().getSelectedItem());
            fetchStudents();
        }
        displayAverageMark();
        changeBtn(true);
    }
    public void onEdit() {
        if(list_student.getSelectionModel().getSelectedItem()!=null){
            manager = new DBManager();
            manager.updateStudent(list_student.getSelectionModel().getSelectedItem(), name.getText(),gender_box.getValue(), email.getText(), birth_date.getValue(), path.getText(), (mark.getText().trim().isEmpty()) ? -1.0 : new Double(mark.getText()), comments.getText());
            fetchStudents();
        }
        displayAverageMark();
        changeBtn(true);
    }
    public void changeBtn(boolean sc) {
        this.button_save.setDisable(sc);
        this.button_cancel.setDisable(sc);
        this.button_edit.setDisable(!sc);
        this.button_delete.setDisable(!sc);
    }
}