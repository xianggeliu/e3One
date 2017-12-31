package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.pojo.TbContent;


public interface ContentService {

	public ResultPage getContentList(long categoryId, int page, int rows);
	
	public E3Result saveTbContent(TbContent tbContent);

	public TbContent findContentById(long id);

	public void deleteContentById(long[] ids);

	public void updateContent(TbContent tbContent);

	public List<TbContent> getContentListByCategoryId(long ad1);
	
}
