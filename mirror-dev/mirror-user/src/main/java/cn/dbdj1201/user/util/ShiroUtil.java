package cn.dbdj1201.user.util;

import cn.dbdj1201.user.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author yz1201
 * @date 2020-08-03 22:28
 **/
public class ShiroUtil {
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
