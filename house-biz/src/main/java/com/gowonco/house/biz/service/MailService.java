package com.gowonco.house.biz.service;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.gowonco.house.biz.mapper.UserMapper;
import com.gowonco.house.common.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 邮件服务
 * @author gowonco
 * @date 2019-07-25 0:20
 */
@Service
public class MailService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${domain.name}")
    private String domainName;

    private final Cache<String, String> registerCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(15,TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String,String>() {

                @Override
                public void onRemoval(RemovalNotification<String, String> removalNotification) {
                    String email = removalNotification.getValue();
                    User user = new User();
                    user.setEmail(email);
                    List<User> targetUser = userMapper.selectUsersByQuery(user);
                    if (!targetUser.isEmpty() && Objects.equal(targetUser.get(0).getEnable(), 0)) {
                        userMapper.delete(email);// 代码优化: 在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作
                    }
                }
            }).build();

    /**
     * 1.缓存key-email关系
     * 2.借助spring mail发送邮件
     * 3.借助异步框架进行异步操作(即注解Async)
     * @param email
     */
    @Async
    public void registerNotify(String email){
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey, email);
        String url = "http://"+domainName+"/accounts/verify?key="+randomKey;
        sendMail("房产交易平台",url,email);
    }

    public  void sendMail(String title, String url, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }

    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if(StringUtils.isBlank(email)){
            return false;
        }
        User updateUser = new User();
        updateUser.setEnable(1);
        updateUser.setEmail(email);
        userMapper.update(updateUser);
        registerCache.invalidate(key);
        return true;
    }

}
