package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.entity.Teacher;
import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:07
 */
public interface TeacherService {
    /**
     * 用于根据教师id号码查找教师并返回教师实例
     *
     * @param id 教师号码
     * @return Teacher 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/12/01 15:07
     */
    public Teacher getTeacherByTeacherID(BigInteger id);
}
