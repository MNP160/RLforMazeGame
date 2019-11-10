import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

private static QAgent qAgent;
private static SarsaAgent sarsa;

    public static void main (String[] args){
       //


        JFrame frame = new JFrame();
        frame.setTitle("Main Menu");
        frame.setSize(250,300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton mazeButton = new JButton("Enter Maze");
        //mazeButton.setBounds(100,100,100,50);
        mazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Maze();
                frame.setVisible(false);
            }
        });


        JButton qButton= new JButton("Run Q-Agent");
        //qButton.setBounds(300,300,200,50);
        qButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
             /* qAgent=new QAgent();
               qAgent.prepareRequiredData();
               qAgent.findQ();
               qAgent.printQMatrix();
               qAgent.printPolicy();*/
              sarsa=new SarsaAgent();
              sarsa.prepareRequiredData();
              int counter=0;
              for(int i=0; i<10000; i++) {

                  sarsa.runSARSA();
                  counter++;
                  System.out.println("Counter:" + counter);
              }
              sarsa.printQMatrix();
               frame.dispose();
           }
       });
        JLabel options= new JLabel("Choose what you want to do: ");
         JPanel jp = new JPanel();

         jp.setLayout(new FlowLayout());
         jp.add(options);
         jp.add(mazeButton);
         jp.add(qButton);
         frame.add(jp);

        frame.setVisible(true);

    }





}
