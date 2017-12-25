package cn.e3mall.controller.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.service.ItemCatService;

/**
 * 种类的选择
 * 
 * @author 祥子
 *
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 根据parentId获取 数据列表
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUiTreeNode> getCatList(@RequestParam(value = "id", defaultValue = "0") long parentId) {
		List<EasyUiTreeNode> list = itemCatService.getCatList(parentId);

		return list;
	}

}
