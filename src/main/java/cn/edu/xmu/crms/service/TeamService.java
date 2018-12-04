package cn.edu.xmu.crms.service;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;

import java.util.List;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:06
 */
public interface TeamService {

    /**
     * 用于根据小组号码查找所有小组并返回小组实例列表
     *
     * @param teamID 小组号码
     * @return List<Student> 返回查找到的对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public List<Student> listMemberByTeamID(BigInteger teamID);

    /**
     * 用于根据课程号码查找所有小组并返回小组列表
     *
     * @param courseID 课程号码
     * @return List<Team> 返回查找到的对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public List<Team> listTeamByCourseID(BigInteger courseID);

    /**
     * 用于根据课程号码查找所有小组并返回小组成员列表/按小组
     *
     * @param courseID 课程号码
     * @return List<Student> 返回查找到的对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public Map<BigInteger,List<Student>> listMemberByCourseID(BigInteger courseID);

    /**
     * 用于根据课程号码和学生号码查找所有小组并返回小组实例
     *
     * @param courseID 课程号码
     * @param studentID 学生号码
     * @return Team 返回查找到的小组的信息，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public Team getTeamByCourseIDAndStudentID(BigInteger courseID,BigInteger studentID);
    /**
     * 用于根据讨论课号码和班级号码查找参与某次讨论课的小组
     *
     * @param seminarID 课程号码
     * @param classID 学生号码
     * @return List<Team> 返回查找到的小组列表的信息，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public List<Team> listPresentationTeamBySeminarIDAndClassID(BigInteger seminarID,BigInteger classID);
}
