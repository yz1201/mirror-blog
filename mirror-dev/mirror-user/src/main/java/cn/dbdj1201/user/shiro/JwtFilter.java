package cn.dbdj1201.user.shiro;

import cn.dbdj1201.user.entity.CommonResult;
import cn.dbdj1201.user.util.JwtUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sun.net.httpserver.Authenticator;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 8:56
 */
@Component
public class JwtFilter extends AuthenticatingFilter {

    /*
        createToken：实现登录，我们需要生成我们自定义支持的JwtToken

        onAccessDenied：拦截校验，当头部没有Authorization时候，我们直接通过，不需要自动登录 ?；当带有的时候，
     首先我们校验jwt的有效性，没问题我们就直接执行executeLogin方法实现自动登录

        onLoginFailure：登录异常时候进入的方法，我们直接把异常信息封装然后抛出

        preHandle：拦截器的前置拦截，因为我们是前后端分析项目，项目中除了需要跨域全局配置之外，我们在拦截器中也需要提供跨域支持。这样，拦截器才不会在进入Controller之前就被限制了。
   */

    @Autowired
    private JwtUtils jwtUtils;


    /**
     * 判断是否允许通过
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("isAccessAllowed方法");
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
//            System.out.println("错误" + e);
            return false;
        }
    }

//    /**
//     * 是否进行登录请求
//     * @param request
//     * @param response
//     * @return
//     */
//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        System.out.println("isLoginAttempt方法");
//        String token=((HttpServletRequest)request).getHeader("token");
//        if (token!=null){
//            return true;
//        }
//        return false;
//    }


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String jwt = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (StrUtil.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String jwt = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        //return true父类亲自处理，否则自己处理
        if (StrUtil.isEmpty(jwt)) {
            return true;
        } else {
            Claims claims = jwtUtils.getClaimByToken(jwt);
            if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
                throw new ExpiredCredentialsException("token失效了，请重新登录<(￣︶￣)↗[GO!]");
            }
            return executeLogin(servletRequest, servletResponse);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        CommonResult<Object> result = new CommonResult<>(400, throwable.getMessage());
        String jsonStr = JSONUtil.toJsonStr(result);

        try {
            response.getWriter().print(jsonStr);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
        return false;
    }

//    @Override
//    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
//        ThreadContext.bind(subject);
//        return super.onLoginSuccess(token, subject, request, response);
//    }

    /**
     * 跨域请求的处理通过
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
