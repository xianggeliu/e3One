package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeNode;

public interface ContentCatService {

	public List<EasyUiTreeNode> getContentCatList(long parentId);
	
	public E3Result addContentCat(long id , String name);

	public void updateContentCatgery(long id, String name);

	public E3Result deleteContentCategory(long id);
}
