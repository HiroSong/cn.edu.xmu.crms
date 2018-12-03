package cn.edu.xmu.crms.service;
import cn.edu.xmu.crms.entity.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:33
 */
public interface SeminarService {
    /**
     * 用于根据课程号码查找该课程讨论课轮次列表返回
     *
     * @param CourseID  课程号
     * @return Round[] 返回查找到的对象，若无记录则为null
     * @author Yanxuehuan
     * @date 2018/12/3 10:50
     */
    public List<Round> getRoundByCourseID(BigInteger CourseID);

    /**
     * 用于根据课程号码查找该课程讨论课轮次列表返回
     *
     * @param ClassID  班级号
     * @return Round[] 返回查找到的对象，若无记录则为null
     * @author Yanxuehuan
     * @date 2018/12/3 10:50
     */
    public List<BigInteger> getRoundByClassID(BigInteger ClassID);

}
