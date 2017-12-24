package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUiTreeNode;

public interface ItemCatService {

	public List<EasyUiTreeNode> getCatList(Long parentId);
}
