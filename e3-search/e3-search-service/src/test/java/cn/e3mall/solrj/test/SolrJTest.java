package cn.e3mall.solrj.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	// 添加文档
	@Test
	public void SolrJOneTest() throws SolrServerException, IOException {
		// 创建一个搜录入server对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		// 创建一个Document对象
		SolrInputDocument document = new SolrInputDocument();
		// 向文档对象中添加域
		document.setField("id", "001");
		document.setField("item_title", "哇咔咔");
		document.setField("item_sell_point", "哇咔咔");
		document.setField("item_price", 199);
		document.setField("item_image", "哇咔咔");
		document.setField("item_category_name", "哇咔咔");
		// 把文档对象写入索引库
		solrServer.add(document);
		// 提交
		solrServer.commit();

	}
	//读取文件
	@Test
	public void getSolrJTest()throws Exception{
		//第一步 : 把solrj相关的jar包添加到工程中
		//第二步: 创建一个SolrServer对象 
		//参数是zooleeper的地址列表 使用逗号分隔
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
		//第三步: 需要设置DefaultCOllection属性
		solrServer.setDefaultCollection("collection2");
		//第四步 : 创建一个solrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		//第五步: 像文档对象中添加域
		document.addField("id", "001");
	document.addField("item_title", "牛天下");
		document.addField("item_price", 199);
		////第六步 : 把文档对象写入所有库
		solrServer.add(document);
		//第七步 : 提交;
		solrServer.commit();
	}
	
	@Test
	public void testFindCloudSolr()throws Exception{
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
		solrServer.setDefaultCollection("collection2");
		SolrQuery query = new SolrQuery();
		query.setQuery("牛天下");
		query.set("df", "item_title");
	    QueryResponse response = solrServer.query(query);
	    System.out.println(response);
	}
	
}
