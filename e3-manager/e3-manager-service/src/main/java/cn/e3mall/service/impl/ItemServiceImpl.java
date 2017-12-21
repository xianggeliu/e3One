package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Override
	public TbItem getItemById(Long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}
	@Override
	public ResultPage getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example );
		PageInfo<TbItem> pages = new PageInfo<>(list);
		ResultPage resultPage = new ResultPage((int) pages.getTotal(), list);
		
		return resultPage;
	}

}
