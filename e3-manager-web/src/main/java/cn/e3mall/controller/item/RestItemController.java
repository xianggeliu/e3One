package cn.e3mall.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * Item的修改Controller
 * @author 祥子
 *
 */
@Controller
public class RestItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 根据ItemId查询商品描述的详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public TbItemDesc getItemDescByItemId(@PathVariable long id){
		TbItemDesc desc = itemService.getItemDescByItemId(id);
		return desc;
	}
	
	/**
	 * 根据Ids删除数据
	 * Ids是数组可以是多个
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result deleteItemByIds(long[] ids){
		itemService.deleteItemIds(ids);
		return E3Result.ok();
	}
	
	/**
	 * 根据ids上架数据
	 * Ids可以是多个
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result reshelfItem(long[] ids){
		itemService.reshelfItemById(ids);
		return E3Result.ok();
	}
	
	
	/**
	 * 根据Ids下架商品
	 * Ids是数组可以是多个
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result instockItem(long[] ids){
		itemService.instockItemById(ids);
		return E3Result.ok();
	}
	
	
	
}

