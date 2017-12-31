package cn.e3mall.test.jedis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ClassPathUtils;
import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void jedisTest01() {
		// 创建一个jedis对象 需要制定ip地址和端口号
		Jedis jedis = new Jedis("192.168.25.128", 6379);
		// 直接使用jedis对象进行操作
		jedis.set("ming", "zi");
		String string = jedis.get("ming");
		System.out.println(string);
		// 关闭jedis
		jedis.close();
	}

	@Test
	public void testJedisPool() throws Exception {
		// 创建一jedispool对象 需要指定ip地址和端口号
		JedisPool pool = new JedisPool("192.168.25.128", 6379);
		// 从jedispool对象中取数据
		Jedis jedis = pool.getResource();
		// 使用jedis管理对象
		String string = jedis.get("ming");
		System.out.println(string);
		// 使用完毕关闭jedis对象 回收连接池
		jedis.close();
		// 系统结束之前需要关闭jedispool
		pool.close();

	}

	@Test
	public void jedisClusterTest() {
		// 创建一个jedisCluster对象 创建一个set对象 set中有很对hostANdport对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 直接使用jedis对象操作redis
		jedisCluster.set("wowo", "yeye");
		String string = jedisCluster.get("wowo");
		System.out.println(string);
		// 系统结束前关闭
		jedisCluster.close();
	}

	@Test
	public void testJedisClient() throws Exception {
		// 创建一个spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		// 获得对象
		JedisClient client = ac.getBean(JedisClient.class);
		client.set("niu", "dada");
		String string = client.get("niu");
		System.out.println(string);
	}

}
