import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class NotePad extends JPanel implements ActionListener{
	JTextArea text;
	JButton saveLog,deleteLog;
	Hashtable table;
	JLabel info;
	int year,month,day;
	File file;
	CalendarPad calendar;
	
	public NotePad(CalendarPad calendar){
		this.calendar=calendar;
		year=calendar.getYear();
		month=calendar.getMonth();
		day=calendar.getDay();
		table=calendar.getHashtable();
		file=calendar.getFile();
		info=new JLabel(" "+" 年"+month+" 月"+day+" 日",JLabel.CENTER);
		info.setFont(new Font(" TimesRoman",Font.BOLD,16));
		info.setForeground(Color.blue);
		saveLog=new JButton("保存日志");
		deleteLog=new JButton("删除日志");
		saveLog.addActionListener(this);
		deleteLog.addActionListener(this);
		setLayout(new BorderLayout());
		JPanel pSouth=new JPanel();
		add(info,BorderLayout.NORTH);
		pSouth.add(saveLog);
		pSouth.add(deleteLog);
		add(pSouth,BorderLayout.SOUTH);
		add(new JScrollPane(text),BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==saveLog){
			saveLog(year,month,day);
		}else if(e.getSource()==deleteLog){
			deleteLog(year,month,day);
		}
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public void setInfo(int year,int month,int day){
		info.setText(" "+year+" 年"+month+" 月"+day+" 日" );
	}
	public void setTextField(String s){
		text.setText(s);
	}
	public void getLogContent(int year,int month,int day){
		String key=" "+year+" "+month+" "+day;
		try{
			FileInputStream input1=new FileInputStream(file);
			ObjectInputStream input2=new ObjectInputStream(input1);
			table=(Hashtable)input2.readObject();
			input1.close();
			input2.close();
		}catch(Exception ee){}
		if(table.containsKey(key)){
			String m=" "+year+" 年"+month+" 月"+day+" 日";
			int ok=JOptionPane.showConfirmDialog(this, m,"询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(ok==JOptionPane.YES_NO_OPTION){
				text.setText((String)table.get(key));
			}else{
				text.setText(" ");
			}
		}else{
			text.setText(" 无日志记录");
		}
	}
	
	public void saveLog(int year,int month,int day){
		String logContent=text.getText();
		String key=" "+year+" "+month+" "+day;
		String m=" "+year+" 年"+month+" 月"+day+" 日，保存日志吗？";
		int ok=JOptionPane.showConfirmDialog(this, m," 询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(ok==JOptionPane.YES_NO_OPTION){
			try{
				FileInputStream input1=new FileInputStream(file);
				ObjectInputStream input2=new ObjectInputStream(input1);
				table=(Hashtable)input2.readObject();
				input1.close();
				input2.close();
				table.put(key, logContent);
				FileOutputStream output1=new FileOutputStream(file);;
				ObjectOutputStream output2=new ObjectOutputStream(output1);
				output2.writeObject(table);
				output1.close();
				output2.close();
			}catch(Exception ee){}
		}
	}
	public void deleteLog(int year,int month,int day){
		String key=" "+year+" "+month+" "+day;
		if(table.containsKey(key)){
			String m=" 删除"+year+" 年"+month+" 月"+day+" 日的日志吗？";
			int ok=JOptionPane.showConfirmDialog(this, m," 询问",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(ok==JOptionPane.QUESTION_MESSAGE){
				try{
					FileInputStream input1=new FileInputStream(file);
					ObjectInputStream input2=new ObjectInputStream(input1);
					table=(Hashtable)input2.readObject();
					input1.close();
					input2.close();
					table.remove(key);
					FileOutputStream output1=new FileOutputStream(file);
					ObjectOutputStream output2=new ObjectOutputStream(output1);
					output2.writeObject(table);
					output1.close();
					output2.close();
					text.setText(null);
				}catch(Exception ee){}
			}
		}else{
			String m=" "+year+" 年"+month+" 月"+day+" 日无日志记录";
			JOptionPane.showMessageDialog(this, m," 提示",JOptionPane.WARNING_MESSAGE);
		}
	}
}
