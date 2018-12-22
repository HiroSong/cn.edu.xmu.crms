package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.KlassDao;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.mapper.KlassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName KlassService
 * @Author Hongqiwu
 **/
@Service
public class KlassService {
    @Autowired
    KlassDao klassDao;
    @Autowired
    KlassMapper klassMapper;

    /**
     * 用courseID查找班级信息列表
     *
     * @param courseID 课程号码
     * @return List<Map<String, Object>> 返回查找到的列表，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
    public List<Map<String, Object>> listKlassInfoByCourseID(BigInteger courseID) {
        List<Klass> klassList = klassDao.listKlassByCourseID(courseID);
        if(klassList == null) {
            return null;
        }
        List<Map<String, Object>> klassMapList = new ArrayList<>();
        for(int i = 0; i < klassList.size(); i++) {
            Klass klass = klassList.get(i);
            Map<String, Object> klassMap = new HashMap<>(4);
            klassMap.put("id",klass.getID());
            String klassName = klass.getGrade().toString()+klass.getKlassSerial().toString();
            klassMap.put("name",klassName);
            klassMap.put("time",klass.getKlassTime());
            klassMap.put("classroom",klass.getKlassLocation());
            klassMapList.add(klassMap);
        }
        return klassMapList;
    }

    public BigInteger createNewKlass(Klass klass) {
        return klassMapper.insertKlassByKlass(klass);
    }

}
