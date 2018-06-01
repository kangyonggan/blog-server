package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.Digests;
import com.kangyonggan.app.util.Encodes;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.server.constants.AppConstants;
import com.kangyonggan.server.constants.YesNo;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.RoleMapper;
import com.kangyonggan.server.mapper.UserMapper;
import com.kangyonggan.server.model.User;
import com.kangyonggan.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<User> searchUsers(Params params) {
        Example example = new Example(User.class);

        Example.Criteria criteria = example.createCriteria();
        String username = params.getQuery().getString("username");
        if (StringUtils.isNotEmpty(username)) {
            criteria.andLike("username", StringUtil.toLike(username));
        }
        String name = params.getQuery().getString("name");
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", StringUtil.toLike(name));
        }
        Date startCreatedTime = params.getQuery().getDate("startCreatedTime");
        if (startCreatedTime != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startCreatedTime);
        }
        Date endCreatedTime = params.getQuery().getDate("endCreatedTime");
        if (endCreatedTime != null) {
            criteria.andLessThanOrEqualTo("createdTime", endCreatedTime);
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    public void saveUser(User user) {
        entryptPassword(user);
        myMapper.insertSelective(user);
    }

    @Override
    public void updateUser(User user) {
        myMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updatePassword(User user, String usernames) {
        entryptPassword(user);

        Example example = new Example(User.class);
        example.createCriteria().andIn("username", Arrays.asList(usernames.split(",")));

        myMapper.updateByExampleSelective(user, example);
    }

    @Override
    public boolean existsUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return super.exists(user);
    }

    @Override
    public void updateUserRoles(String username, String roleCodes) {
        roleMapper.deleteAllRolesByUsername(username);

        if (StringUtils.isNotEmpty(roleCodes)) {
            userMapper.insertUserRoles(username, Arrays.asList(roleCodes.split(",")));
        }
    }

    /**
     * 设定安全的密码，生成随机的salt并经过N次 sha-1 hash
     *
     * @param user
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(AppConstants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, AppConstants.HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }
}
