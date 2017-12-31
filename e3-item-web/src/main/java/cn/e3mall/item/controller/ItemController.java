package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * 商品详情Controller
 * @author 祥子
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 通过ItemId 查询商品详情
	 */
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable long itemId ,Model model){
		TbItem tbItem = itemService.getItemById(itemId);
		TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
		Item item = new Item(tbItem);
		//把数据传递到前台
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	
	
}
