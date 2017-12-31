package cn.e3mall.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.mapper.SearchItemMapper;
import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

/**
 * 索引库维护Service
 * 
 * @author 祥子
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchDao searchDao;
	

	@Override
	public E3Result importItemList() throws Exception {

		// 1）调用dao查询商品列表
		List<SearchItem> list = searchItemMapper.getItems();
		// 2）遍历商品列表，把商品信息写入索引库
		SolrInputDocument document = new SolrInputDocument();
		for (SearchItem item : list) {
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			solrServer.add(document);
		}
		solrServer.commit();
		// 3）返回导入成功
		return E3Result.ok();
	}
	

	@Override
	public SearchResult search(String keyWord, int page, int rows) throws Exception {
		// 拼装查询条件
		SolrQuery solrQuery = new SolrQuery();
		// 设置查询条件
		solrQuery.setQuery(keyWord);
		// 设置分页条件
		solrQuery.setStart(page -1);
		solrQuery.setRows(rows);
		solrQuery.set("df", "item_title");
		// 设置高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		// 调用dao 执行查询
		SearchResult search = searchDao.search(solrQuery);
		// 封装总页数
		int count = search.getRecourdCount();
		int totalpage = count / rows;
		if (totalpage % rows > 0)
			totalpage++;
		search.setTotalPages(totalpage);
		return search;
	}


	@Override
	public E3Result addDocument(long itemId) throws Exception {
		//根据id查找到商品信息
		SearchItem item = searchItemMapper.getSearchItemById(itemId);
		//创建document对象
		SolrInputDocument document = new  SolrInputDocument();
		//把对象写入到索引库
		document.setField("id", item.getId());
		document.setField("item_title", item.getTitle());
		document.setField("item_sell_point", item.getSell_point());
		document.setField("item_price", item.getPrice());
		document.setField("item_image", item.getImage());
		document.setField("item_category_name", item.getCategory_name());
		//向索引库添加文档
		solrServer.add(document);
		//提交事务 事务必须提交
		solrServer.commit();
		return E3Result.ok();
	}

}
