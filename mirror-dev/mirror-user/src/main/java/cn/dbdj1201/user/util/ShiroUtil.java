package cn.dbdj1201.user.util;

import cn.dbdj1201.user.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 8:55
 */
//@Component
public class ShiroUtil {
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
