import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board extends JPanel  implements ActionListener {

   private Timer timer;
   private Map map;
   private Hero[] heroes =new Hero[50];
   private String endMessage;
   private boolean ended=false;
   private QAgent qAgent;          //creating the QAgent
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();   //repainting the rectangle
        for(int i=0; i<heroes.length;i++) {


            randomMove(heroes[i]);

        }
        //qAgent.prepareRequiredData();
        //qAgent.findQ();
       // qAgent.printQMatrix();
        //qAgent.printPolicy();

        for(int i=0;i<heroes.length;i++) {
            if (Map.getMap(heroes[i].getTyleX(), heroes[i].getTyleY()).equals("c")) {
                System.out.println("Winner");
                endMessage = "Winner";
                ended = true;
            }
        }

    }

    public Board(){

        map=new Map();
        //qAgent=new QAgent();
        for(int i=0;i<heroes.length;i++) {
            heroes[i] = new Hero();
        }
       // addKeyListener(new AL());
        this.setFocusable(true);
        this.requestFocus();
        timer=new Timer(1000, this);
        timer.start();           //the action performed will be executed every 25 miliseconds
        setKeyBindings();

     //  qAgent.prepareRequiredData();
      // qAgent.findQ();
      // qAgent.printQMatrix();
      // qAgent.printPolicy();

    }

    public void paint(Graphics g){
        super.paint(g); //using the parent method
        if(!ended) {
            for (int i = 0; i < 14; i++) {   //row
                for (int j = 0; j < 14; j++) {      //column
                    if (Map.getMap(j, i).equals("g")) {   //if it finds "g" when it scans the file- shows grass
                        g.drawImage(map.getGrass(), j * 32, i * 32, null); //no observer required probably
                    }
                    if (Map.getMap(j, i).equals("w")) {       //else if it finds "w" prints wall
                        g.drawImage(map.getWall(), j * 32, i * 32, null);
                    }
                    if (Map.getMap(j, i).equals("c")) {
                        g.drawImage(map.getFinish(), j * 32, i * 32, null);
                    }

                }

            }
        }
        for(int i=0; i<heroes.length;i++) {
            g.drawImage(heroes[i].getHero(), heroes[i].getTyleX() * 32, heroes[i].getTyleY() * 32, null);
        }
           if(endMessage!=null&&ended){

       g.drawString(endMessage,100,100);
       timer.stop();
       }

       // System.out.println("redraw");

    }
static void randomMove(Hero h){  //change to allow negative number
    Random rand = new Random();
    //int x = rand.nextInt(2);
    //int y = rand.nextInt(2);
    int x= ThreadLocalRandom.current().nextInt(-1,1+1);
    int y=ThreadLocalRandom.current().nextInt(-1,1+1);
    System.out.print(x);
    System.out.print(y);
    h.Move(x,y);

}
    private void setKeyBindings(){
        ActionMap am=getActionMap();
        int cond= JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap im =getInputMap(cond);

        String VkLeft = "VK_A";
        String VkRight = "VK_D";
        String VkUp = "VK_W";
        String VkDown = "VK_S";

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0),VkUp);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0),VkDown);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0),VkLeft);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0),VkRight);

        am.put(VkLeft, new KeyAction(VkLeft));
        am.put(VkRight, new KeyAction(VkRight));
        am.put(VkUp, new KeyAction(VkUp));
        am.put(VkDown, new KeyAction(VkDown));
    }


   /* public class AL extends KeyAdapter {     //nested class managing key inputs

          @Override
        public void keyPressed(KeyEvent e){

            int keycode=e.getKeyCode();

            if(keycode == KeyEvent.VK_W){
                hero.Move(0,-32,0,-1);
                                                          //check values when start to move
            }
            if(keycode==KeyEvent.VK_S){
                hero.Move(0,32,0,1);
            }
            if(keycode==KeyEvent.VK_A){
                hero.Move(-32,0,-1,0);
            }
            if(keycode==KeyEvent.VK_D){
                hero.Move(32,0,1,0);
            }
        }

        public void keyReleased(KeyEvent e){

        }

        public void keyTyped(KeyEvent e){

        }



    }*/
    private class KeyAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {

           // System.out.println(e.getActionCommand())
            if(e.getActionCommand().equals( "VK_W")){        //fix comparison
              // if(!Map.getMap(heroes[0].getTyleX(), heroes[0].getTyleY()-1).equals("w")) {
                   heroes[0].Move(0, -1);

                   //check values when start to move
              // }
            }
            if(e.getActionCommand().equals( "VK_S")){
               // if(!Map.getMap(heroes[0].getTyleX(), heroes[0].getTyleY()+1).equals("w")) {
                    heroes[0].Move(0, 1);

                //}
            }
            if(e.getActionCommand().equals( "VK_A")){
                //if(!Map.getMap(heroes[0].getTyleX()-1, heroes[0].getTyleY()).equals("w")) {
                    heroes[0].Move(-1, 0);
                //}
            }
            if(e.getActionCommand().equals("VK_D")){
                //if(!Map.getMap(heroes[0].getTyleX()+1, heroes[0].getTyleY()).equals("w")) {
                    heroes[0].Move(1, 0);
                    System.out.println("Just Work PLSSSSSS");
               // }
            }
        }

        public KeyAction(String command){
            putValue(ACTION_COMMAND_KEY, command);
        }
    }

}


