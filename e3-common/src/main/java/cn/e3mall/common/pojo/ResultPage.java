package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 是一个分页的page类\
 *  提供get set 方法 
 *  提供有参构造方法 方便数据的接收
 * 
 * @author 祥子
 */
public class ResultPage implements Serializable{

	private Integer total;
	private List<?> rows;

	public ResultPage(Integer total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
