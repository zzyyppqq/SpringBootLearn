package com.zyp.springboot.learn.controller;

import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.auth.permission.*;
import com.zyp.springboot.learn.dto.common.table.TableResultGenerator;
import com.zyp.springboot.learn.entity.PermissionEntity;
import com.zyp.springboot.learn.service.auth.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('permissions::read')")
    public RespDTO<PermissionListDTO> list(@Valid PermissionQueryReq req) {
        var generator = new TableResultGenerator<PermissionDTO, PermissionEntity>();
        var listDTO = new PermissionListDTO();
        generator.list(() -> permissionService.query(req), listDTO, PermissionDTO.class);
        return RespDTO.ok(listDTO);
    }

    @PostMapping("/permissions")
    @PreAuthorize("hasAuthority('permissions::write')")
    public RespDTO<Boolean> add(@RequestBody @Valid PermissionAddReq req) {
        return RespDTO.ok(permissionService.add(req));
    }

    @PutMapping("/permissions")
    @PreAuthorize("hasAuthority('permissions::write')")
    public RespDTO<Boolean> update(@RequestBody @Valid PermissionUpdateReq req) {
        return RespDTO.ok(permissionService.update(req));
    }

    @DeleteMapping("/permissions/{id}")
    @PreAuthorize("hasAuthority('permissions::write')")
    public RespDTO<Boolean> delete(@PathVariable long id) {
        return RespDTO.ok(permissionService.removeById(id));
    }

}
