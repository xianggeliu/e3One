package cn.e3mall.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {

	public TbItem getItemById(Long id);

	public ResultPage getItemList(int page, int rows);

	public E3Result addItem(TbItem tbItem, String desc);

	public TbItemDesc getItemDescByItemId(long id);

	public void deleteItemIds(long[] ids);

	public void reshelfItemById(long[] ids);

	public void instockItemById(long[] ids);

}
