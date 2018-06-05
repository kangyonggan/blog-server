package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.Digests;
import com.kangyonggan.app.util.Encodes;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.server.constants.AppConstants;
import com.kangyonggan.server.constants.Status;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.RoleMapper;
import com.kangyonggan.server.mapper.UserMapper;
import com.kangyonggan.server.model.User;
import com.kangyonggan.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
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
        Date startDate = params.getQuery().getDate("startDate");
        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startDate);
        }
        Date endDate = params.getQuery().getDate("endDate");
        if (endDate != null) {
            criteria.andLessThanOrEqualTo("createdTime", endDate);
        }

        String sort = params.getAppSort();
        String order = params.getAppOrder();
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
        if (StringUtils.isNotEmpty(user.getPassword())) {
            entryptPassword(user);
        }
        myMapper.updateByPrimaryKeySelective(user);
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

    @Override
    public void deleteUser(Long id) {
        myMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updatePassword(Long id, String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);

        entryptPassword(user);
        myMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setStatus(Status.ENABLE.getCode());

        return myMapper.selectOne(user);
    }

    @Override
    public User findUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setStatus(Status.ENABLE.getCode());

        return myMapper.selectOne(user);
    }

    @Override
    public User login(User user) {
        User dbUser = findUserByUsername(user.getUsername());
        if (dbUser == null) {
            return null;
        }
        byte[] salt = Encodes.decodeHex(dbUser.getSalt());
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, AppConstants.HASH_INTERATIONS);
        String password = Encodes.encodeHex(hashPassword);
        return dbUser.getPassword().equals(password) ? dbUser : null;
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
