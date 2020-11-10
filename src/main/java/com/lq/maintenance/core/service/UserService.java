package com.lq.maintenance.core.service;

import com.lq.maintenance.core.model.SysUser;

import java.util.Set;

/**
 * @author LZX
 * @Description 用户管理
 * @date 2020/10/27
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 查找用户的菜单权限标识集合
     *
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

    /**
     * @description 注册用户
     * @author lzx
     * @date 2020年11月10日 22:09
     */
    void registerUser(String username, String password);

}
