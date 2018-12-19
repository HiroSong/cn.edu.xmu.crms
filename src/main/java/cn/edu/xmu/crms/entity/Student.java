package cn.edu.xmu.crms.entity;

import java.io.Serializable;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:32
 */
public class Student extends User implements Serializable {
    private String role = "student";
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", number=" + number +
                ", name=" + name +
                ", email=" + email +
                ", password=" + password +
                ", noticeGap=" + noticeGap +
                ", role=" + role +
                '}';
    }
}
