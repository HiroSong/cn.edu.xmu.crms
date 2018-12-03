package cn.edu.xmu.crms.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SongLingbing
 * @date 2018/12/1 0:06
 */
@Component
@Aspect
public class Authentication {
    @Pointcut("within(cn.edu.xmu.crms.controller..*) && !within(cn.edu.xmu.crms.controller.UserController)")
    public void pointCut(){}
    @Around("pointCut()")
    public Object checkInfo(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        if(request==null){
            return null;
        }
        if(request.getSession().isNew()){
            return null;
        }
        if( request.getSession().getAttribute("token")==null){
            return null;
        }
        return pjp.proceed();
    }
}
