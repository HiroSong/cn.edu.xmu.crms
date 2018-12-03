package cn.edu.xmu.crms.serviceimpl;

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
    public int getRoundOrderBySeminarID(BigInteger seminarID)
    {
        BigInteger roundID=seminarDao.selectRoundIDBySeminarID(seminarID);
        if(null==roundID)
            return -1;
        else return seminarDao.selectRoundOrderByRoundID(roundID);
    }
    @Override
    public List<String> getDeadLineBySeminarIDAndClassID(BigInteger seminarID, BigInteger classID)
    {
        BigInteger DDLID=seminarDao.selectDeadLineIDBySeminarIDAndClassID(seminarID,classID);
        if(null==DDLID) return null;
        List<String> deadline=seminarDao.selectDeadLineByDeadLineID(DDLID);
        if(null==deadline) return null;
        return deadline;
    }

    @Override
    public Seminar getSeminarBySeminarIDAndClassID(BigInteger seminarID,BigInteger classID)
    {
        Seminar seminar=seminarDao.selectSeminarBySeminarID(seminarID);
        if(null==seminar)
            return null;
        seminar=seminarDao.selectSeminarBySeminarIDAndClassID(seminarID,classID);
        if(null==seminar)
            return null;
        seminar.setRound(this.getRoundOrderBySeminarID(seminarID));
        seminar.setSeminarStartTime(this.getDeadLineBySeminarIDAndClassID(seminarID,classID).get(0));

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
        if(cancelSeminarRegist(seminarID,teamID))
            if(registSeminar(seminarID,teamID,presentationOrder))
                return true;
        return true;
    }
    @Override
    public boolean cancelSeminarRegist(BigInteger seminarID,BigInteger teamID)
    {
        int num=seminarDao.deletePresentation(seminarID,teamID);
        if(num<=0)
            return false;
        else
            return true;
    }
}
