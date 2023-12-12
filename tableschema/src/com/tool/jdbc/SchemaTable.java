package com.tool.jdbc;

import java.util.List;

public class SchemaTable {
	
	private String tableName;
	
	private String tableComment;
	
	private List<Schema> list;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public List<Schema> getList() {
		return list;
	}

	public void setList(List<Schema> list) {
		this.list = list;
	}
	
	

}
