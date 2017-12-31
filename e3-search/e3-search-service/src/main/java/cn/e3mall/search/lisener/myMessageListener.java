package cn.e3mall.search.lisener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class myMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		//接收消息
		TextMessage text = (TextMessage) message;
		//打印消息
		try {
			System.out.println(text.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
