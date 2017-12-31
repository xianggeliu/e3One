package cn.e3mall.search.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.search.pojo.SearchResult;

public interface SearchService {

	E3Result importItemList() throws Exception;
	SearchResult search(String keyWord,int page ,int rows) throws Exception;
	E3Result addDocument(long itemId) throws Exception;
}
