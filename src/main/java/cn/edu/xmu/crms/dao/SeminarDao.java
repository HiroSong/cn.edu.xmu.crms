package cn.edu.xmu.crms.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


/**
 * @author SongLingbing
 * @date 2018/11/29 15:30
 */
@Mapper
@Repository
public interface SeminarDao{
    /**
     * 用于修改讨论课当前状态
     * @param seminarID 讨论课号
     * @param classID 班级号
     * @return Boolean  是否修改成功
     * @author Hongqiwu
     * @date 2018/12/03 21:21
     */
    void updateSeminarState(BigInteger seminarID, BigInteger classID);
    /**
     * 用于删除讨论课
     * @param seminarID 讨论课号
     * @author Hongqiwu
     * @date 2018/12/03 21:21
     */
    void deleteSeminar(BigInteger seminarID);
    /**
     * 用于删除讨论课的关系表
     * @param seminarID 讨论课号
     * @author Hongqiwu
     * @date 2018/12/03 21:21
     */
    void deleteSeminarClass(BigInteger seminarID);
    /**
     * 用于删除讨论课的关系表
     * @param seminarID 讨论课号
     * @author Hongqiwu
     * @date 2018/12/03 21:21
     */
    void deleteSeminarRound(BigInteger seminarID);
    /**
     * 用于判断是否有存在讨论课
     * @param seminarID 讨论课号
     * @return BigInteger  是否修改成功
     * @author Hongqiwu
     * @date 2018/12/03 22:59
     */
    BigInteger getSeminarIDBySeminarID(BigInteger seminarID);
    /**
     * 用于判断是否有存在讨论课
     * @param seminarID 讨论课号
     * @return BigInteger  是否修改成功
     * @author Hongqiwu
     * @date 2018/12/03 22:59
     */
    Integer getSeminarStateBySeminarAndClassID(BigInteger seminarID, BigInteger classID);
}
