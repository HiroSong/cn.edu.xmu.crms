package cn.edu.xmu.crms.service;

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

/**
 * @author SongLingbing
 * @date 2018/12/24 20:20
 */
@RestController
@Service
public class AttendanceService {
    @Autowired
    FileUtil fileUtil;
    @Autowired
    AttendanceDao attendanceDao;

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
