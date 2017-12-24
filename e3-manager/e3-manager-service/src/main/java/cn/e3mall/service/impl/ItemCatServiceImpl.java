package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	/**
	 * 根据id查找数据
	 */
	@Override
	public List<EasyUiTreeNode> getCatList(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		List<EasyUiTreeNode> easys = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			// 先创建一个EasyUiTreeNode 用来存放数据
			EasyUiTreeNode node = new EasyUiTreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent() ? "closed" : "open");
			// 把封装好的node放入到list中
			easys.add(node);
		}
		// 把easys返回
		return easys;
	}

}
