package cn.dbdj1201.user.service.impl;

import cn.dbdj1201.user.entity.User;
import cn.dbdj1201.user.mapper.UserMapper;
import cn.dbdj1201.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
