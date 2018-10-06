import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import dao.InputDao;
import dao.PinYin;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

public class InputJpanel extends JPanel {
	StringBuffer stringBuffer = new StringBuffer();//要显示在TextArea的内容
	StringBuffer stringSpell = new StringBuffer();//要进行去匹配的汉字拼音
	JTextPane textPanel;
	StyledDocument doc;//插入文字样式
	SimpleAttributeSet sattSet1;//属性集  不设置属性,空白的。
	SimpleAttributeSet sattSet;//属性集
	JFrame jFrame;
	JButton[] buttons = new JButton[7];
	boolean isSpelling;
	boolean isShowHanzi;
	boolean isUpperAndLower;
	List<String> data;
	JScrollPane scroll;
	JPanel jPanelHanzi;
	GridLayout gridLayout;
	JButton btnQ,btnW,btnE,btnR,btnT,btnY,btnU,btnI,btnO,btnP,btnA,
			btnS,btnD,btnF,btnG,btnH,btnJ,btnK,btnL,
			btnZ,btnX,btnC,btnV,btnB,btnN,btnM;
	/**
	 * Create the panel.
	 */
	public InputJpanel() {
		jFrame = new JFrame();
		jFrame.setSize(1172, 646);
		jFrame.getContentPane().add(this);
		jFrame.setResizable(false);	
		
		this.setLayout(null);
		this.setSize(1024, 768);

		data = new ArrayList<>();
		
		isSpelling = false;
		isShowHanzi=false;
		isUpperAndLower = false;
		jPanelHanzi = new JPanel();
		gridLayout = new GridLayout();
		jPanelHanzi.setLayout(null);
		
//		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");   更改整体组件风格
		scroll = new JScrollPane(jPanelHanzi);
		scroll.setEnabled(false);
		scroll.setBounds(10, 234, 1130, 355);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setVisible(false);
		scroll.getVerticalScrollBar().setUI(new MyMetalScrollBarUI());//更改滚动条
		scroll.setHorizontalScrollBar(null);//不显示下面的滚动条
		add(scroll);
		
		textPanel = new JTextPane();
		textPanel.setBounds(10, 62, 1014, 77);
		textPanel.setFont(new Font("宋体", Font.PLAIN, 20));
		//显示光标
		textPanel.getCaret().setVisible(true);
		textPanel.setEditable(false);// 设置不可编辑
		add(textPanel);
		doc = textPanel.getStyledDocument();
		sattSet1 = new SimpleAttributeSet();
		sattSet = new SimpleAttributeSet();
		StyleConstants.setBackground(sattSet, Color.GRAY);//设置背景色

		
		BuleButton btnNewButton = new BuleButton("X");
		btnNewButton.setBounds(1047, 62, 93, 77);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stringBuffer.delete(0, stringBuffer.length());
				stringSpell.delete(0, stringSpell.length());
				textPanel.setText("");
			}
		});
		add(btnNewButton);
		
		BuleButton btnNewButton_1 = new BuleButton("|<");
		btnNewButton_1.setBounds(10, 168, 93, 50);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(buttons[0].getText() !=null && !"".equals(buttons[0].getText())) {
					int position = data.indexOf(buttons[0].getText());//当前buttons[0]上面的汉字在data的里面的位置
					if(position !=0 ) {
						int temp = 0;
						for(int i=position-1 ; ;i-- ) {
							temp= i % 7;
							buttons[temp].setText(data.get(i));
							if(temp == 0) {
								break;
							}
						}
					}
				}
			}
		});
		add(btnNewButton_1);
		BuleButton button = new BuleButton(">|");
		button.setBounds(123, 168, 93, 50);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(buttons[6].getText() !=null &&  !"".equals(buttons[0].getText())) {
					int position = data.indexOf(buttons[6].getText());//当前buttons[6]上面的汉字在data的里面的位置
					if(data.size()>(position + 1)) {
						for(int i =0; i<buttons.length;i++) {
							buttons[i] .setText("");
						}
						int temp = 0; 
						for(int i =position+1 ;i<data.size();i++) {
							if(temp != 6) {
								temp = i%7 ;
								if( temp <7) {
									buttons[temp].setText(data.get(i));
								}
							}
						}
					}
				}
			}
		});
		add(button);
		
		buttons[0] = new BuleButton("");
		buttons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append(buttons[0].getText());
			}
		});
		buttons[0].setBounds(232, 168, 93, 50);
		add(buttons[0]);
		
		buttons[1] = new BuleButton("");
		buttons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append(buttons[1].getText());
			}
		});
		buttons[1].setBounds(352, 168, 93, 50);
		add(buttons[1]);
		
		buttons[2] = new BuleButton("");
		buttons[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append(buttons[2].getText());
			}
		});
		buttons[2].setBounds(472, 168, 93, 50);
		add(buttons[2]);
		
		buttons[3] = new BuleButton("");
		buttons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append(buttons[3].getText());
			}
		});
		buttons[3].setBounds(597, 168, 93, 50);
		add(buttons[3]);
		
		buttons[4] = new BuleButton("");
		buttons[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append(buttons[4].getText());
			}
		});
		buttons[4].setBounds(716, 168, 93, 50);
		add(buttons[4]);
		
		buttons[5] = new BuleButton("");
		buttons[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append(buttons[5].getText());
			}
		});
		buttons[5].setBounds(826, 168, 93, 50);
		add(buttons[5]);
		
		buttons[6] = new BuleButton("");
		buttons[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append(buttons[6].getText());
			}
		});
		buttons[6].setBounds(931, 168, 93, 50);
		add(buttons[6]);
		
		BuleButton button_7 = new BuleButton(">>>");
		button_7.setBounds(1047, 168, 93, 50);
		button_7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				initScroll(scroll);
				if (!isShowHanzi) {
					isShowHanzi =true;
					scroll.setVisible(true);
				}else {
					isShowHanzi = false;
					scroll.setVisible(false);
				}
			}
		});
		add(button_7);
		
		BuleButton button_8 = new BuleButton("0");
		button_8.setBounds(10, 243, 93, 50);
		button_8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_8.getText());
			}
		});
		add(button_8);
		
		BuleButton button_9 = new BuleButton("1");
		button_9.setBounds(123, 243, 93, 50);
		button_9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_9.getText());
			}
		});
		add(button_9);
		
		BuleButton button_10 = new BuleButton("2");
		button_10.setBounds(232, 243, 93, 50);
		button_10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_10.getText());
			}
		});
		add(button_10);
		
		BuleButton button_11 = new BuleButton("3");
		button_11.setBounds(352, 243, 93, 50);
		button_11.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_11.getText());
			}
		});
		add(button_11);
		
		BuleButton button_12 = new BuleButton("4");
		button_12.setBounds(472, 243, 93, 50);
		button_12.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_12.getText());
			}
		});
		add(button_12);
		
		BuleButton button_13 = new BuleButton("5");
		button_13.setBounds(597, 243, 93, 50);
		button_13.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_13.getText());
			}
		});
		add(button_13);
		
		BuleButton button_14 = new BuleButton("6");
		button_14.setBounds(716, 243, 93, 50);
		button_14.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_14.getText());
			}
		});
		add(button_14);
		
		BuleButton button_15 = new BuleButton("7");
		button_15.setBounds(826, 243, 93, 50);
		button_15.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_15.getText());
			}
		});
		add(button_15);
		
		BuleButton button_16 = new BuleButton("8");
		button_16.setBounds(931, 243, 93, 50);
		button_16.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_16.getText());
			}
		});
		add(button_16);
		
		 btnQ = new BuleButton("q");
		btnQ.setBounds(10, 320, 93, 50);
		btnQ.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnQ.getText());
			}
		});
		add(btnQ);
		
		 btnW = new BuleButton("w");
		btnW.setBounds(123, 320, 93, 50);
		btnW.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnW.getText());
			}
		});
		add(btnW);
		
		 btnE = new BuleButton("e");
		btnE.setBounds(232, 320, 93, 50);
		btnE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnE.getText());
			}
		});
		add(btnE);
		
		 btnR = new BuleButton("r");
		btnR.setBounds(352, 320, 93, 50);
		btnR.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnR.getText());
			}
		});
		add(btnR);
		
		 btnT = new BuleButton("t");
		btnT.setBounds(472, 320, 93, 50);
		btnT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnT.getText());
			}
		});
		add(btnT);
		
		 btnY = new BuleButton("y");
		btnY.setBounds(597, 320, 93, 50);
		btnY.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnY.getText());
			}
		});
		add(btnY);
		
		btnU = new BuleButton("u");
		btnU.setBounds(716, 320, 93, 50);
		btnU.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnU.getText());
			}
		});
		add(btnU);
		
		btnI = new BuleButton("i");
		btnI.setBounds(826, 320, 93, 50);
		btnI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnI.getText());
			}
		});
		add(btnI);
		
		 btnO = new BuleButton("o");
		btnO.setBounds(931, 320, 93, 50);
		btnO.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnO.getText());
			}
		});
		add(btnO);
		
		 btnA = new BuleButton("a");
		btnA.setBounds(71, 394, 93, 50);
		btnA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnA.getText());
			}
		});
		add(btnA);
		
		 btnS = new BuleButton("s");
		btnS.setBounds(189, 394, 93, 50);
		btnS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnS.getText());
			}
		});
		add(btnS);
		
		btnD = new BuleButton("d");
		btnD.setBounds(305, 394, 93, 50);
		btnD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnD.getText());
			}
		});
		add(btnD);
		
		btnF = new BuleButton("f");
		btnF.setBounds(426, 394, 93, 50);
		btnF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnF.getText());
			}
		});
		add(btnF);
		
		btnG = new BuleButton("g");
		btnG.setBounds(551, 394, 93, 50);
		btnG.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnG.getText());
			}
		});
		add(btnG);
		
		btnH = new BuleButton("h");
		btnH.setBounds(670, 394, 93, 50);
		btnH.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnH.getText());
			}
		});
		add(btnH);
		
		btnJ = new BuleButton("j");
		btnJ.setBounds(788, 394, 93, 50);
		btnJ.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnJ.getText());
			}
		});
		add(btnJ);
		
		btnK = new BuleButton("k");
		btnK.setBounds(908, 394, 93, 50);
		btnK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnK.getText());
			}
		});
		add(btnK);
		
		btnZ = new BuleButton("z");
		btnZ.setBounds(10, 462, 93, 50);
		btnZ.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnZ.getText());
			}
		});
		add(btnZ);
		
		btnX = new BuleButton("x");
		btnX.setBounds(123, 462, 93, 50);
		btnX.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnX.getText());
			}
		});
		add(btnX);
		
		btnC = new BuleButton("c");
		btnC.setBounds(232, 462, 93, 50);
		btnC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnC.getText());
			}
		});
		add(btnC);
		
		btnV = new BuleButton("v");
		btnV.setBounds(352, 462, 93, 50);
		btnV.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnV.getText());
			}
		});
		add(btnV);
		
		btnB = new BuleButton("b");
		btnB.setBounds(472, 462, 93, 50);
		btnB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnB.getText());
			}
		});
		add(btnB);
		
		btnN = new BuleButton("n");
		btnN.setBounds(597, 462, 93, 50);
		btnN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnN.getText());
			}
		});
		add(btnN);
		
		btnM = new BuleButton("m");
		btnM.setBounds(716, 462, 93, 50);
		btnM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnM.getText());
			}
		});
		add(btnM);
		
		BuleButton button_41 = new BuleButton(".");
		button_41.setBounds(826, 462, 93, 50);
		button_41.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_41.getText());
			}
		});
		add(button_41);
		
		BuleButton button_42 = new BuleButton("退一格");
		button_42.setBounds(931, 462, 93, 50);
		button_42.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stringSpell.length()>0) {
					int position = doc.getLength() - stringSpell.length(); //重新插入stringSpell的位置
					stringSpell.delete(stringSpell.length()-1, stringSpell.length()); //删除StringSpell的最后一个字符
					stringBuffer = new StringBuffer(stringBuffer.substring(0, position)); //stringBuffer截取取之前不用匹配汉字的字符串
					try {
					textPanel.setText("");	//将textPanel设为空
					doc.insertString(0, stringBuffer.toString(), sattSet1);//设置textPanel里面的不用匹配汉字的字符串
					doc.insertString(position, stringSpell.toString(), sattSet);//从position位置插入已经处理过的stringspell（需要匹配汉字的字符串）
					stringBuffer.delete(0, stringBuffer.length());
					stringBuffer.append(textPanel.getText());
					findData();
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}else {
					if (stringBuffer.length()>0) {
						stringBuffer.delete(stringBuffer.length()-1, stringBuffer.length());
					}
					try {
						textPanel.setText("");
						doc.insertString(0, stringBuffer.toString(), sattSet1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		add(button_42);
		
		BuleButton button_43 = new BuleButton("中/英");
		button_43.setBounds(10, 539, 93, 50);
		button_43.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isSpelling) {
					System.out.println("655英");
					textPanel.setText("");
					stringSpell.delete(0, stringSpell.length());
					try {
						doc.insertString(0, stringBuffer.toString(), sattSet1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					isSpelling = false;
				}else {
					System.out.println("665中");
					isSpelling = true;
				}
			}
		});
		add(button_43);
		
		BuleButton button_44 = new BuleButton("空格");
		button_44.setBounds(123, 539, 796, 50);
		button_44.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				append(" ");
			}
		});
		add(button_44);
		
		BuleButton button_45 = new BuleButton("关闭");
		button_45.setBounds(1047, 539, 93, 50);
		add(button_45);
		
		BuleButton button_46 = new BuleButton("保存");
		button_46.setBounds(929, 539, 93, 50);
		add(button_46);
		
		BuleButton button_1 = new BuleButton("9");
		button_1.setBounds(1047, 243, 93, 50);
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				append(button_1.getText());
			}
		});
		add(button_1);
		
		btnP = new BuleButton("p");
		btnP.setBounds(1047, 320, 93, 50);
		btnP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnP.getText());
			}
		});
		add(btnP);
		btnL = new BuleButton("l");
		btnL.setBounds(1011, 394, 93, 50);
		btnL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				append(btnL.getText());
			}
		});
		add(btnL);
		
		BuleButton button_4 = new BuleButton("大写");
		button_4.setBounds(1047, 462, 93, 50);
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isUpperAndLower) {
					isUpperAndLower = false;
					Upper();
				}else {
					isUpperAndLower = true;
					Lower();
				}
			}
		});
		add(button_4);
	}
	/**
	 * 
	 * */
	private void initScroll(JScrollPane scrollPane2) {
		//清空之前的Button
		jPanelHanzi.removeAll();
		//动态设置JButton
		float row = Float.valueOf(data.size()) /Float.valueOf(8);
		int rows = (int) Math.ceil(row);
		jPanelHanzi.setPreferredSize(new Dimension(1100,130*rows));
		List<JButton> jList = new ArrayList<>();
		for(int i= 0; i<rows; i++) {
			for(int j =0;j<8 ; j++) {
				int x = 10 ;
				int y = 10 ;
				if(i==0) {
					x = 0;
				}
				if( j ==0 ) {
					y = 0 ;
				}
				if (jList.size() == data.size()) {
					break;
				}
				BuleButton jButton= new BuleButton("");
				jButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						append(jButton.getText());
					}
				});
				jList.add(jButton);
				jButton.setBounds(y*j+j*121,x*i+i*121, 121, 121);
				jPanelHanzi.add(jButton);
			}
		}
		System.out.println("772data---initScroll"+data);
		for(int i = 0;i<data.size();i++) {
			jList.get(i).setText(data.get(i));
		}
	}
	/**
	 * 1.判断是否是在中文输入
	 * 2.判断是否是小写的字母，输入数字或者空格中断匹配汉字
	 * */
	public void append(String input) {
		//判断是否是26个小写字母，是就进行匹配，剩下其他的都不用匹配汉字，
		//根据输入内容，只有是小写字母才进行匹配
		if(isSpelling) { //判断是否是在匹配汉字
			System.out.println(input);
			String regex = "^[a-z]+$";
			if(input.matches(regex)) {//根据输入内容，只有是小写字母才进行匹配
				stringBuffer.append(input);//添加到stringBufer里面
				try {
					doc.insertString(doc.getLength(), input, sattSet);//显示新输入的字符
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				stringSpell.append(input);//添加到需要匹配的汉字里面
				findData();
			}else {
			//判断是否是汉字，是汉字的话，将汉字替换掉stringBuffer的stringSpell
			if(input!=null &&input.length()>0) {
			char c = input.charAt(0);
				if(0x4e00 <= c && 0x9FA5 >= c) {
					System.out.println("汉字哇 --801");
					stringBuffer.delete(stringBuffer.length()-stringSpell.length(), stringBuffer.length());
				}
					stringSpell.delete(0, stringSpell.length());
					stringBuffer.append(input);
					try {
					textPanel.setText("");
					doc.insertString(0, stringBuffer.toString(), sattSet1);
				    } catch (BadLocationException e) {
					e.printStackTrace();
					}
				}
			}
		}else {
			//英文状态下的输入处理
			stringBuffer.append(input);
			try {
				doc.insertString(doc.getLength(), input,sattSet1);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 开后台线程去查询
	 * 输入的字母长度在6个以下包含六个的，进行查询，超过六个不去查询(单个拼音最大是六个长度)
	 * */
	private void findData() {
		new SwingWorker<List<PinYin>, Integer>() {
			@Override
			protected List<PinYin> doInBackground() throws Exception {
				String input =	stringSpell.toString();
				List<PinYin> findData;
					if(input.length() <=6 ) {
						System.out.println("开始查询--836"+input);
						findData = InputDao.findData(input);//开始查询
					}else {
					System.out.println("超过六个，不进行查询--839");
					return null;
				}
				return findData;
			}
			protected void done() {
				try {
					List<PinYin> list = get();//从doInBackground获取 返回 数据
					if(list != null && list.size()>0) {
						initData(list);//将得到的数据放入data里面,供选择汉字使用
						System.out.println("done,进行显示处理--849");
						System.out.println("done--850"+list);
					}else {
						System.out.println("data 为空--852");
					}
					setMyButton(list);//设置汉字显示
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			private void initData(List<PinYin> list) {
				data.clear();
				for(PinYin p:list) {
					data.add(p.getHanzi());
				}
				System.out.println("data--汉字：-----866:"+data);
			}
			private void setMyButton(List<PinYin> list) {
				if(list !=null && list.size()>0) {
					for(int i = 0  ;i<list.size();i++) {
						if (i <= 6) {
							buttons[i].setText(list.get(i).getHanzi());
						}
					}
				}else {
					for(int i = 0;i<=6;i++) {
						buttons[i].setText("");
					}
				}
			}
		}.execute();
	}
	private void Lower() {
		 btnQ.setText("Q");
		 btnW.setText("W");
		 btnE.setText("E");
		 btnR.setText("R");
		 btnT.setText("T");
		 btnY.setText("Y");
		 btnU.setText("U");
		 btnI.setText("I");
		 btnO.setText("O");
		 btnP.setText("P");
		 btnA.setText("A");
		 btnS.setText("S");
		 btnD.setText("D");
		 btnF.setText("F");
		 btnG.setText("G");
		 btnH.setText("H");
		 btnJ.setText("J");
		 btnK.setText("K");
		 btnL.setText("L");
		 btnZ.setText("Z");
		 btnX.setText("X");
		 btnC.setText("C");
		 btnV.setText("V");
		 btnB.setText("B");
		 btnN.setText("N");
		 btnM.setText("M");
	}
	
	private void Upper() {
		btnQ.setText("q");
		btnW.setText("w");
		btnE.setText("e");
		btnR.setText("r");
		btnT.setText("t");
		btnY.setText("y");
		btnU.setText("u");
		btnI.setText("i");
		btnO.setText("o");
		btnP.setText("p");
		btnA.setText("a");
		btnS.setText("s");
		btnD.setText("d");
		btnF.setText("f");
		btnG.setText("g");
		btnH.setText("h");
		btnJ.setText("j");
		btnK.setText("k");
		btnL.setText("l");
		btnZ.setText("z");
		btnX.setText("x");
		btnC.setText("c");
		btnV.setText("v");
		btnB.setText("b");
		btnN.setText("n");
		btnM.setText("m");
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputJpanel jFJpanel = new InputJpanel();
					jFJpanel.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
		}
}
