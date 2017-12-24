package cn.e3mall.common.pojo;

import java.io.Serializable;

/**
 * 用来存储查到分类的信息
 * 
 * @author 祥子
 *
 */
public class EasyUiTreeNode implements Serializable{
	private Long id;
	private String text;
	private String state;

	public EasyUiTreeNode() {
	}

	public EasyUiTreeNode(Long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
