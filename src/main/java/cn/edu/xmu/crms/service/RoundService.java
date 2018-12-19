package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.RoundDao;
import cn.edu.xmu.crms.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoundService
 * @Description 关于round的处理
 * @Author Hongqiwu
 * @Date 2018/12/20 0:22
 **/

@Service
public class RoundService {
    @Autowired
    RoundDao roundDao;

    public List<Map<String, Object>> listRoundsInfoByCourseID(BigInteger courseID) {
        List<Map<String, Object>> roundInfoList = new ArrayList<>();
        List<Round> rounds = roundDao.listRoundsByCourseID(courseID);
        for(int i = 0; i < rounds.size(); i++) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id",rounds.get(i).getID());
            map.put("order",rounds.get(i).getRoundSerial());
            roundInfoList.add(map);
        }
        return roundInfoList;
    }
}
