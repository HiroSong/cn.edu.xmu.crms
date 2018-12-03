package cn.edu.xmu.crms.serviceimpl;
import cn.edu.xmu.crms.entity.*;

import org.graalvm.compiler.lir.Opcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:33
 */
@Service
public class SeminarServiceImpl {
    @Autowired
    SeminarDao seminarDao;
    @Override
    public Round getRoundByCourseID(BigInteger id){
        Round rounds=seminarDao.selectRoundByCourseID(id);
        return Round;
    }

}
