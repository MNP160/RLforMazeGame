import javax.swing.*;
import java.awt.*;

public class Hero {

   // private int x;
   // private int y;
    private int tyleX;
    private int tyleY;
    private Image hero;


   public static void movebit(Hero h,int x, int y){
         h.Move(x,y);
     }


    public Hero(){
   //   x=32;        //starting position
     // y=32;


      tyleX=1;
      tyleY=1;

        ImageIcon icon = new ImageIcon("C:\\Users\\mpene\\Desktop\\GameImages\\Blocks\\blobRight.png");
        hero=icon.getImage( );
    }

    public void Move(/*int ix, int iy,*/ int tx, int ty){   //x-go left or right depending on positive/negative
                                      //y-go up or down depending on positive/negative
   //  x+=ix;
   //  y+=iy;
   if(!Map.getMap(this.getTyleX()+tx, this.getTyleY()+ty).equals("w")) {


      tyleX += tx;
      tyleY += ty;
   }



    }
    public Image getHero(){
        return hero;
    }

  /*  public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }*/
    public int getTyleX(){
        return tyleX;
    }

    public int getTyleY(){
        return tyleY;
    }

}
