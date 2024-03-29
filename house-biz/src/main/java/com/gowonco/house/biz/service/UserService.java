package com.gowonco.house.biz.service;

import com.google.common.collect.Lists;
import com.gowonco.house.biz.mapper.UserMapper;
import com.gowonco.house.common.model.User;
import com.gowonco.house.common.utils.BeanHelper;
import com.gowonco.house.common.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gowonco
 * @date 2019-07-16 0:16
 */
@Service
@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Value("${file.prefix}")
    private String imgPrefix;


    public List<User> getUsers() {
        return userMapper.selectUsers();
    }

    /**
     * 1.插入数据库（非激活）；密码加盐MD5；保存头像到本地
     * 2.生成key 绑定email
     * 3.发送邮件用户
     *
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()) {
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account, User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        userMapper.insert(account);
        mailService.registerNotify(account.getEmail());
        return true;
    }

    public boolean enable(String key) {
        return mailService.enable(key);
    }

    /**
     * 用户名密码验证
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    public User auth(String username, String password){
        User user = new User();
        user.setEmail(username);
        user.setPasswd(HashUtils.encryPassword(password));
        user.setEnable(1);
        List<User> list = getUserByQuery(user);
        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    public List<User> getUserByQuery(User user){
        List<User> list = userMapper.selectUsersByQuery(user);
        list.forEach(u ->{
            u.setAvatar(imgPrefix+u.getAvatar());
        });
        return list;
    }


}
