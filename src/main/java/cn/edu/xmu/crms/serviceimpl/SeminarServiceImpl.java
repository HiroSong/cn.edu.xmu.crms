package cn.edu.xmu.crms.serviceimpl;
import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.EduClassDao;
import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.entity.*;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.service.SeminarService;
import cn.edu.xmu.crms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:33
 */
@Service
public class SeminarServiceImpl implements SeminarService {
    @Autowired
    SeminarDao seminarDao;

    @Override
    public int getRoundOrderBySeminarId(BigInteger seminarID)
    {
        BigInteger roundId=seminarDao.selectRoundIdBySeminarId(seminarID);
        if(null==roundId)
            return -1;
        else return seminarDao.selectRoundOrderByRoundId(roundId);
    }
    @Override
    public List<String> getDeadLineBySeminarIdAndClassId(BigInteger seminarID, BigInteger classID)
    {
        BigInteger DDLID=seminarDao.selectDeadLineIdBySeminarIdAndClassId(seminarID,classID);
        if(null==DDLID) return null;
        List<String> deadline=seminarDao.selectDeadLineByDeadLineId(DDLID);
        if(null==deadline) return null;
        return deadline;
    }

    @Override
    public Seminar getSeminarBySeminarIdAndClassId(BigInteger seminarID,BigInteger classID)
    {
        Seminar seminar=seminarDao.selectSeminarBySeminarId(seminarID);
        if(null==seminar)
            return null;
        seminar=seminarDao.selectSeminarBySeminarIdAndClassId(seminarID,classID);
        if(null==seminar)
            return null;
        seminar.setRound(this.getRoundOrderBySeminarId(seminarID));
        seminar.setSeminarStartTime(this.getDeadLineBySeminarIdAndClassId(seminarID,classID).get(0));

        return seminar;
    }
    @Override
    public boolean registSeminar(BigInteger seminarID,BigInteger teamID,Integer presentationOrder)
    {
        int num=seminarDao.insertPresentation(seminarID,teamID,presentationOrder);
        if(num<=0)
            return false;
        else
            return true;
    }
    @Override
    public boolean modifySeminarRegist(BigInteger seminarID,BigInteger teamID,Integer presentationOrder)
    {
        if(cancelSeminarRegit(seminarID,teamID))
            if(registSeminar(seminarID,teamID,presentationOrder))
                return true;
        return true;
    }
    @Override
    public boolean cancelSeminarRegit(BigInteger seminarID,BigInteger teamID)
    {
        int num=seminarDao.deletePresentation(seminarID,teamID);
        if(num<=0)
            return false;
        else
            return true;
    }
}
