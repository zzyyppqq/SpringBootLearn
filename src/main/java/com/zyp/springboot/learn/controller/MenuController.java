package com.zyp.springboot.learn.controller;

import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.common.table.TableResultGenerator;
import com.zyp.springboot.learn.dto.menu.*;
import com.zyp.springboot.learn.entity.MenuEntity;
import com.zyp.springboot.learn.service.auth.PermissionService;
import com.zyp.springboot.learn.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/menus")
    @PreAuthorize("hasAuthority('menus::read')")
    public RespDTO<MenuListDTO> list(@Valid MenuQueryReq req) {
        var generator = new TableResultGenerator<MenuDTO, MenuEntity>();
        var listDTO = new MenuListDTO();
        generator.list(() -> menuService.query(req), listDTO, MenuDTO.class);
        // 查询关联的父菜单和权限信息
        var parentMenuIdList = listDTO.getData().stream().map(MenuDTO::getParentId).toList();
        var permissionIdList = listDTO.getData().stream().map(MenuDTO::getPermissionId).toList();
        var parentMenus = menuService.listByIds(parentMenuIdList);
        var permissions = permissionService.listByIds(permissionIdList);
        // 遍历菜单项，填充关联ID数据所对应的展示用信息
        listDTO.getData().forEach(item -> {
            Long parentId = item.getParentId();
            if (parentId != null) {
                // 如果有子菜单项，查询父菜单项的名称并设置到parentIdLabel，用于前端展示
                parentMenus.stream()
                        .filter(p -> p.getId().equals(parentId))
                        .findFirst()
                        .ifPresent(menu -> item.setParentIdLabel(menu.getName()));
            }
            // 查询权限的名称并设置到permissionIdLabel，用于前端展示
            permissions.stream()
                    .filter(p -> p.getId().equals(item.getPermissionId()))
                    .findFirst()
                    .ifPresent(p -> item.setPermissionIdLabel(p.getName()));
        });
        return RespDTO.ok(listDTO);
    }

    @PostMapping("/menus")
    @PreAuthorize("hasAuthority('menus::write')")
    public RespDTO<Boolean> add(@RequestBody @Valid MenuAddReq req) {
        return RespDTO.ok(menuService.add(req));
    }

    @PutMapping("/menus")
    @PreAuthorize("hasAuthority('menus::write')")
    public RespDTO<Boolean> update(@RequestBody @Valid MenuUpdateReq req) {
        return RespDTO.ok(menuService.update(req));
    }

    @DeleteMapping("/menus/{id}")
    @PreAuthorize("hasAuthority('menus::write')")
    public RespDTO<Boolean> delete(@PathVariable long id) {
        return RespDTO.ok(menuService.removeById(id));
    }

}