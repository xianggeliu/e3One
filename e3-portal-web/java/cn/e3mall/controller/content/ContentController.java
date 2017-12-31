package cn.e3mall.controller.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 内容管理页面
 * 
 * @author 祥子
 *
 */
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;

	/**
	 * 根据categoryId查找所有相关的内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public ResultPage getContentList(long categoryId, int page, int rows) {
		ResultPage pages = contentService.getContentList(categoryId, page, rows);
		return pages;
	}

	/**
	 * 保存Content的数据
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result saveContent(TbContent tbContent) {
		E3Result content = contentService.saveTbContent(tbContent);
		return content;
	}
	
	/**
	 * 根据contentId查找富文本内容
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/findDesc")
	@ResponseBody
	public TbContent findDesc(long id) {
		TbContent tbContent = contentService.findContentById(id);
		return tbContent;
	}
	
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(long[] ids) {
		contentService.deleteContentById(ids);
		return E3Result.ok();
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result updateContent(TbContent tbContent) {
		contentService.updateContent(tbContent);
		return E3Result.ok();
	}
	
	

}
