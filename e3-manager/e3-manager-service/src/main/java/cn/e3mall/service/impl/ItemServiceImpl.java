package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Override
	public TbItem getItemById(Long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultPage getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> pages = new PageInfo<>(list);
		ResultPage resultPage = new ResultPage((int) pages.getTotal(), list);

		return resultPage;
	}

	@Override
	public E3Result addItem(TbItem tbItem, String desc) {
		// 1）生成商品id
		Long id = IDUtils.genItemId();
		Date date = new Date();
		// 2）补全TbItem对象的属性
		// 商品状态，1-正常，2-下架，3-删除
		tbItem.setStatus((byte) 1);
		tbItem.setId(id);
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		// 3）向商品表中插入商品信息。
		tbItemMapper.insert(tbItem);
		// 4）创建TbItemDesc对象
		TbItemDesc tbItemDesc = new TbItemDesc();
		// 5）补全pojo对象的属性
		tbItemDesc.setItemId(tbItem.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		// 6）向商品描述表插入数据
		tbItemDescMapper.insert(tbItemDesc);
		// 7）返回E3Result，OK
		return E3Result.ok();
	}

}
