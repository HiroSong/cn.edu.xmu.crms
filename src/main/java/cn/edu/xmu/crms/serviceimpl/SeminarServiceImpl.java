package cn.edu.xmu.crms.serviceimpl;
import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.EduClassDao;
import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.entity.*;

import cn.edu.xmu.crms.service.CourseService;
import cn.edu.xmu.crms.service.SeminarService;
import org.graalvm.compiler.lir.Opcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:33
 */
@Service
public class SeminarServiceImpl implements SeminarService {
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    EduClassDao eduClassDao;
    @Override
    public List<Round> getRoundByCourseID(BigInteger CourseID){
        List<BigInteger> classes=eduClassDao.selectEduClassIdByCourseId(CourseID);
        List<Round> round =new ArrayList<>();
        for(int i=0;i < classes.size();i++){
            for(int j=0;j<getRoundByClassID(classes.get(i)).size();j++){
                Round r=new Round();
                r.setRound_Order(getRoundByClassID(classes.get(i)).get(j));
                round.add(r);
            }
        }
        return round;
    }
    @Override
    public List<BigInteger> getRoundByClassID(BigInteger ClassID){
        List<BigInteger> classes =new ArrayList<>();
        seminarDao.selectRoundByClassID(ClassID);
        return  classes;
    }
    @Override
    public Seminar getSeminarBySeminarIDAndClassID(BigInteger seminarID,BigInteger classID){
        Seminar seminar=seminarDao.selectSeminarBySeminarIDandClassID(seminarID,classID);
        return seminar;
    }

}
