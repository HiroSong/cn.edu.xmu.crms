package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author SongLingbing
 * @date 2018/11/29 15:12
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    SeminarDao seminarDao;
    @Override
    public Team getTeamByNumber(BigInteger teamID)
    {
        Team team=teamDao.selectTeamByTeamId(teamID);
        if(null==team)
            return null;
        else
            return team;
    }
    @Override
    public List<Student> getStudentsByTeamId(BigInteger teamID)
    {
        List<Student> students=new ArrayList<Student>();
        List<BigInteger>studentIdList=teamDao.selectMemberIdByTeamId(teamID);
        for(int i=0;i<studentIdList.size();i++)
        {
            Student student=studentDao.selectStudentByNumber(studentIdList.get(i));
            if(null==student)
                return null;
            else
                students.add(student);
        }
        return students;
    }
    @Override
    public Map<String,Object> getTeamListByCourseId(BigInteger courseID)
    {
        Map<String,Object> teamInfo=new HashMap<String,Object>();
        List<BigInteger> teamIdList=teamDao.selectTeamIdByCourseId(courseID);
        for(int i=0;i<teamIdList.size();i++)
        {
            Team team=teamDao.selectTeamByTeamId(teamIdList.get(i));
            if(null==team)
                return null;
            else
            {
                teamInfo.put(team.getTeamNumber()+team.getTeamName(),team);

                List<BigInteger> memberIdList=new ArrayList<BigInteger>();
                memberIdList=teamDao.selectMemberIdByTeamId(teamIdList.get(i));

                Student leader=studentDao.selectStudentByNumber(team.getTeamLeader());
                List<Student> members=new ArrayList<Student>();
                members.add(leader);
                for(int j=0;j<memberIdList.size();j++) {
                    Student student = studentDao.selectStudentByNumber(memberIdList.get(j));
                    members.add(student);
                }
                teamInfo.put(team.getTeamNumber()+"成员列表",members);
            }
        }
        return teamInfo;
    }
    @Override
    public Map<String,Object> getTeamByCourseIdAndStudentId(BigInteger courseID,BigInteger studentID)
    {
        Team team;
        Map<String,Object> teamInfo=new HashMap<String,Object>();
        Student leader;
        List<Student> memberIdList;
        List<BigInteger> teamIdListOfCourse=teamDao.selectTeamIdByCourseId(courseID);
        List<BigInteger> teamIdListOfLeader=teamDao.selectTeamIdByLeaderId(studentID);
        List<BigInteger> teamIdListOfMember=teamDao.selectTeamIdByMemberId(studentID);

        teamIdListOfLeader.retainAll(teamIdListOfCourse);
        teamIdListOfMember.retainAll(teamIdListOfCourse);
        if(teamIdListOfLeader.isEmpty())
        {
            if (teamIdListOfMember.isEmpty())
                return null;
            team=teamDao.selectTeamByTeamId(teamIdListOfMember.get(0));
            memberIdList=this.getStudentsByTeamId(teamIdListOfMember.get(0));
        }
        else
        {
            team=teamDao.selectTeamByTeamId(teamIdListOfLeader.get(0));
            memberIdList=this.getStudentsByTeamId(teamIdListOfMember.get(0));
        }

        leader=studentDao.selectStudentByNumber(team.getTeamLeader());

        teamInfo.put(team.getTeamNumber(),team.getTeamNumber());
        teamInfo.put(team.getTeamName(),team.getTeamName());
        teamInfo.put("组长",leader.getName());
        teamInfo.put("组长学号",leader.getNumber());
        for(int i=0;i<memberIdList.size();i++)
        {
            teamInfo.put("成员"+i,memberIdList.get(i).getName());
            teamInfo.put("成员"+i+"学号",memberIdList.get(i).getNumber());
        }
        return teamInfo;
    }

    @Override
    public List<Team> getPresentationTeamListBySeminarIdAndClassId(BigInteger seminarID,BigInteger classID)
    {
        List<BigInteger> teamIdListBySeminar=seminarDao.selectTeamIdBySeminarId(seminarID);
        List<BigInteger> teamIdListByClass=teamDao.selectTeamIdByClassId(classID);

        teamIdListByClass.retainAll(teamIdListBySeminar);
        if(teamIdListByClass.isEmpty())
            return null;
        List<Team> teams=new ArrayList<Team>();
        for(int i=0;i<teamIdListByClass.size();i++)
        {
            Team team=teamDao.selectTeamByTeamId(teamIdListByClass.get(i));
            teams.add(team);
        }
        return teams;
    }
}
