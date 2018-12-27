//package cn.edu.xmu.crms.controller;
//
//import cn.edu.xmu.crms.entity.Student;
//import cn.edu.xmu.crms.entity.TeamValidApplication;
//import cn.edu.xmu.crms.service.TeamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigInteger;
//import java.util.Map;
//
///**
// * @author Hongqiwu
// * @date 2018/11/30 20:09
// */
//@RestController
//public class TeamController {
//    @Autowired
//    TeamService teamService;
//
//    @GetMapping("/team/{teamID}")
//    public Map<String, Object> getTeamInfoByTeamID(@PathVariable("teamID") BigInteger teamID) {
//        return teamService.getTeamInfoByTeamID(teamID);
//    }
//
//    //队长解散小组
//    @DeleteMapping("/team/{teamID}")
//    public void deleteTeamByTeamID(@PathVariable("teamID") BigInteger teamID) {
//        teamService.deleteTeamByTeamID(teamID);
//    }
//    //组员或者组长添加新的成员
//    @PutMapping("/team/{teamID}/add")
//    public void addTeamMember(@PathVariable("teamID") BigInteger teamID,
//                              @RequestBody Student student) {
//        teamService.insertStudentByTeamAndStudentID(teamID,student.getID());
//    }
//    //移除成员或踢出队伍
//    @PutMapping("/team/{teamID}/remove")
//    public void removeTeamMember(@PathVariable("teamID") BigInteger teamID,
//                              @RequestBody Student student) {
//        teamService.deleteStudentFromTeamByTeamAndStudentID(teamID,student.getID());
//    }
//
//    //组长发出有效组队申请  如果返回id=0则还有未审核的申请 需等待
//    @PostMapping("/team/{teamID}/teamvalidrequest")
//    public Map<String, Object> createTeamValidRequest(@PathVariable("teamID") BigInteger teamID,
//                                 @RequestBody TeamValidApplication teamValidApplication) {
//        teamValidApplication.setTeamID(teamID);
//        return teamService.insertApplicationByTeamValid(teamValidApplication);
//    }
//    //教师同意合法申请
//    @PutMapping("/team/{teamID}/approve")
//    public void approveTeam(@PathVariable("teamID") BigInteger teamID) {
//        teamService.updateValidApplicationByTeamID(teamID);
//    }
//}
