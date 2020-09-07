package com.lq.maintenance.sys.service.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.lq.maintenance.core.dao.SysMenuMapper;
import com.lq.maintenance.core.model.SysMenu;
import com.lq.maintenance.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImp implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    public SysMenuServiceImp() {
    }

    public HashMap<String, Object> getMenuData() {
        HashMap<String, Object> allMenu = new HashMap();
        List<SysMenu> treeMenu = this.getMenusByParentMenuId(1);
        allMenu.put("treeMenu", treeMenu);
        return allMenu;
    }

    public void addMenu(SysMenu sysMenu) {
        this.sysMenuMapper.insertSelective(sysMenu);
    }

    public void removeMenu(SysMenu sysMenu) {
    }

    public void updateMenu(SysMenu sysMenu) {
        this.sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    List<SysMenu> getMenusByParentMenuId(Integer parentMenuId) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentMenuId(parentMenuId);
        List<SysMenu> menus = this.sysMenuMapper.select(sysMenu);
        Iterator var4 = menus.iterator();

        while(var4.hasNext()) {
            SysMenu menu = (SysMenu)var4.next();
            menu.setChildrenMenu(this.getMenusByParentMenuId(menu.getMenuId()));
        }

        return menus;
    }
}
