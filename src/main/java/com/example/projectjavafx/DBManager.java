package com.example.projectjavafx;

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
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/student?serverTimezone=Europe%2FParis", "root","LtIbzOuzxz1397!");
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
            myStmt.setString(2, student.getName());
            myStmt.setString(3, student.getGender());
            myStmt.setString(4, student.getEmail());
            if (student.getBirth_date() != null) myStmt.setString(5, String.valueOf(student.getBirth_date()));
            else myStmt.setString(5, "0001-01-01");
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
            myStmt.setString(1, String.valueOf(new_name));
            myStmt.setString(2, String.valueOf(new_gender));
            myStmt.setString(3, String.valueOf(new_email));
            myStmt.setString(4, String.valueOf(new_birth_date));
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
