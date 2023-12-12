package com.tool.word;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tool.common.DataBaseElement;
import com.tool.common.Property;
import com.tool.jdbc.DataUtil;
import com.tool.jdbc.Schema;
import com.tool.jdbc.SchemaTable;

public class MainSingleSchema {
	
	public static void main(String[] args) throws Exception {
		
		
		DataBaseElement dataBase = new DataBaseElement();
		dataBase.setIp(Property.ip).setPort(Property.port).setUsername(Property.username).setPassword(Property.password).setDb(Property.db);
		
		Map<String,String> map = DataUtil.selectTable(dataBase);
		List<SchemaTable> schemaTables = new ArrayList(map.size());
		SchemaTable schemaTable;
		for(String str:map.keySet()) {
			schemaTable = new SchemaTable();
			List<Schema> schemas = DataUtil.select(dataBase,str);
			schemaTable.setTableName(str);
			schemaTable.setList(schemas);
			schemaTables.add(schemaTable);
		}
		
		WordUtil.work(schemaTables,Property.file);
		System.out.println("success");
	}

}
