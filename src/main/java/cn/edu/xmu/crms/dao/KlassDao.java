package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.mapper.KlassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName KlassDao
 * @Author Hongqiwu
 **/
@Repository
public class KlassDao {
    @Autowired
    KlassMapper klassMapper;

    public Klass getKlassByKlassID(BigInteger klassID) {
        return klassMapper.getKlassByKlassID(klassID);
    }

    public Klass getKlassByStudentAndCourseID(BigInteger studentID, BigInteger courseID) {
        BigInteger klassID = klassMapper.getKlassIDByStudentAndCourseID(studentID, courseID);
        if(klassID == null) {
            return null;
        }
        return klassMapper.getKlassByKlassID(klassID);
    }

    public List<Klass> listKlassByCourseID(BigInteger courseID) {
        List<Klass> klassList = new ArrayList<>();
        List<BigInteger> klassIDList = klassMapper.listKlassIDByCourseID(courseID);
        for(int i = 0; i < klassIDList.size(); i++) {
            Klass klass = klassMapper.getKlassByKlassID(klassIDList.get(i));
            klassList.add(klass);
        }
        return klassList;
    }

    public void deleteKlassInfoByKlassID(BigInteger klassID) {
        klassMapper.deleteKlassByKlassID(klassID);
        klassMapper.deleteKlassRoundByKlassID(klassID);
        klassMapper.deleteKlassSeminarByKlassID(klassID);
        klassMapper.deleteKlassStudentByKlassID(klassID);
        klassMapper.deleteKlassInTeamByKlassID(klassID);
    }
}
