import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class WinBible3 extends JDialog {

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               WinBible3 dialog = new WinBible3();
               dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
               dialog.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   private JComboBox cbChapter;
   private JComboBox cbVerse;
   private JComboBox cbBook;
   private JScrollPane scrollPane;
   private JTextArea taContents;

   /**
    * Create the dialog.
    */
   public WinBible3() {
      setTitle("성경책");
      setBounds(100, 100, 783, 690);
      
      JPanel panel = new JPanel();
      getContentPane().add(panel, BorderLayout.NORTH);
      panel.setBounds(0, 0, 100, 100);
      JButton btnBibleInsert = new JButton("성경 입력");
      btnBibleInsert.setEnabled(false);
      btnBibleInsert.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
               //System.out.println("DB 연결 완료");               
               Statement statement=conn.createStatement();
               //=============================================               
               // bible.txt 한줄씩 읽어서 출력하시오.       
               try {
                  FileReader fr = new FileReader("./txt/bible.txt");
                  BufferedReader br = new BufferedReader(fr);
                  String line = "";   
                  String strBook="";
                  String strChapter="";
                  while((line=br.readLine()) != null) {
                     String abbr = line.substring(0,2);
                     int col_idx = line.indexOf(':');
                     if(isExist(abbr)) {
                        strBook = FullName(line.substring(0, 2));
                        strChapter = line.substring(2,col_idx);
                     }else {
                        strBook = FullName(line.substring(0, 1));   
                        strChapter = line.substring(1,col_idx);
                     }
                     int blank_idx = line.indexOf(' ');
                     String strVerse = line.substring(col_idx+1,blank_idx);
                     String strContent = line.substring(blank_idx+1);
                     
                     String sql = "insert into bibleTBL values(null,'";
                     sql = sql + strBook + "'," + strChapter + "," + strVerse + ",'";
                     sql = sql + strContent.replaceAll("'", "@") +"')";
                     //System.out.println(sql);
                     
                     if(statement.executeUpdate(sql)>0)
                        ;//System.out.println("성공");
                     else
                        System.out.println(sql);
                     
                  }
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }   
               
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC 드라이버 로드 에러");
            } catch (SQLException e1) {
               System.out.println("DB 연결 오류");
            }         
         }
      });
      panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
      panel.add(btnBibleInsert);
      
      cbBook = new JComboBox();
      cbBook.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
               Statement statement=conn.createStatement();
               //=============================================               
               String sql = "select distinct chapter from bibleTBL where book='";
               sql = sql + cbBook.getSelectedItem() + "'";
               
               cbChapter.removeAllItems();
               
               ResultSet rs = statement.executeQuery(sql);
               while(rs.next()) {
                  cbChapter.addItem(rs.getString(1));
               }
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC 드라이버 로드 에러");
            } catch (SQLException e1) {
               System.out.println("DB 연결 오류");
            }
         }
      });
      cbBook.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
               Statement statement=conn.createStatement();
               //=============================================               
               String sql = "select distinct chapter from bibleTBL where book='";
               sql = sql + cbBook.getSelectedItem() + "'";
               
               cbChapter.removeAllItems();
               
               ResultSet rs = statement.executeQuery(sql);
               while(rs.next()) {
                  cbChapter.addItem(rs.getString(1));
               }
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC 드라이버 로드 에러");
            } catch (SQLException e1) {
               System.out.println("DB 연결 오류");
            }
         }
      });
      cbBook.setModel(new DefaultComboBoxModel(new String[] {"창세기", "출애굽기", "레위기", "민수기", "신명기", "여호수아", "사사기", "룻기", "사무엘상", "사무엘하", "열왕기상", "열왕기하", "역대상", "역대하", "에스라", "느헤미야", "에스더", "욥기", "시편", "잠언", "전도서", "아가", "이사야", "예레미야", "예레미야애가", "에스겔", "다니엘", "호세아", "요엘", "아모스", "오바댜", "요나", "미가", "나훔", "하박국", "스바냐", "학개", "스가랴", "말라기", "마태복음", "마가복음", "누가복음", "요한복음", "사도행전", "로마서", "고린도전서", "고린도후서", "갈라디아서", "에베소서", "빌립보서", "골로새서", "데살로니가전서", "데살로니가후서", "디모데전서", "디모데후서", "디도서", "빌레몬서", "히브리서", "야고보서", "베드로전서", "베드로후서", "요한일서", "요한이서", "요한삼서", "유다서", "요한계시록"}));
      panel.add(cbBook);
      
      cbChapter = new JComboBox();
      cbChapter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
               Statement statement=conn.createStatement();
               //=============================================               
               String sql = "select distinct verse from bibleTBL where book='";
               sql = sql + cbBook.getSelectedItem() + "' and chapter=";
               sql = sql + cbChapter.getSelectedItem();
               
               cbVerse.removeAllItems();
               
               ResultSet rs = statement.executeQuery(sql);
               while(rs.next()) {
                  cbVerse.addItem(rs.getString(1));
               }
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC 드라이버 로드 에러");
            } catch (SQLException e1) {
               System.out.println("DB 연결 오류");
            }
         }
      });
      panel.add(cbChapter);
      
      cbVerse = new JComboBox();
      panel.add(cbVerse);
      
      JButton btnShow = new JButton("성경보기");
      btnShow.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showChapter();
         }
      });
      panel.add(btnShow);
      
      JPanel panel_1 = new JPanel();
      getContentPane().add(panel_1, BorderLayout.CENTER);
      panel_1.setLayout(new BorderLayout(0, 0));
      
      scrollPane = new JScrollPane();
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      panel_1.add(scrollPane, BorderLayout.CENTER);
      
      taContents = new JTextArea();
      taContents.setLineWrap(true);
      taContents.setFont(new Font("나눔고딕", Font.PLAIN, 20));
      scrollPane.setViewportView(taContents);
      
      cbBook.setSelectedIndex(0);
      showChapter();
   }

   protected void showChapter() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
         Statement statement=conn.createStatement();
         //=============================================               
         String sql = "select * from bibleTBL where book='";
         sql = sql + cbBook.getSelectedItem() + "' and chapter=";
         sql = sql + cbChapter.getSelectedItem();
         
         ResultSet rs = statement.executeQuery(sql);
         taContents.setText("");
         String temp = taContents.getText();
         while(rs.next()) {
            String strBook = rs.getString("book");
            String strChapter = rs.getString("Chapter");
            String strVerse = rs.getString("Verse");
            String strContents = rs.getString("Contents");                  
            
            temp = temp + strBook + " " + strChapter + ":" + strVerse;
            temp = temp + " " + strContents + "\n";
            
         }
         taContents.setText(temp);
         //==============================================
      } catch (ClassNotFoundException e1) {
         System.out.println("JDBC 드라이버 로드 에러");
      } catch (SQLException e1) {
         System.out.println("DB 연결 오류");
      }      
   }

   protected String FullName(String abbr) {
      String bibleAbbr[]= {"창","출","레","민","신","수","삿","룻","삼상","삼하","왕상","왕하","대상","대하","스","느","에","욥","시","잠","전","아","사","렘","애","겔","단","호","욜","암","옵","욘","미","나","합","습","학","슥","말","마","막","눅","요","행","롬","고전","고후","갈","엡","빌","골","살전","살후","딤전","딤후","딛","몬","히","약","벧전","벧후","요일","요이","요삼","유","계"};
      String bibleFull[]= {"창세기","출애굽기","레위기","민수기","신명기","여호수아","사사기","룻기","사무엘상","사무엘하","열왕기상","열왕기하","역대상","역대하","에스라","느헤미야","에스더","욥기","시편","잠언","전도서","아가","이사야","예레미야","예레미야애가","에스겔","다니엘","호세아","요엘","아모스","오바댜","요나","미가","나훔","하박국","스바냐","학개","스가랴","말라기","마태복음","마가복음","누가복음","요한복음","사도행전","로마서","고린도전서","고린도후서","갈라디아서","에베소서","빌립보서","골로새서","데살로니가전서","데살로니가후서","디모데전서","디모데후서","디도서","빌레몬서","히브리서","야고보서","베드로전서","베드로후서","요한일서","요한이서","요한삼서","유다서","요한계시록"};
      int idx = -1;
      for(int i=0; i<bibleAbbr.length; i++)
         if(bibleAbbr[i].equals(abbr)) {
            idx = i;
            break;
         }
      
      return bibleFull[idx];
   }

   protected boolean isExist(String abbr) {
      switch(abbr) {
      case "삼하":
      case "삼상":
      case "왕상":
      case "왕하":
      case "대상":
      case "대하":
      case "고전":
      case "고후":
      case "살전":
      case "살후":
      case "딤전":
      case "딤후":
      case "벧전":
      case "벧후":
      case "요일":
      case "요이":
      case "요삼":
         return true;
      }
      return false;
   }

}