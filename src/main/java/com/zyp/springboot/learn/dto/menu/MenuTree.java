package com.zyp.springboot.learn.dto.menu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 给前端渲染的菜单树，已经经过了权限过滤
@Data
public class MenuTree {
    private MenuDTO item;
    private List<MenuTree> children = new ArrayList<>();

    public void addChildNode(MenuTree childNode) {
        children.add(childNode);
    }

    public void addChildMenu(MenuDTO menu) {
        var node = new MenuTree();
        node.item = menu;
        children.add(node);
    }
}
