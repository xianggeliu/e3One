package cn.e3mall.service.Activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SpringActiveMQ {

	@Test
	public void testSpringActiveMq() throws Exception {
		// 初始化spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMQ.xml");
		// 从spring容器中获得JmsTemplate对象
		JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
		// 从spring容器中取Destination对象
		Destination  destination = (Destination) ac.getBean("queueDestination");
		// 使用JmsTemplate对象发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage text = session.createTextMessage("一切都是不细心!");
				return text;
			}
		});
		
	}
	
	@Test
	public void testQueueProducer(){
		// 第一步：初始化一个spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMQ.xml");
		// 第二步：从容器中获得JMSTemplate对象。
		JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
		// 第三步：从容器中获得一个Destination对象
		Destination destination = (Destination) ac.getBean("queueDestination");
		// 第四步：使用JMSTemplate对象发送消息，需要知道Destination
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage text = session.createTextMessage("那就这样吧,");
				return text;
			}
		});
	}
}
