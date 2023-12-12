package com.tool.word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import com.tool.common.Property;
import com.tool.jdbc.Schema;
import com.tool.jdbc.SchemaTable;

public class WordUtil {


	public static void work(List<SchemaTable> schemaTables,String path) {
		XWPFDocument doc = new XWPFDocument();
		
		for(SchemaTable st:schemaTables) {
			
			List<Schema> list = st.getList();
			String tableName = st.getTableName();
			
			String tableComment = st.getTableComment();
		
			int size = list.size();
			XWPFParagraph paragraphX = doc.createParagraph();
			
			XWPFRun run = paragraphX.createRun();
//			run.addCarriageReturn();
			run.setBold(true); //加粗
			run.setText(tableComment==null||tableComment.length()==0?tableName:tableComment);
			
			XWPFParagraph paragraph = doc.createParagraph();
			XWPFRun run1 = paragraph.createRun();
			run1.setText("表名："+tableName);
			
			XWPFTable table = paragraph.getDocument().createTable(size+1, 5);
			XWPFTableRow row0 = table.getRow(0);
			row0.setHeight(300);
			XWPFTableCell cell = null;
			cell = row0.getCell(0);
			setCellText(cell, "字段名", "FFFFFF", 1500);
			cell = row0.getCell(1);
			setCellText(cell, "字段类型", "FFFFFF", 1500);
			cell = row0.getCell(2);
			setCellText(cell, "字段长度", "FFFFFF", 1500);
			cell = row0.getCell(3);
			setCellText(cell, "是否可以为空", "FFFFFF", 1500);
			cell = row0.getCell(4);
			setCellText(cell, "描述", "FFFFFF", 1500);
			Schema schema = null;
			for(int i=1;i<=size;i++) {
				schema = list.get(i-1);
				XWPFTableRow row = table.getRow(i);
				row.setHeight(300);
				XWPFTableCell dataCell = null;
				dataCell = row.getCell(0);
				setCellText(dataCell, schema.getColumnName(), "FFFFFF", 1500);
				dataCell = row.getCell(1);
				setCellText(dataCell, schema.getDataType(), "FFFFFF", 1500);
				dataCell = row.getCell(2);
				String length = schema.getLength();
				if(!schema.getDataType().contains("char")){
					length = split(schema.getColumnType());
				}
				setCellText(dataCell, length, "FFFFFF", 1500);
				dataCell = row.getCell(3);
				setCellText(dataCell, schema.getNullable(), "FFFFFF", 1500);
				dataCell = row.getCell(4);
				setCellText(dataCell, schema.getColumnComment(), "FFFFFF", 1500);
			}
			doc.createParagraph().createRun();
		}
		
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(path));
			doc.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	private static void setCellText(XWPFTableCell cell, String text, String bgcolor, int width) {
		CTTc cttc = cell.getCTTc();
		CTTcPr cellPr = cttc.addNewTcPr();
		cellPr.addNewTcW().setW(BigInteger.valueOf(width));
		CTTcPr ctPr = cttc.addNewTcPr();
		CTShd ctshd = ctPr.addNewShd();
		ctshd.setFill(bgcolor);
		ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		cell.setText(text);
	}

	
	private static String split(String str) {
		if(!str.contains("(")) {
			return null;
		}
		int first = str.indexOf("(");
		int last = str.indexOf(")");
		return str.substring(first+1, last);
	}
}
