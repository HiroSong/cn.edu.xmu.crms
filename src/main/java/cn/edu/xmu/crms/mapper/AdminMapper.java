package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author SongLingbing
 * @date 2018/12/27 19:22
 */
@Mapper
@Component
public interface AdminMapper {
    /**
      * 获取管理员对象以校验身份
      *
      * @param username 管理员账号
      * @return 管理员对象
      * @author SongLingbing
      * @date 2018/12/27 20:07
      */
    Admin getAdminByAdminAccount(String username);
}
