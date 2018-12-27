//package cn.edu.xmu.crms.controller;
//
//import cn.edu.xmu.crms.entity.Attendance;
//import cn.edu.xmu.crms.entity.Seminar;
//import cn.edu.xmu.crms.service.AttendanceService;
//import cn.edu.xmu.crms.service.SeminarService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import java.math.BigInteger;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author SongLingbing
// * @date 2018/11/30 14:56
// */
//@RestController
//public class PresentationController {
//
//    @Autowired
//    AttendanceService attendanceService;
//    @Autowired
//    SeminarService seminarService;
//
//
//    @GetMapping("/seminar/{seminarID}/class/{classID}/attendance")
//    public List<Map<String, Object>> listAttendancesInfo(@PathVariable("seminarID") BigInteger seminarID,
//                                                         @PathVariable("classID") BigInteger classID){
//        return attendanceService.listAttendanceInfoBySeminarIDAndClassID(seminarID,classID);
//    }
//
//    @PutMapping("/question/{questionID}")
//    public void selectQuestion(@PathVariable("questionID") BigInteger questionID)
//    {
//        seminarService.selectQuestion();
//    }
//
//
//}
