package cn.e3mall.service.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.terracotta.quartz.TerracottaToolkitBuilder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

/**
 * helper的测试
 * 
 * @author 祥子
 *
 */
public class PageHelperTest {

	@Test
	public void helperTest() {
		// 先创建helper
		PageHelper.startPage(1, 10);
		// 从spring容器中获取mapper
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper mapper = ac.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = mapper.selectByExample(example);
		
		// 获取结果 使用pageinfo
		PageInfo<TbItem> page = new PageInfo<>(list);

		System.out.println("总页数为 : " + page.getTotal());
		System.out.println("总记录数是  : " + page.getPages());
		System.out.println("每页显示的条数是 : " + list.size());
	}
	
	@Test
	public void arrTest(){
		int[] arr = {1,9,10,2,6};
		int max = 0;
		int min = 1;
		int tmp = 0;
		for (int i = 0; i < arr.length; i++) {
			if(max < arr[i]){
				
				max = arr[i];
			}
			if (min > arr[i]) {
				min = arr[i];
			}
		}
		arr[0] = max;
		arr[arr.length-1] = min;
		System.out.println(Arrays.toString(arr));
	}

}
