package app2048;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;

import java.util.Random;
public class App2048 extends JFrame implements KeyListener,ActionListener{
    
    
    Gfield testlabel;
    Gfield[][] gamefield;
    
    JPanel MainPanel;
    JPanel GamePanel;
    JPanel ControlPanel;
    JPanel ResetPanel;
    
    
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
            
        });
 
    }
    public App2048(String name) {
        super(name);
    }
    

    
    private static class Gfield extends JLabel {
        public Gfield(String text) {
            Border border1 = LineBorder.createBlackLineBorder();
            this.setText(text);
            this.setHorizontalAlignment(CENTER);
            this.setBorder(border1);
        }
        private int CastInt() {
            if(this.getText()!="")
                this.Ival = Integer.parseInt(this.getText());
            else this.Ival = 0;
            return this.Ival;
        }
        //public void setText(String text) {
         //   this.setText(text);
            //this.ICol= bleach(Color.BLUE, Ival);
            //this.setForeground(this.ICol);
        //}
        int pozx;
        int pozy;
        int Ival;
    }
    private static class Gbutton extends JButton {
        public Gbutton(String text,App2048 frame) {
            this.setText(text);
            this.addActionListener(frame);
        }
    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        App2048 frame = new App2048("app2048");       
        frame.setLocationRelativeTo(null);
        
        frame.MainPanel = new JPanel();
        frame.GamePanel = new JPanel();
        frame.ControlPanel = new JPanel();
        frame.ResetPanel = new JPanel();
        
        frame.getContentPane().add(frame.MainPanel);
        frame.MainPanel.setLayout(new GridLayout(3,1));
        frame.MainPanel.add(frame.GamePanel);
        frame.MainPanel.add(frame.ControlPanel);
        frame.MainPanel.add(frame.ResetPanel);
        frame.GamePanel.setLayout(new GridLayout(4,4));
        frame.ControlPanel.setLayout(new GridLayout(3,3));
        frame.ResetPanel.setLayout(new GridLayout(1,1));
        
        frame.gamefield = new Gfield[4][4];
        for(int i=0;i<4;i++) 
            for(int j=0;j<4;j++){
            frame.gamefield[i][j] = new Gfield("");
            frame.GamePanel.add(frame.gamefield[i][j]); 
        }
       
        
        Gbutton buttonReset = new Gbutton("Reset",frame);
        frame.ResetPanel.add(buttonReset);
        
        Gbutton buttonP5 = new Gbutton("",frame);
        Gbutton buttonP4 = new Gbutton("",frame);
        Gbutton buttonP3 = new Gbutton("Rand",frame);
        Gbutton buttonP2 = new Gbutton("",frame);
        Gbutton buttonP1 = new Gbutton("",frame);
        Gbutton buttonU = new Gbutton("Up",frame);
        Gbutton buttonD = new Gbutton("Down",frame);
        Gbutton buttonR = new Gbutton("Right",frame);
        Gbutton buttonL = new Gbutton("Left",frame);
        frame.ControlPanel.add(buttonP1);
        frame.ControlPanel.add(buttonU);
        frame.ControlPanel.add(buttonP2);
        frame.ControlPanel.add(buttonL);
        frame.ControlPanel.add(buttonP3);
        frame.ControlPanel.add(buttonR);
        frame.ControlPanel.add(buttonP4);
        frame.ControlPanel.add(buttonD);
        frame.ControlPanel.add(buttonP5);
         
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    private boolean ChceckEmpty(){
        boolean foundEmpty = false;
        for(int i=0;i<4;i++) 
            for(int j=0;j<4;j++){
                if(gamefield[i][j].getText()=="") {foundEmpty = true; break;}
            //m++; 
        }
        return foundEmpty;
    
    }
    public static Color bleachBlue(int amount)
    {
      Color NewColor = new Color(1, 1, 255);  
      if(amount <64) NewColor = new Color(1, 1, 255-amount*7);
      if(amount >=64) NewColor = new Color(1, 255-(amount/64)*7, 1);
      
      return NewColor;
    }
    private void SetColorFields(){
        for(int i=0;i<4;i++) 
            for(int j=0;j<4;j++)
                if(gamefield[i][j].CastInt()!=0)
                    gamefield[i][j].setForeground(bleachBlue(gamefield[i][j].CastInt()));
    }
    private void ResetFields(){
        for(int i=0;i<4;i++) 
            for(int j=0;j<4;j++){
                gamefield[i][j].setText("");
                gamefield[i][j].setForeground(Color.BLACK);
            } 
        FillRandField();
    }
    private void FillRandField(){
       Random rand = new Random();
       int n = rand.nextInt(16);
       int xPos = n/4;
       int yPos = n%4;
           if(ChceckEmpty()){
            while (gamefield[xPos][yPos].getText() != ""){  
            n = rand.nextInt(16);
            xPos = n/4;
            yPos = n%4;
            }   
            gamefield[xPos][yPos].setText("1");    
            gamefield[xPos][yPos].setForeground(Color.red);
           }
           
    }
    private void UpAndDown(String dir){
        int[] tmp_arr = new int[4]; 
        int[] arr_cnt = new int[4];
        int j,k;
        System.out.println("---------");
        if(dir=="Down") for(int a=0;a<4;a++)arr_cnt[a]=3;
        if(dir=="Up") for(int a=0;a<4;a++)arr_cnt[a]=0;
        for(int i=0;i<4;i++) {
            for(int a=0;a<4;a++)tmp_arr[a]=0;
            if(dir=="Down")j=3; else j=0;
            while((dir=="Down"&&j>0)||(dir=="Up"&&j<3)){
                if(dir=="Down")k=j-1; else k=j+1;
                while((dir=="Down"&&k>=0)||(dir=="Up"&&k<=3)){
                    if(gamefield[j][i].CastInt()==gamefield[k][i].CastInt()||gamefield[k][i].CastInt()==0) {
                        if(gamefield[j][i].CastInt()==gamefield[k][i].CastInt()){
                            tmp_arr[j]= gamefield[j][i].CastInt()+gamefield[k][i].CastInt();
                            gamefield[k][i].setText("");
                            break;
                        }
                        else tmp_arr[j]= gamefield[j][i].CastInt();
                    }
                    else {
                        tmp_arr[j] = gamefield[j][i].CastInt();
                        break;
                    }  
                    if(dir=="Down")k--; else k++;                    
                }
                if(dir=="Down")j--; else j++;                
            }
            System.out.println(""+tmp_arr[0]+tmp_arr[1]+tmp_arr[2]+tmp_arr[3]);
            
            if(dir=="Down")
                tmp_arr[0] = gamefield[0][i].CastInt();
            else
                tmp_arr[3] = gamefield[3][i].CastInt();
            
            if(dir=="Down")j=3; else j=0;
            while((dir=="Down"&&j>=0)||(dir=="Up"&&j<=3)){
                while(arr_cnt[i]>=0&&arr_cnt[i]<=3){
                    if(tmp_arr[arr_cnt[i]]!=0) break;
                    else if(dir=="Down")arr_cnt[i]--; else arr_cnt[i]++;
                }
                if(arr_cnt[i]>=0&&arr_cnt[i]<=3) {
                    gamefield[j][i].setText(""+tmp_arr[arr_cnt[i]]);
                    if(dir=="Down")arr_cnt[i]--; else arr_cnt[i]++;
                }
                else gamefield[j][i].setText("");
                if(dir=="Down")j--; else j++;                 
            }
        }
    }
    private void ToTheSides(String dir){
        int[] tmp_arr = new int[4]; 
        int[] arr_cnt = new int[4];
        int j,k;
        System.out.println("---------");
        if(dir=="Right") for(int a=0;a<4;a++)arr_cnt[a]=3;
        if(dir=="Left") for(int a=0;a<4;a++)arr_cnt[a]=0;
        for(int i=0;i<4;i++) {
            for(int a=0;a<4;a++)tmp_arr[a]=0;
            if(dir=="Right")j=3; else j=0;
            while((dir=="Right"&&j>0)||(dir=="Left"&&j<3)){
                if(dir=="Right")k=j-1; else k=j+1;
                while((dir=="Right"&&k>=0)||(dir=="Left"&&k<=3)){
                    if(gamefield[i][j].CastInt()==gamefield[i][k].CastInt()||gamefield[i][k].CastInt()==0) {
                        if(gamefield[i][j].CastInt()==gamefield[i][k].CastInt()){
                            tmp_arr[j]= gamefield[i][j].CastInt()+gamefield[i][k].CastInt();
                            gamefield[i][k].setText("");
                            break;
                        }
                        else tmp_arr[j]= gamefield[i][j].CastInt();
                    }
                    else {
                        tmp_arr[j] = gamefield[i][j].CastInt();
                        break;
                    }  
                    if(dir=="Right")k--; else k++;                    
                }
                if(dir=="Right")j--; else j++;                
            }
            System.out.println(""+tmp_arr[0]+tmp_arr[1]+tmp_arr[2]+tmp_arr[3]);
            
            if(dir=="Right")
                tmp_arr[0] = gamefield[i][0].CastInt();
            else
                tmp_arr[3] = gamefield[i][3].CastInt();
            
            if(dir=="Right")j=3; else j=0;
            while((dir=="Right"&&j>=0)||(dir=="Left"&&j<=3)){
                while(arr_cnt[i]>=0&&arr_cnt[i]<=3){
                    if(tmp_arr[arr_cnt[i]]!=0) break;
                    else if(dir=="Right")arr_cnt[i]--; else arr_cnt[i]++;
                }
                if(arr_cnt[i]>=0&&arr_cnt[i]<=3) {
                    gamefield[i][j].setText(""+tmp_arr[arr_cnt[i]]);
                    if(dir=="Right")arr_cnt[i]--; else arr_cnt[i]++;
                }
                else gamefield[i][j].setText("");
                if(dir=="Right")j--; else j++;                 
            }
        }
    }
    public void keyTyped(KeyEvent e) { System.out.print("DUPA");   }
    public void keyPressed(KeyEvent e) {    }
    public void keyReleased(KeyEvent e) {    }
    public void actionPerformed(ActionEvent e) {
        //Clear the text components.
        if(e.getActionCommand()=="Reset") ResetFields();
        if(e.getActionCommand()=="Left" ||
          e.getActionCommand()=="Right" ||
          e.getActionCommand()=="Up" ||
          e.getActionCommand()=="Down"||
          e.getActionCommand()=="Rand"){
            if(e.getActionCommand()=="Left" || e.getActionCommand()=="Right"){ 
                ToTheSides(e.getActionCommand());
                SetColorFields();
                FillRandField();
            }
            if(e.getActionCommand()=="Up" || e.getActionCommand()=="Down"){ 
                UpAndDown(e.getActionCommand());
                SetColorFields();
                FillRandField();
            }
            if(e.getActionCommand()=="Rand"){
                SetColorFields();
                FillRandField();
            }
          }
        } ; 
        
    }


