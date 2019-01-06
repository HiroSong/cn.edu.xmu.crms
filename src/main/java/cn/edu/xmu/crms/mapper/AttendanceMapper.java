package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/12/24 22:29
 */
@Mapper
@Component
public interface AttendanceMapper {
    /**
      * 更新报告
      *
      * @param attendance 相关report文件信息和attendance的id
      * @author SongLingbing
      * @date 2018/12/25 0:24
      */
    void updateReport(Attendance attendance);

    /**
      * 更新ppt
      *
      * @param attendance 相关ppt文件信息和attendance的id
      * @author SongLingbing
      * @date 2018/12/25 0:25
      */
    void updatePPT(Attendance attendance);

    /**
      * 获取报告文件相关信息
      *
      * @param id Attendance的id
      * @return attendance 包含报告文件相关信息
      * @author SongLingbing
      * @date 2018/12/25 0:26
      */
    Attendance getReport(BigInteger id);

    /**
     * 获取PPT文件相关信息
     *
     * @param id Attendance的id
     * @return attendance 包含PPT文件相关信息
     * @author SongLingbing
     * @date 2018/12/25 0:26
     */
    Attendance getPPT(BigInteger id);
}
