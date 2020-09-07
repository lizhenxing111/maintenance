package com.lq.maintenance.sys.controller;

import java.util.HashMap;

import com.lq.maintenance.core.AbstractGlobleController;
import com.lq.maintenance.core.model.SysMenu;
import com.lq.maintenance.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/menu"})
public class MenuController extends AbstractGlobleController {
    @Autowired
    private SysMenuService sysMenuService;

    public MenuController() {
    }

    @RequestMapping({"/loginmenu"})
    public HashMap<String, Object> queryMenuByLoginUser() {
        new HashMap();
        HashMap<String, Object> menuData = this.sysMenuService.getMenuData();
        return this.success(menuData);
    }

    @RequestMapping({"/menuTreeList"})
    public HashMap<String, Object> queryMenuLists() {
        new HashMap();
        HashMap<String, Object> menuData = this.sysMenuService.getMenuData();
        return this.success(menuData);
    }

    @RequestMapping({"/add"})
    public HashMap<String, Object> addMenu(SysMenu sysMenu) {
        HashMap<String, Object> map = new HashMap();
        this.sysMenuService.addMenu(sysMenu);
        return map;
    }

    @RequestMapping(
            value = {"/updateMenu"},
            method = {RequestMethod.POST}
    )
    public HashMap<String, Object> updateMenu(@RequestBody SysMenu sysMenu) {
        HashMap<String, Object> map = new HashMap();
        this.sysMenuService.updateMenu(sysMenu);
        return map;
    }

    @RequestMapping({"/remove"})
    public HashMap<String, Object> removeMenu(SysMenu sysMenu) {
        HashMap<String, Object> map = new HashMap();
        this.sysMenuService.removeMenu(sysMenu);
        return this.success(map);
    }
}

