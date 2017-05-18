import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class PlayGame {
    
    /**
    * s = Main ScreenManager Object. Where the game window runs.
    */
    public ScreenManager s;
    
    /**
    * g = Main Graphics2D Object. Where the game window runs.
    */
    public Graphics2D g;
    
    /**
    * Descriptions for the arrays:
    * x[], y[] are for storing the randomly generated fishes and their positions.
    * xs[], ys[] are for storing the fixed coordinates of the fishes.
    * fishes[] is for storing the images of appearing fishes.
    * mach[] is for storing the images of caught fishes.
    * flag[] is for preventing overlapping of fishes appearing on the screen randomly. 
    */
    public static int x[] = new int[10];
    public static int y[] = new int[6];
    
    public static int xs[] = new int[6];
    public static int ys[] = new int[6];
    
    public Image fishes[] = new Image[11];    
    public Image mach[] = new Image[11];
    
    public static int flag[] = new int[6];
    
    /**
    * This constructor initializes everything for the class.
    */
    PlayGame(ScreenManager s, Graphics2D g) {
   
        this.g = g;
        this.s = s;
        
        xs[0] = 155;
        xs[1] = 251;
        xs[2] = 579;
        xs[3] = 474;
        xs[4] = 277;
        xs[5] = 407;

        ys[0] = 361;
        ys[1] = 247;
        ys[2] = 285;
        ys[3] = 457;
        ys[4] = 470;
        ys[5] = 320;
        
        flag[0] = 0;
        flag[1] = 0;
        flag[2] = 0;
        flag[3] = 0;
        flag[4] = 0;
        flag[5] = 0;
        
        fishes[0] = new ImageIcon("Files\\Images\\01.png").getImage();
        fishes[1] = new ImageIcon("Files\\Images\\02.png").getImage();
        fishes[2] = new ImageIcon("Files\\Images\\03.png").getImage();
        fishes[3] = new ImageIcon("Files\\Images\\04.png").getImage();
        fishes[4] = new ImageIcon("Files\\Images\\05.png").getImage();
        fishes[5] = new ImageIcon("Files\\Images\\06.png").getImage();
        fishes[6] = new ImageIcon("Files\\Images\\07.png").getImage();
        fishes[7] = new ImageIcon("Files\\Images\\08.png").getImage();
        fishes[8] = new ImageIcon("Files\\Images\\09.png").getImage();
        fishes[9] = new ImageIcon("Files\\Images\\10.png").getImage();
        fishes[10] = new ImageIcon("Files\\Images\\fightingFish.png").getImage();
        
        mach[0] = new ImageIcon("Files\\Images\\11.png").getImage();
        mach[1] = new ImageIcon("Files\\Images\\12.png").getImage();
        mach[2] = new ImageIcon("Files\\Images\\13.png").getImage();
        mach[3] = new ImageIcon("Files\\Images\\14.png").getImage();
        mach[4] = new ImageIcon("Files\\Images\\15.png").getImage();
        mach[5] = new ImageIcon("Files\\Images\\16.png").getImage();
        mach[6] = new ImageIcon("Files\\Images\\17.png").getImage();
        mach[7] = new ImageIcon("Files\\Images\\18.png").getImage();
        mach[8] = new ImageIcon("Files\\Images\\19.png").getImage();
        mach[9] = new ImageIcon("Files\\Images\\20.png").getImage();
        mach[10] = new ImageIcon("Files\\Images\\fightingFish2.png").getImage();
    }
    
    /**
    * This method displays the score and time at fixed positions when the game is running.
    */
    public void paintScoreNTime(Graphics2D g) {
        g.setFont(new Font("Broadway", Font.BOLD, 30));
        g.setColor(Color.DARK_GRAY);
        g.drawString(String.valueOf(FishingGame.score), 678, 562);

        g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        g.setColor(Color.RED);
        g.drawString(String.valueOf(FishingGame.time), 700, 53);
    }
    
    /**
    * This method displays the random fishes at random positions when the game is running.
    * And it also saves which fish is displayed at which position.
    */
    public void paintFish(Graphics2D g, int x, int y) {      
        if (flag[y] == 0){
            g.drawImage(fishes[x], xs[y], ys[y], null);
            if(x == 0)
            flag[y] = 1;
            else if(x == 1)
            flag[y] = 2;
            else if(x == 2)
            flag[y] = 3;
            else if(x == 3)
            flag[y] = 4;
            else if(x == 4)
            flag[y] = 5;
            else if(x == 5)
            flag[y] = 6;
            else if(x == 6)
            flag[y] = 7;
            else if(x == 7)
            flag[y] = 8;
            else if(x == 8)
            flag[y] = 9;
            else if(x == 9)
            flag[y] = 10;
            else if(x == 10)
            flag[y] = 11;
        }
        
        /**
        * These if blocks displays the caught fishes while they are dragged.
        */
        if(FishingGame.image[0] == true){
            flag[FishingGame.t] = 1;
            g.drawImage(mach[0], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[1] == true){
            flag[FishingGame.t] = 2;
            g.drawImage(mach[1], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[2] == true){
            flag[FishingGame.t] = 3;
            g.drawImage(mach[2], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[3] == true){
            flag[FishingGame.t] = 4;
            g.drawImage(mach[3], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[4] == true){
            flag[FishingGame.t] = 5;
            g.drawImage(mach[4], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[5] == true){
            flag[FishingGame.t] = 6;
            g.drawImage(mach[5], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[6] == true){
            flag[FishingGame.t] = 7;
            g.drawImage(mach[6], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[7] == true){
            flag[FishingGame.t] = 8;
            g.drawImage(mach[7], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[8] == true){
            flag[FishingGame.t] = 9;
            g.drawImage(mach[8], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[9] == true){
            flag[FishingGame.t] = 10;
            g.drawImage(mach[9], FishingGame.x1-25, FishingGame.y1-5, null);
        }
        else if(FishingGame.image[10] == true){
            flag[FishingGame.t] = 11;
            g.drawImage(mach[10], FishingGame.x1-25, FishingGame.y1-5, null);
        }        
    }   
}