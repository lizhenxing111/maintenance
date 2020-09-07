package com.lq.maintenance.core.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(
        name = "sys_menu"
)
public class SysMenu {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer menuId;
    private Integer parentMenuId;
    private String menuName;
    private String menuUrl;
    private String menuDescription;
    private String menuIcon;
    @Transient
    private List<SysMenu> childrenMenu = new ArrayList();

    public SysMenu() {
    }

    public List<SysMenu> getChildrenMenu() {
        return this.childrenMenu;
    }

    public void setChildrenMenu(List<SysMenu> childrenMenu) {
        this.childrenMenu = childrenMenu;
    }

    public String getMenuIcon() {
        return this.menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getParentMenuId() {
        return this.parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return this.menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuDescription() {
        return this.menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }
}
