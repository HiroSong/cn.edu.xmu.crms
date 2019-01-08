package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.dao.AttendanceDao;
import cn.edu.xmu.crms.entity.Attendance;
import cn.edu.xmu.crms.util.common.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/12/24 20:20
 */
@RestController
@Service
public class AttendanceService {
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    AttendanceDao attendanceDao;
    @Autowired
    FileUtil fileUtil;

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/seminar/{seminarID}/class/{classID}/attendance")
    public List<Map<String, Object>> listAttendanceInfoBySeminarIDAndClassID(@PathVariable("seminarID") BigInteger seminarID,
                                                                             @PathVariable("classID") BigInteger classID) {
        BigInteger klassSeminarID = seminarDao.getKlassSeminarIDBySeminarIDAndClassID(seminarID, classID);
        List<Map<String, Object>> attendanceInfoList = new ArrayList<>();
        List<Attendance> attendances = teamDao.listAttendancesByKlassSeminarID(klassSeminarID);
        for (int i = 0; i < attendances.size(); i++) {
            Attendance attendance = attendances.get(i);
            Map<String, Object> map = new HashMap<>(0);
            map.put("id", attendance.getID());
            map.put("teamID", attendance.getTeamID());
            map.put("teamOrder", attendance.getTeamOrder());
            map.put("teamNumber", attendance.getTeam().getTeamNumber());
            map.put("bePresent", attendance.getBePresent());
            attendanceInfoList.add(map);
        }
        return attendanceInfoList;
    }

    @PreAuthorize("hasAuthority('student')")
    @PostMapping("/attendance/{attendanceID}/report")
    public Map<String, Object> updateReport(@PathVariable("attendanceID") BigInteger attendanceID, @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = attendanceDao.updateFile(attendanceID, "report", file);
        return map;
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/attendance/{attendanceID}/report")
    public Map<String, Object> getReport(@PathVariable("attendanceID") BigInteger attendanceID) {
        return attendanceDao.getFile("report", attendanceID);
    }

    @PreAuthorize("hasAuthority('student')")
    @PostMapping("/attendance/{attendanceID}/ppt")
    public Map<String, Object> updatePPT(@PathVariable("attendanceID") BigInteger attendanceID, @RequestParam("file") MultipartFile file) {
        return attendanceDao.updateFile(attendanceID, "ppt", file);
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/attendance/{attendanceID}/ppt")
    public Map<String, Object> getPPT(@PathVariable("attendanceID") BigInteger attendanceID) {
        return attendanceDao.getFile("ppt", attendanceID);
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @RequestMapping("/download/attendance/{attendanceID}/report/{reportName}")
    public void downloadReport(HttpServletResponse response, @PathVariable("attendanceID") BigInteger attendanceID, @PathVariable("reportName") String reportName) {
        fileUtil.downloadFile(response, "//report//" + attendanceID + "-", reportName);
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @RequestMapping("/download/attendance/{attendanceID}/ppt/{pptName}")
    public void downloadPPT(HttpServletResponse response, @PathVariable("attendanceID") BigInteger attendanceID, @PathVariable("pptName") String pptName) {
        fileUtil.downloadFile(response, "//ppt//" + attendanceID + "-", pptName);
    }
}
