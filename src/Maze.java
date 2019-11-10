import javax.swing.*;



public class Maze  {


    
  public  Maze() {
    JFrame frame = new JFrame();
    frame.setTitle("Maze Adventure");
    frame.setSize(550,550);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.add(new Board());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
}



}
