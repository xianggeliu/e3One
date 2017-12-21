package cn.e3mall.service;

import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.pojo.TbItem;

public interface ItemService {

	public TbItem getItemById(Long id);
	
	public ResultPage getItemList(int page ,int rows);
}
