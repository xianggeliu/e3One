package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 前台页面的展示
 * 
 * @author 祥子
 *
 */
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	@Value("${index.ad1.cid}")
	private long ad1;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		List<TbContent> ad1List = contentService.getContentListByCategoryId(ad1);
		model.addAttribute("ad1List", ad1List);
		return "index";
	}

}
