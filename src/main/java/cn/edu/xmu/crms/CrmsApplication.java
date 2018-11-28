package cn.edu.xmu.crms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
public class CrmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmsApplication.class, args);
    }
}
