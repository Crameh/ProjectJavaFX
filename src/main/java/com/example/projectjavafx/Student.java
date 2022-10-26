package com.example.projectjavafx;
import java.time.LocalDate;


public class Student {
    private int id;
    private String name;
    private String gender;
    private String email;
    private LocalDate birth_date;
    private String photo;
    private double mark;
    private String comments;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public String getPhoto() {
        return photo;
    }

    public double getMark() {
        return mark;
    }

    public String getComments() {
        return comments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Student(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public Student(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
    public Student(String name, String gender, String email, LocalDate birth_date, String photo, Double mark, String comments) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.birth_date = birth_date;
        this.photo = photo;
        this.mark = mark;
        this.comments = comments;
    }

    public Student(int id, String name, String gender, String email, LocalDate birth_date, String photo, Double mark, String comments) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.birth_date = birth_date;
        this.photo = photo;
        this.mark = mark;
        this.comments = comments;
    }

    public String toString() { return id + " | " + name; }
}
