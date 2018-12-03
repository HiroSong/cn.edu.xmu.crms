package cn.edu.xmu.crms.serviceimpl;
import cn.edu.xmu.crms.entity.*;

import org.graalvm.compiler.lir.Opcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:33
 */
@Service
public class SeminarServiceImpl {
    @Autowired
    SeminarDao seminarDao;
    @Override
    public ArrayList<Round> getRoundByCourseID(BigInteger id){
        ArrayList<BigInteger> roundOrder=seminarDao.selectRoundByCourseID(id);
        ArrayList<Round> round =new ArrayList<>();
        for(int i=0;i < roundOrder.size();i++){
            round.add(seminarDao.selectRoundByCourseID(roundOrder.get(i)));
        }
        return round;
    }

}
