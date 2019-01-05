package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName ShareTeamApplication
 * @Description 共享组队申请
 * @Author Hongqiwu
 * @Date 2018/12/17 15:35
 **/
public class ShareTeamApplication {
    private BigInteger id;
    private Integer status;
    private Course mainCourse;
    private Course subCourse;
    private Teacher subCourseTeacher;
    private Teacher mainCourseTeacher;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Course getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(Course mainCourse) {
        this.mainCourse = mainCourse;
    }

    public Course getSubCourse() {
        return subCourse;
    }

    public void setSubCourse(Course subCourse) {
        this.subCourse = subCourse;
    }

    public Teacher getSubCourseTeacher() {
        return subCourseTeacher;
    }

    public void setSubCourseTeacher(Teacher subCourseTeacher) {
        this.subCourseTeacher = subCourseTeacher;
    }

    public Teacher getMainCourseTeacher() {
        return mainCourseTeacher;
    }

    public void setMainCourseTeacher(Teacher mainCourseTeacher) {
        this.mainCourseTeacher = mainCourseTeacher;
    }
}
