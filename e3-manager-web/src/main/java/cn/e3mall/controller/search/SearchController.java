package cn.e3mall.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.search.service.SearchService;

/**
 * 索引维护Controller
 * @author 祥子
 *
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList() throws Exception{
		E3Result e3Result = searchService.importItemList();
		return e3Result;
	}
	
	
	
}
