package cn.e3mall.freemarker.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {
	public static void main(String[] args) throws Exception {
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(
				new File("F:/workspace/projectOne/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");
		// 第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("demo.ftl");
		// 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map data = new HashMap<>();
		Student student = new Student(1, "牛大大1", "女", "北街");
		data.put("student", student);
		List<Student> list = new ArrayList<>();
		list.add(new Student(1, "牛大大1", "女", "北街"));
		list.add(new Student(2, "牛大大2", "女", "北街"));
		list.add(new Student(3, "牛大大3", "女", "北街"));
		list.add(new Student(4, "牛大大4", "女", "北街"));
		list.add(new Student(5, "牛大大5", "女", "北街"));
		list.add(new Student(6, "牛大大6", "女", "北街"));
		list.add(new Student(7, "牛大大7", "女", "北街"));
		Date date = new Date();
		data.put("date", date);
		 data.put("list", list);
		 data.put("test", "梦一场!");
		// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		// Writer we = new FileWriter(new File("E:/demo/test.html"));
		Writer we = new FileWriter(new File("E:/demo/demo.html"));
		// 第七步：调用模板对象的process方法输出文件。
		template.process(data, we);
		// 第八步：关闭流。
		we.close();
	}
	


}
