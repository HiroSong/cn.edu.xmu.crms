package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;


/**
 * @author SongLingbing
 * @date 2018/11/29 15:33
 */
@Service
public class SeminarServiceImpl implements SeminarService {
    @Autowired
    SeminarDao seminarDao;
    /**
     * 删除讨论课
     * @author Hongqiwu
     * @date 2018/12/03 23:32
     */
    @Override
    public void deleteSeminarAll(BigInteger seminarID) {
        seminarDao.deleteSeminar(seminarID);
        seminarDao.deleteSeminarClass(seminarID);
        seminarDao.deleteSeminarRound(seminarID);
    }
}
