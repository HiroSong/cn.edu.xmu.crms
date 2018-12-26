package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.KlassDao;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.mapper.KlassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@Service
public class KlassService {
    @Autowired
    KlassDao klassDao;
    @Autowired
    KlassMapper klassMapper;

    @GetMapping("/course/{courseID}/class")
    public List<Map<String, Object>> listKlassInfoByCourseID(@PathVariable("courseID") BigInteger courseID) {
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
