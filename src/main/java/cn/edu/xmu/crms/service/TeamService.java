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
     * 用于根据小组号码查找小组并返回小组实例
     *
     * @param teamID 小组号码
     * @return Team 返回查找到的对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public Team getTeamByNumber(BigInteger teamID);

    /**
     * 需要分成List<Team>和List<Member>
     * 用于根据课程号码查找所有小组并返回小组实例列表,包含小组信息和成员列表。
     *
     * @param courseID 课程号码
     * @return List<Team> 返回查找到的对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public Map<String,Object> getTeamListByCourseId(BigInteger courseID);

    /**
     * 需要分成List<Team>和List<Member>
     * 用于根据小组号码查找所有小组并返回小组实例列表
     *
     * @param teamID 小组号码
     * @return List<Student> 返回查找到的对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public List<Student> getStudentsByTeamId(BigInteger teamID);

    /**
     * 用于根据课程号码和学生号码查找所有小组并返回小组实例列表
     *
     * @param courseID 课程号码
     * @param studentID 学生号码
     * @return map 返回查找到的小组的信息，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public Map<String,Object> getTeamByCourseIdAndStudentId(BigInteger courseID,BigInteger studentID);
    /**
     * 用于根据讨论课号码和班级号码查找参与某次讨论课的小组
     *
     * @param seminarID 课程号码
     * @param classID 学生号码
     * @return List<Team> 返回查找到的小组列表的信息，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public List<Team> getPresentationTeamListBySeminarIdAndClassId(BigInteger seminarID,BigInteger classID);
}
