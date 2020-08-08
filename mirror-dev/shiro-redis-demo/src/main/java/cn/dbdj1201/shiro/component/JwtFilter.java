package cn.dbdj1201.shiro.component;

import cn.dbdj1201.shiro.entity.CommonResult;
import cn.dbdj1201.shiro.util.JwtUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 8:56
 */
@Component
public class JwtFilter extends AuthenticatingFilter {

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
            return false;
        }
    }

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
        CommonResult<Object> result = new CommonResult<>(400, throwable.getMessage(), null);
        String jsonStr = JSONUtil.toJsonStr(result);

        try {
            response.getWriter().print(jsonStr);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
        return false;
    }
}
