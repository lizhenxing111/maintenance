package com.lq.maintenance.core.service.impl;

import com.lq.maintenance.core.dao.SysUserMapper;
import com.lq.maintenance.core.model.SysUser;
import com.lq.maintenance.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class SysUserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        SysUser user = sysUserMapper.selectOne(sysUser);
        return user;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");
        return permissions;
    }

    @Override
    public void registerUser(String username, String password) {
        String encodePassword = new BCryptPasswordEncoder().encode(password);
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setUserPassword(encodePassword);
        sysUser.setCreateDate(new Date());
        sysUser.setUserLock(true);
        sysUserMapper.insertSelective(sysUser);
    }

}
