package cn.dbdj1201.shiro.service.impl;

import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.mapper.UserMapper;
import cn.dbdj1201.shiro.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
