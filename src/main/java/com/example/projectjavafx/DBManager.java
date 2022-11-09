package com.example.projectjavafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DBManager {

    public List<Student> loadStudents(){
        List<Student> studentAll= new ArrayList<Student>();
        Connection myConn= this.Connector();
        try {
            Statement myStmt= myConn.createStatement();
            String sql = "select * from Student";
            ResultSet myRs= myStmt.executeQuery(sql);
            while (myRs.next()) {
                Student s= new Student(myRs.getInt("id"),myRs.getString("name"),
                        myRs.getString("gender"), myRs.getString("email"),
                        myRs.getDate("birth_date").toLocalDate(), myRs.getString("photo"),
                        myRs.getDouble("mark"), myRs.getString("comments"));

                studentAll.add(s);
            }
            this.close(myConn, myStmt, myRs);
            return studentAll;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection Connector(){
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/student?serverTimezone=Europe%2FParis", "root","root");
            return connection;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Double averageMark() {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "SELECT AVG(mark) as avg FROM Student WHERE mark >= 0;";
            myStmt = myConn.prepareStatement(sql);
            myRs= myStmt.executeQuery(sql);
            System.out.println("average updated !");
            String ans = "";
            if (myRs.next()) {
                ans = myRs.getString(1);
            }
            return Double.parseDouble(ans);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return 0.0;
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }
    public void addStudent(Student student) {
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "INSERT INTO Student (id, name, gender, email, birth_date, photo, mark, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, String.valueOf(student.getId()));
            if (student.getName().length() != 0){
                myStmt.setString(2, student.getName());
            }
            else {
                alertMessage("Incorrect name","Please write a name");
                throw new IllegalArgumentException();
            }
            if (student.getGender() != null){
                myStmt.setString(3, student.getGender());
            }
            else {
                alertMessage("Incorrect gender","Please choose a gender");
                throw new IllegalArgumentException();
            }
            if (student.getEmail() != "") {
                if (!student.getEmail().contains("@") || (!student.getEmail().contains(".com") && !student.getEmail().contains(".fr"))) {
                    myStmt.setString(4, "");
                } else {
                    myStmt.setString(4, student.getEmail());
                }
            } else {
                myStmt.setString(4, student.getEmail());
            }
            if (student.getBirth_date() != null) {
                if (student.getBirth_date().isAfter(LocalDate.of(1979, 12, 31)) && student.getBirth_date().isBefore(LocalDate.of(2001, 01, 01))){
                    myStmt.setString(5, String.valueOf(student.getBirth_date()));
                } else {
                    alertMessage("Incorrect Birth Date", "The birth date has to be between 1980 and 2000");
                    myStmt.setString(5, "1980-01-01");
                }
            }
            else myStmt.setString(5, "1980-01-01");
            myStmt.setString(6, student.getPhoto());
            myStmt.setString(7, String.valueOf(student.getMark()));
            myStmt.setString(8, student.getComments());
            myStmt.execute();
            System.out.println("add");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }

    }
    public void deleteStudent(Student student) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "DELETE FROM Student WHERE id = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, String.valueOf(student.getId()));
            myStmt.execute();
            System.out.println("delete");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }

    public void alertMessage(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("I understood the error");
            }
        });
    }

    public void updateStudent(Student student, String new_name, String new_gender, String new_email, LocalDate new_birth_date, String new_photo, Double new_mark, String new_comment) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "UPDATE Student SET " +
                    "name = ?, gender = ?, email = ?, birth_date = ?, photo = ?, mark = ?, comments = ? " +
                    "WHERE id = ?";
            myStmt = myConn.prepareStatement(sql);
            if (new_name.length() != 0){
                myStmt.setString(1, new_name);
            }
            else {
                alertMessage("Incorrect name","Please write a name");
                throw new IllegalArgumentException();
            }
            if (new_gender != null){
                myStmt.setString(2, new_gender);
            }
            else {
                alertMessage("Incorrect gender","Please choose a gender");
                throw new IllegalArgumentException();
            }
            if (!new_email.contains("@")){
                alertMessage("Incorrect email","Your email should contain '@'");
                throw new IllegalArgumentException();
            }
            else {
                if (!(new_email.contains(".com") || new_email.contains(".fr"))) {
                    alertMessage("Incorrect email","Your email should contain a correct domain name");
                    throw new IllegalArgumentException();
                }
                myStmt.setString(3, String.valueOf(new_email));
            }
            if (new_birth_date != null) {
                if (new_birth_date.isAfter(LocalDate.of(1979, 12, 31)) && new_birth_date.isBefore(LocalDate.of(2001, 01, 01))){
                    myStmt.setString(4, String.valueOf(new_birth_date));
                } else {
                    alertMessage("Incorrect Birth Date", "The birth date has to be between 1980 and 2000");
                    myStmt.setString(4, "1980-01-01");
                }
            } else myStmt.setString(4, "1980-01-01");
            myStmt.setString(5, String.valueOf(new_photo));
            myStmt.setString(6, String.valueOf(new_mark));
            myStmt.setString(7, String.valueOf(new_comment));
            myStmt.setString(8, String.valueOf(student.getId()));
            myStmt.execute();
            System.out.println("update");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }
}
