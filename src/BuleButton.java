

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class BuleButton extends JButton{
	public BuleButton(String title) {
		setText(title);
		setFont(new Font("微软雅黑", Font.PLAIN, 18));
		setBackground(Color.WHITE);
		setFocusPainted(false);//去掉文字边框
		setBorder(BorderFactory.createLineBorder(MyColor.borderColor,2));
	}
	
	public BuleButton() {
		setFont(new Font("微软雅黑", Font.PLAIN, 18));
		setBackground(Color.WHITE);
		setFocusPainted(false);//去掉文字边框
		setBorder(BorderFactory.createLineBorder(MyColor.borderColor,1));
		setForeground(MyColor.borderColor);
	}
	
}