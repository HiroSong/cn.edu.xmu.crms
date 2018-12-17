package cn.edu.xmu.crms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author SongLingbing
 * @date 2018/11/30 23:28
 */
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {
}
