package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.content.service.ContentCatService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * 前台页面内容管理的Service
 * 
 * @author 祥子
 *
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	/**
	 * 根据parentId 显示目录树
	 */
	@Override
	public List<EasyUiTreeNode> getContentCatList(long parentId) {
		// 1）根据parentid查询内容分类列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<EasyUiTreeNode> nodes = new ArrayList<>();
		for (TbContentCategory tcb : list) {
			// 2）把内容分类列表转换成TreeNode的列表
			EasyUiTreeNode treeNode = new EasyUiTreeNode();
			treeNode.setId(tcb.getId());
			treeNode.setText(tcb.getName());
			treeNode.setState(tcb.getIsParent() ? "closed" : "open");
			nodes.add(treeNode);
		}
		// 3）返回结果

		return nodes;
	}

	/**
	 * 目录树增加节点;
	 */
	@Override
	public E3Result addContentCat(long id, String name) {
		// 1）创建一个TbContentCategory对象
		TbContentCategory tcb = new TbContentCategory();
		Date date = new Date();
		// 2）补全pojo对象的属性
		tcb.setParentId(id);
		tcb.setName(name);
		// 1(正常),2(删除)',
		tcb.setStatus(1);
		// 默认值排序
		tcb.setSortOrder(1);
		tcb.setCreated(date);
		tcb.setUpdated(date);
		// 判断是否为父节点 1为true，0为false',
		tcb.setIsParent(false);
		// 3）向数据库中插入数据
		tbContentCategoryMapper.insert(tcb);
		// 4）判断父节点的状态。如果不是父节点，应该改为父节点
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
		if (!category.getIsParent()) {
			category.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(category);
		}

		// 5）返回E3Result，其中包含TbContentCategory对象。

		return E3Result.ok(tcb);
	}
	
	/**
	 * 目录树节点修改
	 */

	@Override
	public void updateContentCatgery(long id, String name) {
		TbContentCategory tcb = new TbContentCategory();
		tcb.setId(id);
		tcb.setName(name);
		tbContentCategoryMapper.updateByPrimaryKeySelective(tcb);
	}

	@Override
	public E3Result deleteContentCategory(long id) {
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
		if (category.getIsParent()) {
			E3Result e3 = new E3Result();
			e3.setStatus(250);
			return e3;
		}
		tbContentCategoryMapper.deleteByPrimaryKey(id);
		return E3Result.ok(); 
	}

}
