package cn.edu.xmu.crms.service;
import cn.edu.xmu.crms.entity.Seminar;
import java.math.BigInteger;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:33
 */
public interface SeminarService {
    /**
     * 用于根据讨论课号码和班级号码查找讨论课信息
     *
     * @param seminarID 讨论课号码
     * @param classID 班级号码
     * @return Seminar 返回查找到的讨论课对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public Seminar getSeminarBySeminarIdAndClassId(BigInteger seminarID,BigInteger classID);
    /**
     * 用于根据小组号码查找小组并返回小组实例
     *
     * @param seminarID 小组号码
     * @return Seminar 对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public int getRoundOrderBySeminarId(BigInteger seminarID);
    /**
     * 用于根据讨论课号码和班级号码查找截止时间并返回截止时间的开始时间和结束时间
     *
     * @param seminarID 小组号码
     * @param classID 班级号码
     * @return List 返回查找到的截止日期，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/2 17:00
     */
    public List<String> getDeadLineBySeminarIdAndClassId(BigInteger seminarID,BigInteger classID);
    /**
     * 用于根据讨论课号码、小组号码和讨论课展示顺序报名讨论课
     *
     * @param seminarID 讨论课号码
     * @param teamID 班级号码
     * @param presentationOrder 讨论课展示顺序
     * @return boolean 返回查找到的讨论课对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/3 9:30
     */
    public boolean registSeminar(BigInteger seminarID,BigInteger teamID,Integer presentationOrder);
    /**
     * 用于根据讨论课号码、小组号码和讨论课展示顺序修改讨论课报名
     *
     * @param seminarID 讨论课号码
     * @param teamID 小组号码
     * @param presentationOrder 讨论课展示顺序
     * @return boolean 返回查找到的讨论课对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/3 12:00
     */
    public boolean modifySeminarRegist(BigInteger seminarID,BigInteger teamID,Integer presentationOrder);

    /**
     * 用于根据讨论课号码、小组号码取消讨论课报名
     *
     * @param seminarID 讨论课号码
     * @param teamID 小组号码
     * @return boolean 返回查找到的讨论课对象，若无记录则为null
     * @author LaiShaopeng
     * @date 2018/12/3 12:00
     */
    public boolean cancelSeminarRegit(BigInteger seminarID,BigInteger teamID);
}
