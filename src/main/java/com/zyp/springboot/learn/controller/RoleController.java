package com.zyp.springboot.learn.controller;

import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.auth.permission.PermissionDTO;
import com.zyp.springboot.learn.dto.auth.role.*;
import com.zyp.springboot.learn.dto.auth.role_permission.RolePermissionQueryReq;
import com.zyp.springboot.learn.dto.common.AdjustReq;
import com.zyp.springboot.learn.dto.common.table.TableResultGenerator;
import com.zyp.springboot.learn.entity.RoleEntity;
import com.zyp.springboot.learn.entity.RolePermissionEntity;
import com.zyp.springboot.learn.infra.errorcode.SystemErrorCode;
import com.zyp.springboot.learn.service.auth.PermissionService;
import com.zyp.springboot.learn.service.auth.RolePermissionService;
import com.zyp.springboot.learn.service.auth.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('roles::read')")
    public RespDTO<RoleListDTO> list(@Valid RoleQueryReq req) {
        var generator = new TableResultGenerator<RoleDTO, RoleEntity>();
        var listDTO = new RoleListDTO();
        generator.list(() -> roleService.query(req), listDTO, RoleDTO.class);
        return RespDTO.ok(listDTO);
    }

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('roles::write')")
    public RespDTO<Boolean> add(@RequestBody @Valid RoleAddReq req) {
        return RespDTO.ok(roleService.add(req));
    }

    @PutMapping("/roles")
    @PreAuthorize("hasAuthority('roles::write')")
    public RespDTO<Boolean> update(@RequestBody @Valid RoleUpdateReq req) {
        return RespDTO.ok(roleService.update(req));
    }

    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('roles::write')")
    public RespDTO<Boolean> delete(@PathVariable long id) {
        return RespDTO.ok(roleService.removeById(id));
    }

    // 返回角色已经分配的权限列表
    @GetMapping("/roles/{id}/permissions")
    @PreAuthorize("hasAuthority('roles::read')")
    public RespDTO<List<PermissionDTO>> permissions(@PathVariable long id) {
        var rolePermissionQueryReq = new RolePermissionQueryReq();
        rolePermissionQueryReq.setRoleId(id);
        rolePermissionQueryReq.setPage(1);
        rolePermissionQueryReq.setSize(50000);
        var rolePermissions = rolePermissionService.query(rolePermissionQueryReq).getRecords();
        var permissionIds = rolePermissions.stream().map(RolePermissionEntity::getPermissionId).toList();
        var permissions = permissionService.listByIds(permissionIds);
        return RespDTO.ok(permissions.stream().map(PermissionDTO::from).toList());
    }

    // 给角色分配权限：包括权限新增和移除
    @PostMapping("/roles/{id}/permissions")
    @PreAuthorize("hasAuthority('roles::write')")
    public RespDTO<Boolean> adjustPermissions(@PathVariable long id, @RequestBody AdjustReq req) {
        RoleEntity role = this.roleService.getById(id);
        if (role == null) {
            return RespDTO.errorMsg(SystemErrorCode.DATA_NOT_EXIST, "找不到角色");
        }
        return rolePermissionService.batchOperation(id, req);
    }
}
