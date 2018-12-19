package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.entity.EduClass;

import java.math.BigInteger;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:08
 */
public interface EduClassService {
    /**
     * 用于根据课程号码查找班级并返回班级实例
     *
     * @param id 课程号码
     * @return ArrayList<> 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/12/1 14:05
     */
    List<EduClass> listEduClassByCourseID(BigInteger id);
}
