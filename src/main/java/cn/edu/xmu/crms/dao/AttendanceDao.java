package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Attendance;
import cn.edu.xmu.crms.mapper.AttendanceMapper;
import cn.edu.xmu.crms.util.common.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/12/24 22:27
 */
@Repository
public class AttendanceDao {
    @Autowired
    AttendanceMapper attendanceMapper;
    @Autowired
    FileUtil fileUtil;

    static final String REPORT_FILE="report";
    static final String PPT_FILE="ppt";

    public Map<String, Object> updateFile(BigInteger attendanceID, String fileType, MultipartFile file){
        Map<String, Object> fileInfo = new HashMap<>(3);
        String url = fileUtil.uploadFile("//report//", file);
        if (url == null) {
            fileInfo.put("message", "fail to upload");
            return fileInfo;
        }
        fileInfo.put("id", attendanceID);
        fileInfo.put("name", file.getOriginalFilename());
        fileInfo.put("url", url);
        Attendance attendance = new Attendance();
        attendance.setID(new BigInteger(fileInfo.get("id").toString()));
        attendance.setReportName(fileInfo.get("name").toString());
        attendance.setReportUrl(fileInfo.get("url").toString());
        if(fileType==REPORT_FILE) {
            attendanceMapper.updateReport(attendance);
        }else if(fileType==PPT_FILE){
            attendanceMapper.updatePPT(attendance);
        }
        return fileInfo;
    }


    public Map<String, Object>  getFile(String fileType, BigInteger id){
        Map<String, Object> map = new HashMap<>(2);
        Attendance attendance=null;
        if(fileType==REPORT_FILE) {
            attendance = attendanceMapper.getReport(id);
        }else if(fileType==PPT_FILE) {
            attendance = attendanceMapper.getPPT(id);
        }
        if(attendance==null){
            map.put("message", "no file found");
            return map;
        }
        map.put("name", attendance.getReportName());
        map.put("url", attendance.getReportUrl());
        return map;
    }
}
