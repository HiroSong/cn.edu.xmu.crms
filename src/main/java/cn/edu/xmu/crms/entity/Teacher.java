package cn.edu.xmu.crms.entity;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:30
 */
public class Teacher extends User {
    private String role = "teacher";
    private String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
