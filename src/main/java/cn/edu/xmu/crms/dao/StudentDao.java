package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.KlassMapper;
import cn.edu.xmu.crms.mapper.StudentMapper;
import cn.edu.xmu.crms.util.common.FileUtil;
import cn.edu.xmu.crms.util.excel.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName StudentDao
 * @Author Hongqiwu
 **/

@Repository
public class StudentDao {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    FileUtil fileUtil;
    @Autowired
    ExcelUtil excelUtil;

    public Student getStudentByStudentID(BigInteger studentID) {
        return studentMapper.getStudentByStudentID(studentID);
    }

    public Integer updateStudentInfo(Student student) {
        return studentMapper.updateStudentInfoByStudent(student);
    }

    public Integer resetStudentPassword(BigInteger studentID) {
        return studentMapper.resetStudentPasswordByStudentID(studentID);
    }

    public Integer deleteStudentByStudentID(BigInteger studentID) {
        return studentMapper.deleteStudentByStudentID(studentID);
    }

    public Integer updateStudentActiveByStudent(Student student) {
        return studentMapper.updateStudentActiveByStudent(student);
    }

    public List<Student> listAllStudents() {
        return studentMapper.listAllStudents();
    }

    public List<Student> listNoTeamStudentsByCourseID(BigInteger courseID) {
        return studentMapper.listNoTeamStudentsByCourseID(courseID);
    }

    public String insertStudentList(BigInteger klassID, MultipartFile file) throws IOException {
        klassMapper.deleteKlassStudentByKlassID(klassID);
        String folder = FileUtil.getUploadedFolder();
        String excelUrl = folder + "//excel//" + fileUtil.uploadFile("//excel//", file);
        List<Row> rowList;
        try {
            rowList = excelUtil.readExcel(excelUrl);
            fileUtil.deleteUploadedFile("//excel//", excelUrl);
        }catch (IOException e){
            return "解析失败";
        }
        List<Student> studentList = new ArrayList<>();
        Row row;
        for (int i=2;i<rowList.size();i++) {
            row = rowList.get(i);
            Student student = new Student();
            student.setUsername(excelUtil.getCellValue(row.getCell(0)));
            student.setName(excelUtil.getCellValue(row.getCell(1)));
            student.setPassword("123456");
            student.setBeActive(0);
            studentList.add(student);
        }
        studentMapper.insertStudentList(studentList);
        BigInteger courseID = courseMapper.getCourseIDByKlassID(klassID);
        List<BigInteger> studentIDList = studentMapper.listStudentID(studentList);
        Map<String, Object> map = new HashMap<>(3);
        map.put("courseID", courseID);
        map.put("klassID", klassID);
        map.put("studentIDList", studentIDList);
        klassMapper.insertStudentKlass(map);
        return "插入成功";
    }
}
