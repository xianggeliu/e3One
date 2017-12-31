package cn.e3mall.item.controller;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 模板控制的Controller
 * @author 祥子
 *
 */
@Controller
public class HtmlGenController {
	@Autowired
	private FreeMarkerConfigurer config;
	
	@RequestMapping("/html/item")
	@ResponseBody
	public String getHtml() throws Exception{
		// 1、从spring容器中获得FreeMarkerConfigurer对象。
		// 2、从FreeMarkerConfigurer对象中获得Configuration对象。
		Configuration configuration = config.getConfiguration();
		// 3、使用Configuration对象获得Template对象。
		Template template = configuration.getTemplate("test.ftl");
		// 4、创建数据集
		Map map = new HashMap<>();
		map.put("test","请叫我英雄!~");
		// 5、创建输出文件的Writer对象。
		Writer wr = new FileWriter("E:/demo/tests.html");
		// 6、调用模板对象的process方法，生成文件。
		template.process(map, wr);
		// 7、关闭流。
		wr.close();
		return "ok";
	}
	
}
