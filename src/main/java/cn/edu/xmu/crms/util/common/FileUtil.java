package cn.edu.xmu.crms.util.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/12/24 21:49
 */
@Component
public class FileUtil {
    private static String UPLOADED_FOLDER = "//home";

    public static String getUploadedFolder() {
        return UPLOADED_FOLDER;
    }

    public String uploadFile(String fileType, MultipartFile uploadFile){
        String url;
        if (uploadFile.isEmpty()) {
            return null;
        }
        try {
            url = this.saveUploadedFile(fileType, uploadFile);
        } catch (IOException e) {
            return null;
        }
        return url;
    }

    public void downloadFile(HttpServletResponse response,String fileType,  String fileName) {
        if (fileName != null) {
            File file = new File(UPLOADED_FOLDER  + fileType + fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private String saveUploadedFile(String fileType, MultipartFile file) throws IOException {
            if (file.isEmpty()) {
                return null;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileType + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
    }

    public String deleteUploadedFile(String fileType, String fileName) {
        File file = new File(UPLOADED_FOLDER  + fileType + fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return "删除成功";
            }
        }
        return "删除失败";
    }
}
