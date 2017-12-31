package cn.e3mall.search.lisener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.net.nntp.Threadable;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.service.SearchService;

public class AddItemIndexLisener implements MessageListener {
	@Autowired
	private SearchService searchService;
	
	@Override
	public void onMessage(Message message) {
		try {
		//等待一秒,等Item数据保存到数据库之后再执行
		Thread.sleep(1000);
		// 1、接收消息
		TextMessage text = null;
		Long itemId = null;
		// 2、从消息中取商品id
			text = (TextMessage) message;
			itemId = Long.parseLong(text.getText());
			// 3、根据商品id调用service 添加数据
			searchService.addDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
