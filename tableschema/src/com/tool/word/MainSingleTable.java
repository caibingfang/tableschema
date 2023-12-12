package com.tool.word;

import java.util.ArrayList;
import java.util.List;

import com.tool.common.DataBaseElement;
import com.tool.common.Property;
import com.tool.jdbc.DataUtil;
import com.tool.jdbc.Schema;
import com.tool.jdbc.SchemaTable;

public class MainSingleTable {
	
	public static void main(String[] args) throws Exception {
		DataBaseElement dataBase = new DataBaseElement();
		dataBase.setIp(Property.ip).setPort(Property.port).setUsername(Property.username).setPassword(Property.password).setDb(Property.db);
		
		List<Schema> list = DataUtil.select(dataBase,Property.table);
		
		SchemaTable st = new SchemaTable();
		st.setList(list);
		st.setTableName(Property.table);
		
		List<SchemaTable> schemaTables = new ArrayList<>(1);
		schemaTables.add(st);
		
		WordUtil.work(schemaTables,Property.file);
		System.out.println("success");
	}

}
