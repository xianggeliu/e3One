package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;

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
		// 做添加动作的时候先删除redis中的缓存数据
		jedisClient.hdel("content-info", tbContent.getCategoryId() + "");
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
			//需要先查找到Content
			TbContent tbContent = tbContentMapper.selectByPrimaryKey(id);
			// 做添加动作的时候先删除redis中的缓存数据
			jedisClient.hdel("content-info", tbContent.getCategoryId() + "");
			tbContentMapper.deleteByPrimaryKey(id);

		}

	}

	/**
	 * 根据id修改数据
	 */
	@Override
	public void updateContent(TbContent tbContent) {
		// 做修改动作的时候先删除redis中的缓存数据
		jedisClient.hdel("content-info", tbContent.getCategoryId() + "");
		tbContent.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKeySelective(tbContent);
	}

	@Override
	public List<TbContent> getContentListByCategoryId(long ad1) {
		try {
			String hget = jedisClient.hget("content-info", ad1 + "");
			// 先查询redis里没有缓存
			if (StringUtils.isNotBlank(hget)) {
				List<TbContent> contents = JsonUtils.jsonToList(hget, TbContent.class);
				return contents;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(ad1);
		// 1）根据cid查询内容列表
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		try {
			String json = JsonUtils.objectToJson(list);
			// 把数据存到缓存中
			jedisClient.hset("content-info", ad1 + "", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 2）返回结果
		return list;
	}

}
