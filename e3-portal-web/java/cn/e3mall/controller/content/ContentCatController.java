package cn.e3mall.controller.content;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.content.service.ContentCatService;

/**
 * 内容分类管理Controller
 * @author 祥子
 *
 */
@Controller
public class ContentCatController {
	@Autowired
	private ContentCatService catService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUiTreeNode> getContentCategoryList(@RequestParam(value="id" ,defaultValue="0") long parentId){
		List<EasyUiTreeNode> list = catService.getContentCatList(parentId);
		
		return list;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result addContentCatgory(long parentId ,String name){
		E3Result e3Result = catService.addContentCat(parentId, name);
		return e3Result;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCategory(long id ,String name){
		catService.updateContentCatgery(id,name);
		return E3Result.ok();
	}
	
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public E3Result deleteContentCategory(long id){
		E3Result e3Result = catService.deleteContentCategory(id);
		return e3Result;
	}
	
}
