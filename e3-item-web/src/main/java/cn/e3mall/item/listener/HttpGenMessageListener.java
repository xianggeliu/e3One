package cn.e3mall.item.listener;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 生成静态页面
 * @author 祥子
 *
 */

public class HttpGenMessageListener implements MessageListener {
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer configurer;
	@Value("${html.gen.path}")
	private String htmlPath;
	
	@Override
	public void onMessage(Message message) {
		try {
			// 创建一个MessageListener实现类
			// 在实现类中接收消息
			TextMessage text = (TextMessage) message;
			// 从消息中取商品id
			long itemId = Long.parseLong(text.getText());
			// 根据商品id查询商品基本信息和商品描述。
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
			//创建一个数据集
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			// 创建一个商品详情页面的freemarker模板
			Configuration configuration = configurer.getConfiguration();
			configuration.setDefaultEncoding("utf-8");
			Template template = configuration.getTemplate("item.ftl");
			// 指定文件的输出目录
			Writer out = new FileWriter(htmlPath + itemId + ".html");
			// 生成文件。文件名是商品id。路径任意目录，不要生成在工程内部
			template.process(data, out);
			// 关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
