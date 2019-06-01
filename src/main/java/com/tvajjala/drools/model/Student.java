package com.tvajjala.drools.model;

public class Student {

    private int marks;

    private double attendance;

    private Grade grade;

    private String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public enum Grade {
        A, B, C
    }

    @Override
    public String toString() {
        return "Student{" +
                "marks=" + marks +
                ", attendance=" + attendance +
                ", grade=" + grade +
                ", msg='" + msg + '\'' +
                '}';
    }
}
