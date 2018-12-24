package cn.edu.xmu.crms.util.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SongLingbing
 * @date 2018/12/24 8:58
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(403);
        try{
            response.getWriter().write("{\"message\":  \"没有访问权限\"}");
        }catch (IOException exception){

        }
    }

}