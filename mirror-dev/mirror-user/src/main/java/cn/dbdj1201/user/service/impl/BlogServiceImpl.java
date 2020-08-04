package cn.dbdj1201.user.service.impl;

import cn.dbdj1201.user.entity.Blog;
import cn.dbdj1201.user.mapper.BlogMapper;
import cn.dbdj1201.user.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
