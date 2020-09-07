package com.lq.maintenance.sys.service;

import com.lq.maintenance.core.model.SysMenu;

import java.util.HashMap;

public interface SysMenuService {
    HashMap<String, Object> getMenuData();

    void addMenu(SysMenu sysMenu);

    void removeMenu(SysMenu sysMenu);

    void updateMenu(SysMenu sysMenu);
}
