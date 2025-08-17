package com.zyp.springboot.learn.service.menu;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyp.springboot.learn.dto.menu.*;
import com.zyp.springboot.learn.entity.MenuEntity;
import com.zyp.springboot.learn.repository.MenuDao;
import com.zyp.springboot.learn.service.BaseService;
import com.zyp.springboot.learn.util.BeanUtils;
import com.zyp.springboot.learn.util.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MenuService extends BaseService<MenuDao, MenuEntity> {
    public boolean add(MenuAddReq req) {
        return this.save(BeanUtils.copy(req, getEntityClass()));
    }

    public IPage<MenuEntity> query(MenuQueryReq req) {
        LambdaQueryWrapper<MenuEntity> param = Wrappers.lambdaQuery();
        if (req.getId() != null) {
            param.eq(MenuEntity::getId, req.getId());
        }
        if (StrUtils.hasLength(req.getName())) {
            param.like(MenuEntity::getName, req.getName());
        }
        if (StrUtils.hasLength(req.getShowName())) {
            param.like(MenuEntity::getShowName, req.getShowName());
        }
        if (StrUtils.hasLength(req.getDescription())) {
            param.like(MenuEntity::getDescription, req.getDescription());
        }
        if (StrUtils.hasLength(req.getPath())) {
            param.like(MenuEntity::getParentId, req.getPath());
        }
        if (req.getParentId() != null) {
            param.eq(MenuEntity::getParentId, req.getParentId());
        }
        if (req.getPermissionId() != null) {
            param.eq(MenuEntity::getPermissionId, req.getPermissionId());
        }

        param.orderByDesc(MenuEntity::getCreateTime);
        var page = Page.<MenuEntity>of(req.getPage(), req.getSize());
        return this.page(page, param);
    }

    public boolean update(MenuUpdateReq req) {
        return this.updateById(BeanUtils.copy(req, getEntityClass()));
    }

    public static MenuTree buildMenuTree(List<MenuEntity> authorizedMenu) {
        MenuTree root = new MenuTree();
        var topMenus = authorizedMenu.stream().filter(m -> m.getParentId() == 0).toList();
        for (MenuEntity topMenu : topMenus) {
            MenuTree menuTree = new MenuTree();
            menuTree.setItem(MenuDTO.from(topMenu));
            root.addChildNode(menuTree);
            var childMenus = authorizedMenu.stream().filter(m -> Objects.equals(m.getParentId(), topMenu.getId())).toList();
            for (MenuEntity childMenu : childMenus) {
                menuTree.addChildMenu(MenuDTO.from(childMenu));
            }
            // 按order字段对子菜单项进行排序
            menuTree.getChildren().sort(Comparator.comparingInt(i -> i.getItem().getOrder()));
        }
        // 按order字段对父菜单项进行排序
        root.getChildren().sort(Comparator.comparingInt(i -> i.getItem().getOrder()));
        return root;
    }
}
