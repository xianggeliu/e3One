package cn.e3mall.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

/**
 * 前台页面搜索的controller
 * 
 * @author 祥子
 *
 */

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@Value("${search.rows}")
	private int rows;

	@RequestMapping("/search")
	public String searchShow(Model model, String keyword, @RequestParam(defaultValue = "1") int page) throws Exception {
		if (StringUtils.isNotBlank(keyword)) {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		}
		SearchResult search = searchService.search(keyword, page, rows);
		model.addAttribute("itemList", search.getItemList());
		model.addAttribute("totalPages", search.getTotalPages());
		model.addAttribute("recourdCount", search.getRecourdCount());
		// 查询的数据回显
		model.addAttribute("page", page);
		model.addAttribute("query", keyword);
		return "search";
	}

}
