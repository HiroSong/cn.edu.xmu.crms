package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.entity.Seminar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


/**
 * @ClassName SeminarMapper
 * @Description 有关数据库中讨论课信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Component
public interface SeminarMapper {
    /**
     * 通过klassID获取SeminarID
     *
     * @param klassID 班级ID
     * @return List<BigInteger> 讨论课ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:40
     */
    List<BigInteger> listSeminarsIDByKlassID(BigInteger klassID);

    /**
     * 通过seminarID获取Seminar对象
     *
     * @param seminarID 班级ID
     * @return Seminar 讨论课对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Seminar getSeminarBySeminarID(BigInteger seminarID);
    /**
     * 通过courseID获取子课程ID
     *
     * @param courseID 课程ID
     * @return BigInteger 子课程ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listSubCoursesIDByCourseID(BigInteger courseID);
    /**
     * 通过courseID获取主课程ID
     *
     * @param courseID 课程ID
     * @return BigInteger 主课程ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listMainCoursesIDByCourseID(BigInteger courseID);
    /**
     * 通过主和子课程ID获取共享ID
     *
     * @param mainCourseID 主课程ID
     * @param subCourseID 子课程ID
     * @return BigInteger 共享ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getSeminarShareIDByMainAndSubCourseID(BigInteger mainCourseID, BigInteger subCourseID);
    /**
     * 通过roundID获取讨论课ID列表
     *
     * @param roundID 轮次ID
     * @return BigInteger 讨论课ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listSeminarsIDByRoundID(BigInteger roundID);
    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();
    /**
     * 插入新的讨论课信息
     *
     * @param  seminar 讨论课对象
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void insertSeminar(Seminar seminar);
    /**
     * 建立班级讨论课联系
     *
     * @param  klassID 班级ID
     * @param  seminarID 讨论课ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void insertKlassSeminarBy2ID(BigInteger klassID, BigInteger seminarID);
    /**
     * 修改讨论课信息
     *
     * @param  seminar 讨论课对象
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateSeminarBySeminarID(Seminar seminar);
    /**
     * 删除讨论课信息
     *
     * @param  seminarID 讨论课ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteSeminarBySeminarID(BigInteger seminarID);
    /**
     * 删除班级和讨论课联系的信息
     *
     * @param  seminarID 讨论课ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteKlassSeminarBySeminarID(BigInteger seminarID);
    /**
     * 修改讨论课报告提交截止时间
     *
     * @param  map 讨论课报告截止时间信息
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateSeminarReportDDLByKlassAndSeminarID(Map<String, Object> map);

    /**
     * 获取讨论课状态
     *
     * @param seminarID 讨论课ID
     * @param klassID 班级ID
     * @return  Map ddl和status信息
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    Integer getStatusBySeminarAndKlassID(BigInteger seminarID, BigInteger klassID);

    /**
     * 获取报告截止时间
     *
     * @param seminarID 讨论课ID
     * @param klassID 班级ID
     * @return  Map ddl和status信息
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    String getReportDDLBySeminarAndKlassID(BigInteger seminarID, BigInteger klassID);


    /**
     * 获取班级讨论课ID
     *
     * @param klassID 班级ID
     * @param seminarID 讨论课
     * @return BigInteger 班级讨论课ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getKlassSeminarIDByKlassAndSeminarID(BigInteger klassID, BigInteger seminarID);

    /**
     * 获取某次讨论课某队伍成绩
     *
     * @param klassSeminarID 班级讨论课ID
     * @param teamID 队伍ID
     * @return Map 某次讨论课成绩
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Double getPreScoreByKlassSeminarAndTeamID(BigInteger klassSeminarID, BigInteger teamID);

    Double getReportScoreByKlassSeminarAndTeamID(BigInteger klassSeminarID, BigInteger teamID);

    Double getQuestionScoreByKlassSeminarAndTeamID(BigInteger klassSeminarID, BigInteger teamID);
    /**
     * 修改某次讨论课某队伍成绩
     *
     * @param map 修改的信息
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void updateSeminarScoreByKlassSeminarAndTeamID(Map<String,Object> map);

    /**
     * 通过seminarID和klassID获取具体班级下的讨论课的ID
     *
     * @param seminarID 讨论课ID
     * @param klassID 班级ID
     * @return BigInteger 具体班级下的讨论课的ID
     * @author LaiShaopeng
     * @date 2018/12/24 15:06:00
     */
    BigInteger getKlassSeminarIDBySeminarIDAndClassID(BigInteger seminarID, BigInteger klassID);

    void insertKlassSeminar(Map<String,Object> map);

    /**
     * 开始讨论课
     *
     * @param  klassID 班级ID
     * @param  seminarID 讨论课ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateStartSeminarByKlassAndSeminarID(BigInteger klassID, BigInteger seminarID);

    /**
     * 结束讨论课
     *
     * @param  klassID 班级ID
     * @param  seminarID 讨论课ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateEndSeminarByKlassAndSeminarID(BigInteger klassID, BigInteger seminarID);


    Seminar getSeminarInProcess();

    /**
     * 修改讨论课报告提交截止时间
     *
     * @param  map 讨论课报告截止时间信息
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateSeminarReportDDLBySeminarID(Map<String, Object> map);


    BigInteger getKlassIDByProcessSeminarID(BigInteger seminarID);
}
