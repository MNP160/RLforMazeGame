import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Map {     //will hold the maze map

    private Scanner scan;
    protected static String[] map=new String[14];  //protected because QAgent needs access to It/ alternatively load the file again in QAgent class
    private Image grass;  // will hold the 32x32 images i found
    private Image wall;
    private Image finish;


    public Map(){

        ImageIcon icon= new ImageIcon("C:\\Users\\mpene\\Desktop\\GameImages\\Blocks\\stoneBlock.png");
        grass=icon.getImage();

        icon=new ImageIcon("C:\\Users\\mpene\\Desktop\\GameImages\\Blocks\\darkStoneBlock.png");
        wall=icon.getImage();

        icon=new ImageIcon("C:\\Users\\mpene\\Desktop\\GameImages\\Blocks\\coin.png");
        finish=icon.getImage();
        openFile();
        readFile();
        closeFile();
    }

    public Image getGrass(){     //will be used to pass the images to the board class
        return grass;
    }
    public Image getWall(){
        return wall;
    }
    public Image getFinish(){return finish;}

    public void openFile(){
        try {


            scan = new Scanner(new File("C:\\Users\\mpene\\Desktop\\GameImages\\mazeMap.txt"));  //hardcoded maze for now
                                                          //path to text file containing matrix/maze
       }
        catch(Exception ex){          //should not crash If cant find file
           System.out.println(ex.getMessage());

        }
    }

    public void closeFile(){
         scan.close();
    }

    public void readFile(){          //support function for reading the file
        while(scan.hasNext()){
            for(int i=0; i<14;i++){
              map[i]=scan.next();
            }
        }
    }

    public static String getMap(int x, int y){             //method for matching tile to position in map
         String index=map[y].substring(x,x+1);
         return index;

    }

}
