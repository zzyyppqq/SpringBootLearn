package com.zyp.springboot.learn.service.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyp.springboot.learn.constant.SpecialUsername;
import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.auth.permission.PermissionDTO;
import com.zyp.springboot.learn.dto.auth.role.RoleDTO;
import com.zyp.springboot.learn.dto.user.*;
import com.zyp.springboot.learn.entity.PermissionEntity;
import com.zyp.springboot.learn.entity.RoleEntity;
import com.zyp.springboot.learn.entity.UserEntity;
import com.zyp.springboot.learn.entity.UserRoleEntity;
import com.zyp.springboot.learn.infra.errorcode.BusinessException;
import com.zyp.springboot.learn.infra.errorcode.SystemErrorCode;
import com.zyp.springboot.learn.repository.UserDao;
import com.zyp.springboot.learn.service.BaseService;
import com.zyp.springboot.learn.service.auth.PermissionService;
import com.zyp.springboot.learn.service.auth.RoleService;
import com.zyp.springboot.learn.service.auth.UserRoleService;
import com.zyp.springboot.learn.util.BcryptUtils;
import com.zyp.springboot.learn.util.BeanUtils;
import com.zyp.springboot.learn.util.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class UserService extends BaseService<UserDao, UserEntity> {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
//    @Autowired
//    private MenuService menuService;
    @Autowired
    private PermissionService permissionService;
    public RespDTO<LoginUserDetails> login(UserLoginReq req) {
        var resp = new LoginUserDetails();
        // 按用户名从数据库查找用户
        UserEntity userEntity = getBaseMapper().getByUsername(req.getUsername());
        // 校验密码
        if (userEntity != null && BcryptUtils.matches(req.getPassword(), userEntity.getPassword())) {
            if (userEntity.getDisabled()) {
                return RespDTO.error(SystemErrorCode.USER_STATUS_ERROR);
            }
            resp.getLoginUserBasicInfo().fill(userEntity);
            return RespDTO.ok(resp);
        } else {
            return RespDTO.paramError("用户名或者密码错误");
        }
    }

    public RespDTO<UserFullInfoDTO> getUserData(Long uid) {
        UserEntity userEntity = getById(uid);
        if (userEntity == null) {
            return RespDTO.errorMsg(SystemErrorCode.DATA_NOT_EXIST, "用户不存在");
        } else {
            if (userEntity.getDisabled()) {
                throw new BusinessException(SystemErrorCode.USER_STATUS_ERROR);
            }
            var userData = new UserFullInfoDTO();
            userData.setUid(uid);
            userData.setUsername(userEntity.getUsername());
            userData.setNickName(userEntity.getNickName());
            // 角色和权限部分需要留到后面完成角色和权限模块之后
             fillRoleAndPermission(userData);
            return RespDTO.ok(userData);
        }
    }

    // 查询用户所属的角色列表及这些角色拥有的权限列表
    private void fillRoleAndPermission(UserFullInfoDTO userData) {
        var uid = userData.getUid();
        var username = userData.getUsername();
//        List<MenuEntity> authorizedMenu;
        List<PermissionEntity> permissions;
        List<RoleEntity> roles;
//        List<MenuEntity> allMenu = menuService.list();
        // super用户特殊处理，拥有所有的角色和权限
        if (SpecialUsername.SUPER.equals(username)) {
//            authorizedMenu = allMenu;
            permissions = permissionService.list();
            roles = roleService.list();
        } else {
            roles = this.roles(uid);
            List<Long> roleIdList = roles.stream().map(RoleEntity::getId).toList();
            // 角色拥有的权限列表
            permissions = roleService.permissions(roleIdList);
//            authorizedMenu = allMenu.stream().filter(m ->
//                    permissions.stream().anyMatch(p -> Objects.equals(p.getId(), m.getPermissionId()))).toList();
        }
//        MenuTree root = MenuService.buildMenuTree(authorizedMenu);
        // 简单的模型转换，从实体类转成DTO
        userData.setPermissions(permissions.stream().map(PermissionDTO::from).toList());
        userData.setRoles(roles.stream().map(RoleDTO::from).toList());
//        userData.setMenuTree(root);
    }

    public boolean add(UserAddReq req) {
        String encodedPwd = BcryptUtils.encode(req.getPassword());
        var userEntity = BeanUtils.copy(req, getEntityClass());
        userEntity.setPassword(encodedPwd);
        return save(userEntity);
    }

    public IPage<UserEntity> query(UserQueryReq userQueryReq) {
        LambdaQueryWrapper<UserEntity> param = Wrappers.lambdaQuery();
        if (userQueryReq.getId() != null) {
            param.eq(UserEntity::getUid, userQueryReq.getId());
        }
        if (userQueryReq.getUid() != null) {
            param.eq(UserEntity::getUid, userQueryReq.getUid());
        }
        if (StrUtils.hasLength(userQueryReq.getNickName())) {
            param.like(UserEntity::getNickName, userQueryReq.getNickName());
        }
        if (StrUtils.hasLength(userQueryReq.getUserName())) {
            param.like(UserEntity::getUsername, userQueryReq.getUserName());
        }
        param.orderByDesc(UserEntity::getCreateTime);
        var page = Page.<UserEntity>of(userQueryReq.getPage(), userQueryReq.getSize());
        return this.page(page, param);
    }

    public boolean update(UserUpdateReq req) {
        return updateById(BeanUtils.copy(req, getEntityClass()));
    }

    public List<RoleEntity> roles(long uid) {
        var userRoles = userRoleService.getByUidList(List.of(uid));
        var roleIdList = userRoles.stream().map(UserRoleEntity::getRoleId).toList();
        return roleService.listByIds(roleIdList);
    }

    public Map<Long, List<RoleEntity>> roles(List<Long> uidList) {
        var userRoles = userRoleService.getByUidList(uidList);
        var roleIdList = userRoles.stream().map(UserRoleEntity::getRoleId).toList();
        var roles = roleService.listByIds(roleIdList);
        Map<Long, List<RoleEntity>> rolesByUid = new HashMap<>();
        for (UserRoleEntity userRole : userRoles) {
            var assignedRoles = rolesByUid.get(userRole.getUid());
            if (assignedRoles == null) {
                assignedRoles = new ArrayList<>();
            }
            roles.stream()
                    .filter(r -> r.getId().equals(userRole.getRoleId()))
                    .findFirst()
                    .ifPresent(assignedRoles::add);
            rolesByUid.put(userRole.getUid(), assignedRoles);
        }
        return rolesByUid;
    }
}
