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
    public List<Student> listMemberByTeamID(BigInteger teamID)
    {
        List<Student> members=new ArrayList<Student>();
        List<BigInteger>studentIDList=teamDao.selectMemberIDListByTeamID(teamID);
        for(int i=0;i<studentIDList.size();i++)
        {
            Student student=studentDao.selectStudentByNumber(studentIDList.get(i));
            if(null==student)
                return null;
            else
                members.add(student);
        }
        return members;
    }

    @Override
    public List<Team> listTeamByCourseID(BigInteger courseID)
    {
        List<Team> teams=new ArrayList<Team>();
        List<BigInteger> teamIDList=teamDao.selectTeamIDListByCourseID(courseID);
        for(int i=0;i<teamIDList.size();i++)
        {
            Team team=teamDao.selectTeamByTeamID(teamIDList.get(i));
            if(null==team)
                return null;
            else
                teams.add(team);
        }
        return teams;
    }

    @Override
    public Map<BigInteger,List<Student>> listMemberByCourseID(BigInteger courseID)
    {
        Map<BigInteger,List<Student>> listMember=new HashMap<BigInteger, List<Student>>();
        List<BigInteger> teamIDList=teamDao.selectTeamIDListByCourseID(courseID);
        for(int i=0;i<teamIDList.size();i++)
        {
            Team team=teamDao.selectTeamByTeamID(teamIDList.get(i));
            List<BigInteger> studentIDList=teamDao.selectMemberIDListByTeamID(teamIDList.get(i));
            List<Student> students= new ArrayList<Student>();
            for(int j=0;j<studentIDList.size();j++)
            {
                Student student=studentDao.selectStudentByID(studentIDList.get(j));
                students.add(student);
            }
            listMember.put(team.getID(),students);
        }
        return listMember;
    }

    @Override
    public Team getTeamByCourseIDAndStudentID(BigInteger courseID,BigInteger studentID)
    {
        Team team;
        Student leader;
        List<Student> memberIDList;
        List<BigInteger> teamIDListOfCourse=teamDao.selectTeamIDListByCourseID(courseID);
        List<BigInteger> teamIDListOfLeader=teamDao.selectTeamIDListByLeaderID(studentID);
        List<BigInteger> teamIDListOfMember=teamDao.selectTeamIDListByMemberID(studentID);

        teamIDListOfLeader.retainAll(teamIDListOfCourse);
        teamIDListOfMember.retainAll(teamIDListOfCourse);
        if(teamIDListOfLeader.isEmpty())
        {
            if (teamIDListOfMember.isEmpty())
                return null;
            team=teamDao.selectTeamByTeamID(teamIDListOfMember.get(0));
        }
        else
        {
            team=teamDao.selectTeamByTeamID(teamIDListOfLeader.get(0));
        }
        return team;
    }

    @Override
    public List<Team> listPresentationTeamBySeminarIDAndClassID(BigInteger seminarID,BigInteger classID)
    {
        List<BigInteger> teamIDListBySeminar=seminarDao.selectTeamIDBySeminarID(seminarID);
        List<BigInteger> teamIDListByClass=teamDao.selectTeamIDListByClassID(classID);

        teamIDListByClass.retainAll(teamIDListBySeminar);
        if(teamIDListByClass.isEmpty())
            return null;
        List<Team> teams=new ArrayList<Team>();
        for(int i=0;i<teamIDListByClass.size();i++)
        {
            Team team=teamDao.selectTeamByTeamID(teamIDListByClass.get(i));
            teams.add(team);
        }
        return teams;
    }
}
