package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.EduClassDao;
import cn.edu.xmu.crms.entity.EduClass;
import cn.edu.xmu.crms.service.EduClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author hongqiwu
 * @date 2018/12/01 14:07
 */
@Service
public class EduClassServiceImpl implements EduClassService {
    @Autowired
    EduClassDao eduClassDao;
    @Override
    public ArrayList<EduClass> getEduClassByCourseId(BigInteger id) {
        ArrayList<BigInteger> classId = eduClassDao.selectEduClassIdByCourseId(id);
        ArrayList<EduClass> eduClass = new ArrayList<>();
        for(int i = 0; i < classId.size(); i++) {
            eduClass.add(eduClassDao.selectEduClassByClassId(classId.get(i)));
        }
        return eduClass;
    }
}
