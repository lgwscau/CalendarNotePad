import java.util.Calendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Hashtable;

public class CalendarPad extends JFrame implements MouseListener{
	int year,month,day;
	Hashtable hashtable;
	File file;
	JTextField howDay[];
	JLable title;
	Calendar calendar;
	int dayOfWeek;
	NotePad notepad=null;
	Month changeMonth;
	Year changeYear;
	String week[ ] ={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	JPanel leftPanel,rightPanel;
	public CalendarPad(int year,int month,int day){
		leftPanel=new JPanel();
		JPanel leftCenter=new JPanel();
		JPanel leftNorth=new JPanel();
		leftCenter.setLayout(new GridLayout(7,7));
		rightPanel=new JPanel();
		this.year=year;
		this.month=month;
		this.day=day;
		changeYear=new Year(this);
		changeYear.setYear(year);
		changeMonth=new Month(this);
		changeMonth.setMonth(month);
		
		title=new JLabel[7];
		showDay=new JTextField[42];
		for(int j=0;j<7;j++){
			title[j]=new JLabel();
			title[j].setText(week[j]);
			title[j].setBorder(BorderFactory.createRaisedBevelBorder());
		}
		title[0].setForeground(Color.red);
		title[6].setForeground(Color.BLUE);
		
		for(int i=0;i<42;i++){
			showDay[i]=new JTextField();
			showDay[i].addMouseListener(this);
			showDay[i].setEditable(false);
			leftCenter.add(showDay[i]);
		}
		
		calendar=Calendar.getInstance();
		Box box=Box.createHorizontalBox();
		
		box.add(changeYear);
		box.add(changeMonth);
		leftNorth.add(box);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftNorth,BorderLayout.NORTH);
		leftPanel.add(leftCenter,BorderLayout.CENTER);
		leftPanel.add(new Label("请在年份输入框内输入所查年份（负数表示公元前），并回车确认"),BorderLayout.SOUTH);
		leftPanel.validate();
		Container con=getContentPane();
		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightPanel);
		con.add(split,BorderLayout.CENTER);
		con.validate();
		
		hashtable=new Hashtable();
		file=new File("CalendarNotePad.txt");
		if(!file.exists()){
			try{
				FileOutputStream output=new FileOutputStream(file);
				ObjectOutputStream o_output=new ObjectOutputStream(output);
				o_output.writeObject(hashtable);
				o_output.close();
				output.close();
			}catch(IOException ee){}
		}
		
		notepad=new NotePad(this);
		rightPanel.add(notepad);
		
		setCalendarPad(year,month);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(windowEvent e){
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.setBounds(100, 50, 524, 285);
		this.validate();
	}
	
	public void setCalendarPad(int year,int month){
		calendar.set(year, month-1,1);
		dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK-1);
		if(month==1||month==2||month==3||month==5||month==7||month==8
				||month==10||month==12){
			orderNumber(dayOfWeek,31);
		}else if(month==4||month==6||month==9||month==11){
			orderNumber(dayOfWeek,30);
		}else if(month==2){
			if((year%4==0&&year%100!=0)||(year%400==0)){
				orderNumber(dayOfWeek,29);
			}else{
				orderNumber(dayOfWeek,28);
			}
		}
	}
	public void orderNumber(int dayOfWeek,int amountOfMonth){
		for(int i=dayOfWeek,n=1;i<dayOfWeek+amountOfMonth;i++){
			showDay[i].setText(" "+n);
			int(n==day){
				showDay[i].setForeground(Color,green);
				showDay[i].setFont(new Font(" TimesRoman",Font.BOLD,20));
			}else{
				showDay[i].setFont(new Font(" timesRoman",Font.BOLD,12));
				showDay[i].setForeground(Color.black);
			}
			if(i%7==6){
				showDay[i].setForeground(Color.blue);
			}
			if(i%7==0){
				showDay[i].setForeground(Color.red);
			}
			n++;
		}
		for(int i=0;i<dayOfWeek;i++){
			showDay[i].setText(" ");
		}
		for(int i=dayOfWeek+amountOfMonth;i<42;i++){
			showDay[i].setText(" ");
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
	
	public Hashtable getHashtable(){
		return this.hashtable;
	}
	
	public File getFile(){
		return this.file;
	}
	
	public void mousePressed(MouseEvent e){
		JTextField sourse=(JTextField)e.getSource();
		try{
			day=Integer.parseInt(sourse.getText());
			notepad.setDay(day);
			notepad.setInfo(year,month,day);
			notepad.setTextField(null);
			notepad.getLogContent(year,month,day);
		}catch(Exception ee){}
	}
	public void mouseClicked(MouseEvent e){
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public static void main(String args[]){
		Calendar calendar=Calendar.getInstance();
		int y=calendar.get(Calendar.YEAR);
		int m=calendar.get(Calendar.MONTH)-1;
		int d=calendar.get(Calendar.DAY_OF_MONTH);
		new CalendarPad(y,m,d);
	}
}
