package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.KlassDao;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.mapper.KlassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @Autowired
    StudentDao studentDao;

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

    @PostMapping("/class/{classID}/student")
    public Map<String, Object> importStudentByExcel(@PathVariable("classID")BigInteger klassID, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>(1);
        String status = studentDao.insertStudentList(klassID, file);
        map.put("message", status);
        return map;
    }

    @DeleteMapping("/class/{classID}")
    public void deleteKlass(@PathVariable("classID") BigInteger klassID) {
        klassDao.deleteKlassByKlassID(klassID);
    }
}
