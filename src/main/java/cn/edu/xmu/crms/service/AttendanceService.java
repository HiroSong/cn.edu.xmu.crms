package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.entity.Attendance;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/12/24 20:20
 */
@Service
public class AttendanceService {

    @Autowired
    SeminarMapper seminarMapper;
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    AttendanceDao attendanceDao;
    @Autowired
    FileUtil fileUtil;

    @GetMapping("/seminar/{seminarID}/class/{classID}/attendance")
    public List<Map<String,Object>> listAttendanceInfoBySeminarIDAndClassID(@PathVariable("seminarID") BigInteger seminarID,
                                                                            @PathVariable("classID") BigInteger classID) {
        BigInteger klass_seminarID = seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID, classID);
        List<Map<String, Object>> attendanceInfoList = new ArrayList<>();
        List<Attendance> attendances = teamDao.listAttendancesByKlassSeminarID(klass_seminarID);
        for (int i = 0; i < attendances.size(); i++) {
            Attendance attendance = attendances.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("teamOrder", attendance.getTeamOrder());
            map.put("teamNumber", attendance.getTeam().getTeamNumber());
            attendanceInfoList.add(map);
        }
        return attendanceInfoList;
    }
}
