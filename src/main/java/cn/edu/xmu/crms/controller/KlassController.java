package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.KlassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * @ClassName KlassController
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/20 1:35
 **/
@RestController
public class KlassController {
    @Autowired
    KlassDao klassDao;

    @PutMapping("/class/{classID}")
    public void importStudentList(@PathVariable("classID")
                                BigInteger klassID) {
        //未完成
    }

    @DeleteMapping("/class/{classID}")
    public void deleteKlassByKlassID(@PathVariable("classID")
                                               BigInteger klassID) {
        klassDao.deleteKlassInfoByKlassID(klassID);
    }
}
