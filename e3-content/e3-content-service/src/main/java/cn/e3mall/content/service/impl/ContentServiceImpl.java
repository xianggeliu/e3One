package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;

	/**
	 * 根据categoryId 查询所有的相关content数据
	 */
	@Override
	public ResultPage getContentList(long categoryId, int page, int rows) {
		// 1）设置分页信息
		PageHelper.startPage(page, rows);
		// 2）根据内容的分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		// 3）取分页结果
		PageInfo<TbContent> pages = new PageInfo<>(list);
		// 4）创建一个DataGridResult对象
		// 5）设置属性
		ResultPage result = new ResultPage((int) pages.getTotal(), list);
		// 6）返回DataGridResult对象。
		return result;
	}

	/**
	 * 保存content数据
	 */
	@Override
	public E3Result saveTbContent(TbContent tbContent) {
		// 1）补全pojo对象的属性
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		// 2）向内容表插入数据
		tbContentMapper.insert(tbContent);
		// 3）返回成功
		return E3Result.ok();
	}

	/**
	 * 根据Id查询数据 前台页面回显数据使用
	 */
	@Override
	public TbContent findContentById(long id) {
		TbContent content = tbContentMapper.selectByPrimaryKey(id);
		return content;
	}

	/**
	 * 根据id删除数据
	 */
	@Override
	public void deleteContentById(long[] ids) {
		for (long id : ids) {
			tbContentMapper.deleteByPrimaryKey(id);

		}

	}

	/**
	 * 根据id修改数据
	 */
	@Override
	public void updateContent(TbContent tbContent) {
		tbContent.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKeySelective(tbContent);
	}

}
