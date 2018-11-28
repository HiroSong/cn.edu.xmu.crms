package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User selectUserByName(String name);
}