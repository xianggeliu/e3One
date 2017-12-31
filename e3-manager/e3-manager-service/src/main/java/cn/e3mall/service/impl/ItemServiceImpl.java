package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.ResultPage;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	// 商品在缓存中的过期时间
	@Value("${item.expier}")
	private int expier;

	@Override
	public TbItem getItemById(Long itemId) {
		// 用try是为了查询索引库抛异常 不影响正常从数据库中查询数据
		try {
			// 先从缓存中查找,
			String item = jedisClient.get("item_info:" + itemId + ":base");
			// 判断item是否为空
			if (StringUtils.isNotBlank(item)) {
				TbItem tbItem = JsonUtils.jsonToPojo(item, TbItem.class);
				return tbItem;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		try {
			jedisClient.set("item_info:" + itemId + ":base", JsonUtils.objectToJson(tbItem));
			// 设置过期时间
			jedisClient.expire("item_info:" + itemId + ":base", expier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}

	@Override
	public ResultPage getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> pages = new PageInfo<>(list);
		ResultPage resultPage = new ResultPage((int) pages.getTotal(), list);

		return resultPage;
	}

	@Override
	public E3Result addItem(TbItem tbItem, String desc) {
		// 1）生成商品id
		final Long id = IDUtils.genItemId();
		Date date = new Date();
		// 2）补全TbItem对象的属性
		// 商品状态，1-正常，2-下架，3-删除
		tbItem.setStatus((byte) 1);
		tbItem.setId(id);
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		// 3）向商品表中插入商品信息。
		tbItemMapper.insert(tbItem);
		// 4）创建TbItemDesc对象
		TbItemDesc tbItemDesc = new TbItemDesc();
		// 5）补全pojo对象的属性
		tbItemDesc.setItemId(tbItem.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		// 6）向商品描述表插入数据
		tbItemDescMapper.insert(tbItemDesc);
		// 发送一个商品信息到消息队列里
		jmsTemplate.send(topicDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(id + "");
				return message;
			}
		});
		// 7）返回E3Result，OK
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescByItemId(long itemId) {
		// 有try是为了防止查询索引库抛异常 不影响正常的查询功能
		try {
			// 先从缓存中获取数据
			String desc = jedisClient.get("item_info:" + itemId + ":desc");
			// 判断desc中是否有数据
			if (StringUtils.isNotBlank(desc)) {
				// 有数据就把数据返回
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(desc, TbItemDesc.class);
				return itemDesc;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		try {

			jedisClient.set("item_info:" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
			// 设置过期时间
			jedisClient.expire("item_info:" + itemId + ":desc", expier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItemDesc;
	}

	@Override
	public void deleteItemIds(long[] ids) {
		for (long id : ids) {
			// '商品状态，1-正常，2-下架，3-删除',
			TbItem item = tbItemMapper.selectByPrimaryKey(id);
			// 修改商品的状态
			item.setStatus((byte) 3);
			tbItemMapper.updateByPrimaryKeySelective(item);
		}

	}

	@Override
	public void reshelfItemById(long[] ids) {
		for (long id : ids) {
			// '商品状态，1-正常，2-下架，3-删除',
			TbItem item = tbItemMapper.selectByPrimaryKey(id);
			// 修改商品的状态
			item.setStatus((byte) 1);
			tbItemMapper.updateByPrimaryKeySelective(item);
		}

	}

	@Override
	public void instockItemById(long[] ids) {

		for (long id : ids) {
			// '商品状态，1-正常，2-下架，3-删除',
			TbItem item = tbItemMapper.selectByPrimaryKey(id);
			// 修改商品的状态
			item.setStatus((byte) 2);
			tbItemMapper.updateByPrimaryKeySelective(item);
		}
	}



}
