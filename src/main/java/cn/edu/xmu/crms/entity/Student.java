package cn.edu.xmu.crms.entity;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:32
 */
public class Student extends User {
    private String role = "student";
    private String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}