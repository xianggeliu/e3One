package cn.e3mall.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;

/**
 * 查询索引库dao
 * 
 * @author 祥子
 *
 */
@Repository
public class SearchDaoImpl implements SearchDao {
	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		// 执行查询
		QueryResponse response = solrServer.query(query);
		// 取查询结果
		SolrDocumentList results = response.getResults();
		// 取总记录数
		long found = results.getNumFound();
		// 创建一个list
		List<SearchItem> searchItems = new ArrayList<>();
		// 取商品列表
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument document : results) {
			SearchItem searchItem = new SearchItem();
			searchItem.setId((String) document.get("id"));
			searchItem.setPrice((long) document.get("item_price"));
			searchItem.setSell_point((String) document.get("item_sell_point"));
			searchItem.setImage((String) document.get("item_image"));
			searchItem.setCategory_name((String) document.get("item_category_name"));
			List<String> list = highlighting.get(document.get("id")).get("item_title");
			String itemTitle = "";
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) document.get("item_title");
			}
			searchItem.setTitle(itemTitle);
			searchItems.add(searchItem);
		}
		// 封装到serchResult对象中
		SearchResult searchResult = new SearchResult();
		searchResult.setRecourdCount((int) found);
		searchResult.setItemList(searchItems);
		return searchResult;
	}

}
