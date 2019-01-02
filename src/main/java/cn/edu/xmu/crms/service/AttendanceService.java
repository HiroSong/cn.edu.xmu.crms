package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import cn.edu.xmu.crms.dao.AttendanceDao;
import cn.edu.xmu.crms.entity.Attendance;
import cn.edu.xmu.crms.util.common.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/12/24 20:20
 */
@RestController
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
            map.put("id", attendance.getID());
            map.put("teamOrder", attendance.getTeamOrder());
            map.put("teamNumber", attendance.getTeam().getTeamNumber());
            attendanceInfoList.add(map);
        }
        return attendanceInfoList;
    }

    @PostMapping("/attendance/{attendanceID}/report")
    public Map<String, Object> updateReport(@PathVariable("attendanceID") BigInteger attendanceID, @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = attendanceDao.updateFile(attendanceID,"report", file);
        return map;
    }

    @GetMapping("/attendance/{attendanceID}/report")
    public Map<String, Object> getReport(@PathVariable("attendanceID") BigInteger attendanceID) {
        return attendanceDao.getFile("report", attendanceID);
    }

    @PostMapping("/attendance/{attendanceID}/ppt")
    public Map<String, Object> updatePPT(@PathVariable("attendanceID") BigInteger attendanceID, @RequestParam("file") MultipartFile file) {
        return attendanceDao.updateFile(attendanceID,"ppt", file);
    }

    @GetMapping("/attendance/{attendanceID}/ppt")
    public Map<String, Object> getPPT(@PathVariable("attendanceID") BigInteger attendanceID) {
        return attendanceDao.getFile("ppt", attendanceID);
    }

    @RequestMapping("/attendance/report/{reportName}")
    public void downloadReport(HttpServletResponse response, @PathVariable("reportName")String reportName){
        fileUtil.downloadFile(response, "//report//" ,reportName);
    }

    @RequestMapping("/attendance/ppt/{pptName}")
    public void downloadPPT(HttpServletResponse response, @PathVariable("pptName")String pptName){
        fileUtil.downloadFile(response, "//ppt//" ,pptName);
    }
}
