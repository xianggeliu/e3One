package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.search.pojo.SearchItem;

public interface SearchItemMapper {

	List<SearchItem> getItems();
	SearchItem getSearchItemById(long itemId);
}
