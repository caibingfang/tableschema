package com.tool.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tool.common.DataBaseElement;
import com.tool.jdbc.DataUtil;
import com.tool.jdbc.Schema;
import com.tool.jdbc.SchemaTable;
import com.tool.word.WordUtil;

public class ExportDataUI {
	
	private static JTextField ipText;
	
	private static JTextField portText;
	
	private static JTextField userText;
	
	private static JTextField passwordText;
	
	private static JTextField schemaText;
	
//	private static JTable table;
//	
	private static JTextArea tableNamesArea;
	
	
	public static void main(String[] args) {    
        // 创建 JFrame 实例
        JFrame frame = new JFrame("导出数据库表结构工具");
        // Setting the width and height of frame
        frame.setSize(450, 600);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();    
        // 添加面板
        frame.add(panel);
        /* 
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
    	
    	Font font = new Font("宋体",Font.BOLD,20);
        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel ipLabel = new JLabel("数据库IP*:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        ipLabel.setBounds(10,20,80,45);
        panel.add(ipLabel);

        /* 
         * 创建文本域用于用户输入
         */
        ipText = new JTextField(20);
        ipText.setBounds(100,20,265,45);
        ipText.setFont(font);
        panel.add(ipText);
        
        
        JLabel portLabel = new JLabel("数据库端口*:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        portLabel.setBounds(10,70,80,45);
        panel.add(portLabel);

        /* 
         * 创建文本域用于用户输入
         */
        portText = new JTextField(20);
        portText.setBounds(100,70,265,45);
        portText.setFont(font);
        panel.add(portText);
        
        JLabel userLabel = new JLabel("用户名*:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,120,80,45);
        panel.add(userLabel);

        /* 
         * 创建文本域用于用户输入
         */
        userText = new JTextField(20);
        userText.setBounds(100,120,265,45);
        userText.setFont(font);
        panel.add(userText);
        

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("密码*:");
        passwordLabel.setBounds(10,170,80,45);
        panel.add(passwordLabel);

        /* 
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        passwordText = new JTextField(20);
        passwordText.setBounds(100,170,265,45);
        passwordText.setFont(font);
        panel.add(passwordText);
        
        
        JLabel schemaLabel = new JLabel("数据库实例*:");
        schemaLabel.setBounds(10,220,80,45);
        panel.add(schemaLabel);

        /* 
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        schemaText = new JTextField(20);
        schemaText.setBounds(100,220,265,45);
        schemaText.setFont(font);
        panel.add(schemaText);
        
        JLabel tableNameLable = new JLabel("数据库表名:");
        tableNameLable.setBounds(10,270,80,45);
        panel.add(tableNameLable);

        /* 
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        
        
        tableNamesArea = new JTextArea();
        tableNamesArea.setBounds(100,270,265,90);
        tableNamesArea.setAutoscrolls(true);
        tableNamesArea.setLineWrap(true);
        tableNamesArea.setWrapStyleWord(true);
        tableNamesArea.setFont(font);
        
        panel.add(tableNamesArea);
        
        
        
        

        // 创建登录按钮
        JButton loginButton = new JButton("导出");
        loginButton.setBounds(100, 400, 80, 45);
        panel.add(loginButton);
        
        JLabel info = new JLabel("<html>说明:*代表必填，如表名为空则导出实例中全部表结构，多表可使用英文逗号隔开。</html>");
        info.setBounds(20,450,375,90);
        info.setForeground(Color.red);
        panel.add(info);
        
        
        JLabel author = new JLabel("<html>如有疑问 请联系: caibingfang@bubi.cn</html>");
        author.setBounds(160,480,375,90);
        author.setForeground(Color.red);
        panel.add(author);
        
        
        
        loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				loginButton.setEnabled(false);
				
				String ip = ipText.getText().trim();
				String port = portText.getText().trim();
				
				String user = userText.getText().trim();
				String password = passwordText.getText().trim();
				
				String schema = schemaText.getText().trim();
				String tables = tableNamesArea.getText().trim();
				
				if(ip.isEmpty()||port.isEmpty()||user.isEmpty()||password.isEmpty()||schema.isEmpty()) {
					JOptionPane.showMessageDialog(null, "必填项不能为空", "提示", JOptionPane.ERROR_MESSAGE);
					loginButton.setEnabled(true);
					return;
				}
				
				DataBaseElement dataBase = new DataBaseElement();
				dataBase.setIp(ip).setPort(port).setUsername(user).setPassword(password).setDb(schema);
				
				Map<String,String> map = null;
				Set<String> set = null;
				try {
					map = DataUtil.selectTable(dataBase);
					set = map.keySet();
				} catch (Exception ept) {
					JOptionPane.showMessageDialog(null, "数据库操作失败"+ept.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
					loginButton.setEnabled(true);
					return;
				}
				
				if(!tables.isEmpty()) {
					String[] split = tables.split(",");
					if(split.length!=0 ) {
						set = new LinkedHashSet<>(Arrays.asList(split));
					}
				}
				
				
				List<SchemaTable> schemaTables = new ArrayList<>(set.size());
				SchemaTable schemaTable;
				for(String str:set) {
					schemaTable = new SchemaTable();
					
					List<Schema> schemas = null;
					
					try {
						schemas = DataUtil.select(dataBase,str);
					} catch (Exception ept) {
						JOptionPane.showMessageDialog(null, "数据库操作失败"+ept.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
						loginButton.setEnabled(true);
						return;
					}
					
					schemaTable.setTableName(str);
					schemaTable.setTableComment(map.get(str));
					schemaTable.setList(schemas);
					schemaTables.add(schemaTable);
				}
				JFileChooser fileChooser = new JFileChooser();
				
				String fileName = schema+".docx";
				fileChooser.setSelectedFile(new File(fileName));
				int result = fileChooser.showSaveDialog(panel);
				
				String file = null;
				
				if(result == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile().getAbsolutePath();
				}
				
				WordUtil.work(schemaTables,file);
				JOptionPane.showMessageDialog(null, "操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				loginButton.setEnabled(true);
			}
        	
        });
        
        
    }
	

}
