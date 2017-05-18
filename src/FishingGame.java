import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


/**
*   @Author: U Khyoi Nu
*   @Reg. No.: 2012331064
*   @Project: Fishing Game
*/


/**
*   This is the main class of this project.
*/
public class FishingGame implements MouseListener, KeyListener, MouseMotionListener, Runnable {
    
    /**
    * gp = Main Graphics2D Object. Where the game window runs.
    */
    Graphics2D gp;
    
    /**
    * s = Main ScreenManager Object. Where the game window runs.
    */
    ScreenManager s;
    
    /**
    * game = it is the Object of the PlayGame Class. Which determines the appearance of the fishes.
    */
    PlayGame game;
    
    /**
    * DisplayMode to choose for full screen.
    * Note: This is initiated only for my own pc.
    *       Haven't got the opportunity to check if it works on every pc.
    *       But in most cases it works fine.
    */
    public static final DisplayMode modes1[] = {
        new DisplayMode(800, 600, 32, 0),};
    
    /**
    * bSound = Main Background Sound.it is the Object of the SoundThread Class. Which plays the background sound as long as the game runs.
    */
    static SoundThread bSound = new SoundThread(); 
    
    /**
    * The main method of the game.
    * Here the main object to run the game is created.
    * It also initiates the run method by which every single task of the game is performed.
    * @param args = arguments from the terminal. Not needed.
    */
    public static void main(String[] args) {
        FishingGame main = new FishingGame();
        main.run();
    }
    
    /**
    * Images Used to display the background of each and every new window.
    */
    Image startPage, menu, level, help, scores, background, result;
    
    /**
    * Descriptions for this variable group:
    * score is for monitoring the score of game.
    * time is for showing the time of game on the screen.
    * t is for knowing the position of fish caught.
    * x1, y1 are for the coordinates of the fishes while dragged. 
    * easyHigh, mediumHigh, hardHigh are for the highest scores of different levels. 
    *time1, time2 determine the time interval of fishes appearance.
    * start, cumTime, timePassed2, timePassed control the time for a game.
    */
    public static int score = 0;
    public static int time = 0;
    public static int t = 100;
    public static int x1, y1;
    public static int easyHigh, mediumHigh, hardHigh;
    public static int time1, time2;
    public static long start, cumTime, timePassed2, timePassed;
    
    /**
    * These are the boolean variables to control the appearance of different screens.
    */
    boolean play = false;
    boolean scoresa = false;
    boolean helpa = false;
    boolean resulta = false;
    boolean begina = true;
    
    /**
    * These are the boolean variables to control the changes due to mouse movement.
    */
    /**
    *These are for choosing options.  
    */
    boolean mPlay = false;
    boolean mScores = false;
    boolean mHelp = false;
    boolean mQuit = false;
    /**
    *These are for back buttons.  
    */
    boolean mHelpa = false;
    boolean mScoresa = false;
    boolean mBacka = false;
    boolean mResulta = false;
    /**
    *These are for choosing levels.  
    */
    boolean mEasy = false; 
    boolean mMedium = false;
    boolean mHard = false;
    
    /**
    * These are the boolean variables to determine the level of game.
    */
    boolean easyRun = false; 
    boolean mediumRun = false;
    boolean hardRun = false;
    
    /**
    * These are the boolean variables to control the whole game logic.
    */
    boolean gameMenu = true;
    boolean clicked = false;
    public static boolean[] image = new boolean[11];
    
    /**
    * r is a random number generator for random appearance of the fishes.
    */
    Random r = new Random();
    
    /**
    * This method initializes everything to start the game.
    */
    void init() {
        s = new ScreenManager();
    
        startPage = new ImageIcon("Files\\Images\\StartPage.png").getImage();
        menu = new ImageIcon("Files\\Images\\main menu.png").getImage();
        level = new ImageIcon("Files\\Images\\Level.png").getImage();
        help = new ImageIcon("Files\\Images\\help.png").getImage();
        scores = new ImageIcon("Files\\Images\\scores.png").getImage();
        result = new ImageIcon("Files\\Images\\result.png").getImage();
        background = new ImageIcon("Files\\Images\\PlayBackGround.png").getImage();
        getHighScore();
        for(int i = 0;i < 11; i++){
            image[i] = false;
        }
    }
    
    /**
    * This is the main run method. Where the game begins.
    * Every single task of this game is handled by this method.
    */
    public void run() {
        init();
        try {
            DisplayMode dm = s.findFirstCompatibleMode(modes1);
            s.setFullScreen(dm);
            Window w = s.getFullScreenWindow();
            w.addMouseListener(this);
            w.addMouseMotionListener(this);
            w.addKeyListener(this);
            /**
            * The following five lines are changing the mouse cursor.
            */
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage("Files/Images/Ab2.png");
            Point hotSpot = new Point(0, 25);
            Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "Ab");
            w.setCursor(cursor);
            
            /**
            * This line saves the starting time for starting page.
            */
            start = System.currentTimeMillis();
            
            /**
            * Here the game initializes.
            */
            while (gameMenu == true) {
                Graphics2D g1 = s.getGraphics();
                
                /**
                * This block displays the starting page for 3 seconds.
                */
                while(System.currentTimeMillis() - start <= 3000){
                    if(begina == true)
                    g1.drawImage(startPage, 0, 0, null);
                    s.update();
                    g1.dispose();
                }
                begina = false;
                
                /**
                * These lines displays the main menu.
                */
                paintOpeningScreen(g1);
                s.update();
                g1.dispose();
               
                /**
                 * This block displays the HighScore Screen.
                 */
                while (scoresa == true) {
                    Graphics2D g2 = s.getGraphics();
                    paintScores(g2);
                    s.update();
                    g2.dispose();
                }
                
                /**
                 * This block displays the Help Screen.
                 */
                while (helpa == true) {
                    Graphics2D g2 = s.getGraphics();
                    paintHelp(g2);
                    s.update();
                    g2.dispose();
                }
                
                /**
                * This if block initiates the game & displays the  level selection screen.
                */
                while (play == true) {
                    gp = s.getGraphics();
                    paintLevel(gp);     
                   
                    /**
                    * These are the boolean variables to initialize a particular level of game.
                    */
                    boolean easyFlag = false; 
                    boolean mediumFlag = false;
                    boolean hardFlag = false;

                    score = 0;
                    time = 0;

                    /**
                    * Game for easy difficulty level.
                    */
                    while(easyRun == true && time <= 60){
                        gp = s.getGraphics();
                        /**
                        * Initializes the level.
                        */
                        if(easyFlag == false){
                            game = new PlayGame(s, gp);
                            easyFlag = true;
                            time1 = (int) System.currentTimeMillis();
                            /**
                            * Generating random fishes for random positions.
                            */
                            for (int i = 0; i < 6; ++i) {
                                PlayGame.x[i] = r.nextInt(11);
                                PlayGame.y[i] = r.nextInt(6);
                            }
                        }
                        
                        /**
                        * Making the positions available for another group of fishes.
                        */
                        PlayGame.flag[0] = 0;
                        PlayGame.flag[1] = 0;
                        PlayGame.flag[2] = 0;
                        PlayGame.flag[3] = 0;
                        PlayGame.flag[4] = 0;
                        PlayGame.flag[5] = 0;
                        
                        /**
                        * Displays the game background, score, time and fishes.
                        */
                        paintPlay(gp); 
                        game.paintScoreNTime(gp);
                        for(int i = 0; i < 6; ++i){
                            game.paintFish(gp, PlayGame.x[i], PlayGame.y[i]);
                        }
                        
                        /**
                        * Counting time for the running game.
                        */
                        timePassed = System.currentTimeMillis() - cumTime;
                        timePassed2 += timePassed;
                        if (timePassed2 >= 1000) {
                            ++time;
                            s.update();
                            gp.dispose();
                            timePassed2 = 0;
                        }
                        cumTime += timePassed;

                        time2 = (int) System.currentTimeMillis();

                        /**
                        * Counting time for the one group of fishes.
                        */
                        if(time2 - time1 >= 2800  && time2 - time1 <= 5600){
                            time1 = time2;
                            for (int i = 0; i < 6; ++i) {
                                PlayGame.x[i] = r.nextInt(11);
                                PlayGame.y[i] = r.nextInt(6);
                            }  
                        }

                        s.update();
                        gp.dispose();

                        /**
                        * When the game finishes this block displays the result screen, checks for high score and returns to main menu.
                        */
                        if(time == 60){
                            putHighScore();
                            resulta = true;
                            easyRun = false;
                            play = false;
                        }
                    }

                    /**
                    * Game for medium difficulty level.
                    */
                    while(mediumRun == true && time <= 60){
                        gp = s.getGraphics();
                        
                        /**
                        * Initializes the level.
                        */
                        if(easyFlag == false){
                            game = new PlayGame(s, gp);
                            easyFlag = true;
                            time1 = (int) System.currentTimeMillis();
                            /**
                            * Generating random fishes for random positions.
                            */
                            for (int i = 0; i < 6; ++i) {
                                PlayGame.x[i] = r.nextInt(11);
                                PlayGame.y[i] = r.nextInt(6);
                            }
                        }

                        /**
                        * Making the positions available for another group of fishes.
                        */
                        PlayGame.flag[0] = 0;
                        PlayGame.flag[1] = 0;
                        PlayGame.flag[2] = 0;
                        PlayGame.flag[3] = 0;
                        PlayGame.flag[4] = 0;
                        PlayGame.flag[5] = 0;

                        /**
                        * Displays the game background, score, time and fishes.
                        */
                        paintPlay(gp); 
                        game.paintScoreNTime(gp);
                        for(int i = 0; i < 6; ++i){
                            game.paintFish(gp, PlayGame.x[i], PlayGame.y[i]);
                        }

                        /**
                        * Counting time for the running game.
                        */
                        timePassed = System.currentTimeMillis() - cumTime;
                        timePassed2 += timePassed;
                        if (timePassed2 >= 1000) {
                            ++time;
                            s.update();
                            gp.dispose();
                            timePassed2 = 0;
                        }
                        cumTime += timePassed;

                        time2 = (int) System.currentTimeMillis();

                        /**
                        * Counting time for the one group of fishes.
                        */
                        if(time2 - time1 >= 2100  && time2 - time1 <= 4200){
                            time1 = time2;
                            for (int i = 0; i < 6; ++i) {
                                PlayGame.x[i] = r.nextInt(11);
                                PlayGame.y[i] = r.nextInt(6);
                            }  
                        }

                        s.update();
                        gp.dispose();

                        /**
                        * When the game finishes this block displays the result screen, checks for high score and returns to main menu.
                        */
                        if(time == 60){
                            putHighScore();
                            resulta = true;
                            mediumRun = false;
                            play = false;
                        }
                    }
                    
                    /**
                    * Game for hard difficulty level.
                    */
                    while(hardRun == true && time <= 60){
                        gp = s.getGraphics();
                        
                        /**
                        * Initializes the level.
                        */
                        if(easyFlag == false){
                            game = new PlayGame(s, gp);
                            easyFlag = true;
                            time1 = (int) System.currentTimeMillis();
                            /**
                            * Generating random fishes for random positions.
                            */
                            for (int i = 0; i < 6; ++i) {
                                PlayGame.x[i] = r.nextInt(11);
                                PlayGame.y[i] = r.nextInt(6);
                            }
                        }

                        /**
                        * Making the positions available for another group of fishes.
                        */
                        PlayGame.flag[0] = 0;
                        PlayGame.flag[1] = 0;
                        PlayGame.flag[2] = 0;
                        PlayGame.flag[3] = 0;
                        PlayGame.flag[4] = 0;
                        PlayGame.flag[5] = 0;

                        /**
                        * Displays the game background, score, time and fishes.
                        */
                        paintPlay(gp); 
                        game.paintScoreNTime(gp);
                        for(int i = 0; i < 6; ++i){
                            game.paintFish(gp, PlayGame.x[i], PlayGame.y[i]);
                        }

                        /**
                        * Counting time for the running game.
                        */
                        timePassed = System.currentTimeMillis() - cumTime;
                        timePassed2 += timePassed;
                        if (timePassed2 >= 1000) {
                            ++time;
                            s.update();
                            gp.dispose();
                            timePassed2 = 0;
                        }
                        cumTime += timePassed;

                        time2 = (int) System.currentTimeMillis();

                        /**
                        * Counting time for the one group of fishes.
                        */
                        if(time2 - time1 >= 1200  && time2 - time1 <= 2400){
                            time1 = time2;
                            for (int i = 0; i < 6; ++i) {
                                PlayGame.x[i] = r.nextInt(11);
                                PlayGame.y[i] = r.nextInt(6);
                            }  
                        }

                        s.update();
                        gp.dispose();

                        
                        /**
                        * When the game finishes this block checks for high score and takes to result screen.
                        */
                        if(time == 60){
                            putHighScore();
                            resulta = true;
                            hardRun = false;
                            play = false;
                        }
                    }
                    
                s.update();
                gp.dispose();
                }
                
                /**
                * This block displays the result screen
                */
                while(resulta == true){
                    Graphics2D g2 = s.getGraphics();
                    paintResult(g2);
                    s.update();
                    g2.dispose();
                }
            }
        } catch (Exception e) {
        } finally {
            /**
            * This line restores the screen into the default display of the operating system after while exiting the program.
            */
            s.restoreScreen();
        }
    }

    /**
    * Paint method for main menu.
    */
    public void paintOpeningScreen(Graphics2D g1) {
        g1.drawImage(menu, 0, 0, null);
        Font f;
        Color norm;
        Color m;
        if (mPlay == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 26);
            g1.setFont(f);
            m = new Color(255, 128, 0);
            g1.setColor(m);
            g1.drawString("Play", 300, 430);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 24);
            g1.setFont(f);
            norm = new Color(255, 128, 0);
            g1.setColor(norm);
            g1.drawString("Play", 300, 430);
        }
        if (mScores == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 19);
            g1.setFont(f);
            m = new Color(204, 0, 204);
            g1.setColor(m);
            g1.drawString("High", 463, 526);
            g1.drawString("Score", 462, 546);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 18);
            g1.setFont(f);
            norm = new Color(204, 0, 204);
            g1.setColor(norm);
            g1.drawString("High", 463, 526);
            g1.drawString("Score", 462, 546);
        }
        if (mHelp == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 26);
            g1.setFont(f);
            m = new Color(178, 102, 255);
            g1.setColor(m);
            g1.drawString("Help", 143, 515);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 24);
            g1.setFont(f);
            norm = new Color(178, 102, 255);
            g1.setColor(norm);
            g1.drawString("Help", 143, 515);
        }
        if (mQuit == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 25);
            g1.setFont(f);
            m = new Color(0, 153, 0);
            g1.setColor(m);
            g1.drawString("Quit", 644, 474);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 22);
            g1.setFont(f);
            norm = new Color(0, 153, 0);
            g1.setColor(norm);
            g1.drawString("Quit", 644, 474);
        }
    }
    
    /**
    * Paint method for high score.
    */
    public void paintScores(Graphics2D g) {
        g.drawImage(scores, 0, 0, null);
        Font f;
        Color norm;
        Color m;
        
        if (mScoresa == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 24);
            g.setFont(f);
            m = new Color(255, 128, 0);
            g.setColor(m);
            g.drawString("Back", 660, 547);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 21);
            g.setFont(f);
            norm = new Color(255, 128, 0);
            g.setColor(norm);
            g.drawString("Back", 660, 547);
        }
            f = new Font("Bookman Old Style", Font.BOLD, 40);
            g.setFont(f);
            m = new Color(153, 204, 255);
            g.setColor(m);
            g.drawString(""+easyHigh, 240, 556);
            
            g.setFont(f);
            m = new Color(51, 255, 51);
            g.setColor(m);
            g.drawString(""+mediumHigh, 448, 445);
            
            g.setFont(f);
            m = new Color(204, 153, 255);
            g.setColor(m);
            g.drawString(""+hardHigh, 657, 343);
    }

    /**
    * Paint method for help screen.
    */
    public void paintHelp(Graphics2D g) {
        g.drawImage(help, 0, 0, null);
        Font f;
        Color norm;
        Color m;
        
        if (mHelpa == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 24);
            g.setFont(f);
            m = new Color(255, 128, 0);
            g.setColor(m);
            g.drawString("Back", 660, 547);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 21);
            g.setFont(f);
            norm = new Color(255, 128, 0);
            g.setColor(norm);
            g.drawString("Back", 660, 547);
        }
    }
    
    /**
    * Paint method for result screen.
    */
    public void paintResult(Graphics2D g) {
        g.drawImage(result, 0, 0, null);
        Font f;
        Color norm;
        Color m;
        
        if (mResulta == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 23);
            g.setFont(f);
            m = new Color(255, 128, 0);
            g.setColor(m);
            g.drawString("Menu", 660, 547);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 21);
            g.setFont(f);
            norm = new Color(255, 128, 0);
            g.setColor(norm);
            g.drawString("Menu", 660, 547);
        }
        
        if (score < 100) {
            f = new Font("Snap ITC", Font.BOLD, 70);
            g.setFont(f);
            m = new Color(204, 0, 0);
            g.setColor(m);
            g.drawString("BAD", 320, 305);
            f = new Font("Broadway", Font.BOLD, 50);
            g.setFont(f);
            g.drawString("YOU SCORED "+score, 180, 370);
        }
        
        else if (score >= 100 && score < 250) {
            f = new Font("Snap ITC", Font.BOLD, 45);
            g.setFont(f);
            m = new Color(255, 255, 102);
            g.setColor(m);
            g.drawString("AVERAGE", 300, 305);
            f = new Font("Broadway", Font.BOLD, 50);
            g.setFont(f);
            g.drawString("YOU SCORED "+score, 190, 370);
        }
        
        else if (score >= 250) {
            f = new Font("Snap ITC", Font.BOLD, 70);
            g.setFont(f);
            m = new Color(0, 153, 0);
            g.setColor(m);
            g.drawString("GOOD", 305, 305);
            f = new Font("Broadway", Font.BOLD, 50);
            g.setFont(f);
            g.drawString("YOU SCORED "+score, 180, 370);
        }
        
    }

    /**
    * Paint method for game screen.
    */
    public void paintPlay(Graphics2D g) {
        g.drawImage(background, 0, 0, null);
        Font f;
        Color norm;
        Color m;

        if (mBacka == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 24);
            g.setFont(f);
            m = new Color(255, 128, 0);
            g.setColor(m);
            g.drawString("Back", 64, 548);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 22);
            g.setFont(f);
            norm = new Color(255, 128, 0);
            g.setColor(norm);
            g.drawString("Back", 64, 549);
        }
    }
    
    /**
    * Paint method for level selection screen.
    */
    public void paintLevel(Graphics2D g) {
        g.drawImage(level, 0, 0, null);
        Font f;
        Color norm;
        Color m;

        if (mBacka == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 24);
            g.setFont(f);
            m = new Color(255, 128, 0);
            g.setColor(m);
            g.drawString("Back", 64, 548);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 22);
            g.setFont(f);
            norm = new Color(255, 128, 0);
            g.setColor(norm);
            g.drawString("Back", 64, 549);
        }
        if (mEasy == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 24);
            g.setFont(f);
            m = new Color(128, 255, 0);
            g.setColor(m);
            g.drawString("Easy", 248, 340);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 24);
            g.setFont(f);
            norm = new Color(128, 255, 0);
            g.setColor(norm);
            g.drawString("Easy", 248, 340);
        }
        if (mMedium == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 18);
            g.setFont(f);
            m = new Color(204, 204, 255);
            g.setColor(m);
            g.drawString("Medium", 419, 494);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 18);
            g.setFont(f);
            norm = new Color(204, 204, 255);
            g.setColor(norm);
            g.drawString("Medium", 419, 494);
        }
        if (mHard == true) {
            f = new Font("Comic Sans MS", Font.BOLD, 22);
            g.setFont(f);
            m = new Color(255, 0, 0);
            g.setColor(m);
            g.drawString("Hard", 665, 382);
        } else {
            f = new Font("Comic Sans MS", Font.PLAIN, 22);
            g.setFont(f);
            norm = new Color(255, 0, 0);
            g.setColor(norm);
            g.drawString("Hard", 665, 382);
        }
    }
    
      
    /**
    * This method reads the stored HighScores from the consecutive files.
    */
    private void getHighScore()
    {
        try
        {
            File Fe = new File("Files\\Easy.txt");
            File Fm = new File("Files\\Medium.txt");
            File Fh = new File("Files\\Hard.txt");
            Scanner Se = new Scanner(Fe);
            Scanner Sm = new Scanner(Fm);
            Scanner Sh = new Scanner(Fh);
            easyHigh = Se.nextInt();
            mediumHigh = Sm.nextInt();
            hardHigh = Sh.nextInt();
            Se.close();
            Sm.close();
            Sh.close();
        }
        catch(Exception e){}
    }
    
    /**
    * This method stores the new HighScores in the consecutive files.
    */
    private void putHighScore()
    {
        try
        {
            BufferedWriter outputWriter = null;
            if(easyRun == true)
            {
                if(score>easyHigh)
                {
                    easyHigh = score;
                    try
                    {
                        outputWriter = new BufferedWriter(new FileWriter("Files\\Easy.txt"));
                        outputWriter.write(Integer.toString(easyHigh));
                        outputWriter.flush();  
                        outputWriter.close();
                    }
                    catch(Exception e){}
                }
            }
            if(mediumRun == true)
            {
                if(score>mediumHigh)
                {
                    mediumHigh = score;
                    try
                    {
                        outputWriter = new BufferedWriter(new FileWriter("Files\\Medium.txt"));
                        outputWriter.write(Integer.toString(mediumHigh));
                        outputWriter.flush();  
                        outputWriter.close();
                    }
                    catch(Exception e){}
                }
            }
            if(hardRun == true)
            {
                if(score>hardHigh)
                {
                    hardHigh = score;
                    try
                    {
                        outputWriter = new BufferedWriter(new FileWriter("Files\\Hard.txt"));
                        outputWriter.write(Integer.toString(hardHigh));
                        outputWriter.flush();  
                        outputWriter.close();
                    }
                    catch(Exception e){}
                }
            }
                
        }
        catch(Exception e){}
    }

    /**
     * This method is only used for presenting an attractive GUI.
     * Handles the change of size of different buttons if the mouse pointer goes over it.
     * @param e refers to the mouse event.
     */
    public void mouseMoved(MouseEvent e) {
        
        /**
        * Handles buttons of main menu.
        */
        if (e.getX() > 282 && e.getX() < 347 && e.getY() < 441 && e.getY() > 402) {
            mPlay = true;
        } else {
            mPlay = false;
        }

        if (e.getX() > 450 && e.getX() < 523 && e.getY() < 545 && e.getY() > 513) {
            mScores = true;
        } else {
            mScores = false;
        }

        if (e.getX() > 125 && e.getX() < 187 && e.getY() < 517 && e.getY() > 490) {
            mHelp = true;
        } else {
            mHelp = false;
        }

        if (e.getX() > 632 && e.getX() < 693 && e.getY() < 485 && e.getY() > 451) {
            mQuit = true;
        } else {
            mQuit = false;
        }
        
        /**
        * Handles buttons of level selection.
        */
        if (e.getX() > 239 && e.getX() < 302 && e.getY() < 364 && e.getY() > 319) {
            mEasy = true;
        } else {
            mEasy = false;
        }

        if (e.getX() > 414 && e.getX() < 484 && e.getY() < 520 && e.getY() > 472) {
            mMedium = true;
        } else {
            mMedium = false;
        }

        if (e.getX() > 659 && e.getX() < 723 && e.getY() < 400 && e.getY() > 359) {
            mHard = true;
        } else {
            mHard = false;
        }
        
        /**
        * Handles the back buttons.
        */
        if (e.getX() > 650 && e.getX() < 715 && e.getY() < 555 && e.getY() > 524) {
            mHelpa = true;
        } else {
            mHelpa = false;
        }
        
        if (e.getX() > 650 && e.getX() < 715 && e.getY() < 555 && e.getY() > 524) {
            mScoresa = true;
        } else {
            mScoresa = false;
        }
        
        if (e.getX() > 650 && e.getX() < 715 && e.getY() < 555 && e.getY() > 524) {
            mResulta = true;
        } else {
            mResulta = false;
        }
        
        if (e.getX() > 48 && e.getX() < 110 && e.getY() > 521 && e.getY() < 551) {
            mBacka = true;
        } else {
            mBacka = false;
        }
    }
    
    /**
    * This method handles the button selection.
    * It checks the success of click and plays the sound.
    @param e refers to the mouse clicking event.
    */
    public void mouseClicked(MouseEvent e) {
        
        /**
        * This block checks if Quit is clicked.
        * If so it plays the clicking sound and terminates the program.
        */
        if (e.getX() > 632 && e.getX() < 693 && e.getY() < 485 && e.getY() > 451) {
            if(play == false && helpa == false && scoresa == false){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                System.exit(0);
            }
        }
        
        /**
        * This block checks if Help is clicked.
        * If so it plays the clicking sound and displays the help screen.
        */
        if (e.getX() > 125 && e.getX() < 187 && e.getY() < 517 && e.getY() > 490) {
            if(play == false && helpa == false && scoresa == false){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                helpa = true;
            }
        }
        /**
        * This block checks if Back button of Help is clicked.
        * If so it plays the clicking sound and displays the main menu.
        */
        if (e.getX() > 650 && e.getX() < 715 && e.getY() < 555 && e.getY() > 524) {
            if(helpa == true){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                helpa = false;
            }
        }
        
        /**
        * This block checks if High Score is clicked.
        * If so it plays the clicking sound and displays the high score screen.
        */
        if (e.getX() > 450 && e.getX() < 523 && e.getY() < 545 && e.getY() > 513) {
            if(play == false && helpa == false && scoresa == false){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                scoresa = true;
            }
        }
        /**
        * This block checks if Back button of High Score is clicked.
        * If so it plays the clicking sound and displays the main menu.
        */
        if (e.getX() > 650 && e.getX() < 715 && e.getY() < 555 && e.getY() > 524) {
            if(scoresa == true){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                scoresa = false;
            }
        }
        
        /**
        * This block checks if Play is clicked.
        * If so it plays the clicking sound and displays the level selection screen.
        */
        if (e.getX() > 282 && e.getX() < 347 && e.getY() < 441 && e.getY() > 402) {
            if(play == false && helpa == false && scoresa == false){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                play = true;
            }
        }
        /**
        * This block checks if Back button of Level Selection screen is clicked.
        * If so it plays the clicking sound and displays the main menu.
        */
        if (e.getX() > 48 && e.getX() < 110 && e.getY() > 521 && e.getY() < 551) {
            if(play == true && resulta == false)
                if(easyRun == false && mediumRun == false && hardRun == false){
                    try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                play = false;
                }
        }
        
        /**
        * This block checks if Back button of Game is clicked when the game is running.
        * If so it plays the clicking sound and displays the level selection screen.
        */
        if (e.getX() > 48 && e.getX() < 110 && e.getY() > 521 && e.getY() < 551) {
            if(play == true && resulta == false){
                if(easyRun == true || mediumRun == true || hardRun == true){
                    try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                easyRun = false;
                mediumRun = false;
                hardRun = false;
                }
            }
        }
        
        /**
        * This block checks if Menu button of Help is clicked.
        * If so it plays the clicking sound and displays the main menu.
        */
        if (e.getX() > 650 && e.getX() < 715 && e.getY() < 555 && e.getY() > 524) {
            if(resulta == true){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                resulta = false;
            }
        }
        
        /**
        * This block checks if Easy button of level selection is clicked.
        * If so it plays the clicking sound and starts the game for easy level.
        */
        if (e.getX() > 239 && e.getX() < 302 && e.getY() < 364 && e.getY() > 319) {
            if(play == true && resulta == false && easyRun == false && mediumRun == false && hardRun == false){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                easyRun = true;
            }
        }
        
        /**
        * This block checks if Medium button of level selection is clicked.
        * If so it plays the clicking sound and starts the game for medium level.
        */
        if (e.getX() > 414 && e.getX() < 484 && e.getY() < 520 && e.getY() > 472) {
            if(play == true && resulta == false && easyRun == false && mediumRun == false && hardRun == false){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                mediumRun = true;
            }
        }
        
        /**
        * This block checks if Hard button of level selection is clicked.
        * If so it plays the clicking sound and starts the game for hard level.
        */
        if (e.getX() > 659 && e.getX() < 723 && e.getY() < 400 && e.getY() > 359) {
            if(play == true && resulta == false && easyRun == false && mediumRun == false && hardRun == false){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Caught.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } 
                catch (Exception ex) {}
                hardRun = true;
            }
        }
        
    }

    /**
    * This method handles the fish catching.
    * @param e refers to the mouse pressing event.
    */
    public void mousePressed(MouseEvent e) {
        
        /**
        * These if blocks checks if the player clicked on any fish.
        * If so it saves the array index of selected fish.
        */
        if(PlayGame.flag[0] == 1 || PlayGame.flag[0] == 2 || 
           PlayGame.flag[0] == 3 || PlayGame.flag[0] == 4 ||
           PlayGame.flag[0] == 5 || PlayGame.flag[0] == 6 || 
           PlayGame.flag[0] == 7 || PlayGame.flag[0] == 8 ||
           PlayGame.flag[0] == 9 || PlayGame.flag[0] == 10 ||
           PlayGame.flag[0] == 11)
        {
            if((e.getX()>=PlayGame.xs[0]) && (e.getX()<=(PlayGame.xs[0]+75)) && (e.getY()>=PlayGame.ys[0]-10) && (e.getY()<=(PlayGame.ys[0]+65))){
            t = 0;
            clicked = true;
            x1=e.getX();
            y1=e.getY();
            if(PlayGame.flag[0] == 1)
                image[0] = true;
            else if(PlayGame.flag[0] == 2)
                image[1] = true;
            else if(PlayGame.flag[0] == 3)
                image[2] = true;
            else if(PlayGame.flag[0] == 4)
                image[3] = true;
            else if(PlayGame.flag[0] == 5)
                image[4] = true;
            else if(PlayGame.flag[0] == 6)
                image[5] = true;
            else if(PlayGame.flag[0] == 7)
                image[6] = true;
            else if(PlayGame.flag[0] == 8)
                image[7] = true;
            else if(PlayGame.flag[0] == 9)
                image[8] = true;
            else if(PlayGame.flag[0] == 10)
                image[9] = true;
            else if(PlayGame.flag[0] == 11)
                image[10] = true;
            }
        }
        if(PlayGame.flag[1] == 1 || PlayGame.flag[1] == 2 || 
           PlayGame.flag[1] == 3 || PlayGame.flag[1] == 4 ||
           PlayGame.flag[1] == 5 || PlayGame.flag[1] == 6 || 
           PlayGame.flag[1] == 7 || PlayGame.flag[1] == 8 ||
           PlayGame.flag[1] == 9 || PlayGame.flag[1] == 10 ||
           PlayGame.flag[1] == 11)
        {
            if((e.getX()>=PlayGame.xs[1]) && (e.getX()<=(PlayGame.xs[1]+75)) && (e.getY()>=PlayGame.ys[1]-10) && (e.getY()<=(PlayGame.ys[1]+65))){
            t = 1;
            clicked = true;
            x1=e.getX();
            y1=e.getY();
            if(PlayGame.flag[1] == 1)
                image[0] = true;
            else if(PlayGame.flag[1] == 2)
                image[1] = true;
            else if(PlayGame.flag[1] == 3)
                image[2] = true;
            else if(PlayGame.flag[1] == 4)
                image[3] = true;
            else if(PlayGame.flag[1] == 5)
                image[4] = true;
            else if(PlayGame.flag[1] == 6)
                image[5] = true;
            else if(PlayGame.flag[1] == 7)
                image[6] = true;
            else if(PlayGame.flag[1] == 8)
                image[7] = true;
            else if(PlayGame.flag[1] == 9)
                image[8] = true;
            else if(PlayGame.flag[1] == 10)
                image[9] = true;
            else if(PlayGame.flag[1] == 11)
                image[10] = true;
            }
        }
        if(PlayGame.flag[2] == 1 || PlayGame.flag[2] == 2 || 
           PlayGame.flag[2] == 3 || PlayGame.flag[2] == 4 ||
           PlayGame.flag[2] == 5 || PlayGame.flag[2] == 6 || 
           PlayGame.flag[2] == 7 || PlayGame.flag[2] == 8 ||
           PlayGame.flag[2] == 9 || PlayGame.flag[2] == 10 ||
           PlayGame.flag[2] == 11)
        {
            if((e.getX()>=PlayGame.xs[2]) && (e.getX()<=(PlayGame.xs[2]+75)) && (e.getY()>=PlayGame.ys[2]-10) && (e.getY()<=(PlayGame.ys[2]+65))){
            t = 2;
            clicked = true;
            x1=e.getX();
            y1=e.getY();
            if(PlayGame.flag[2] == 1)
                image[0] = true;
            else if(PlayGame.flag[2] == 2)
                image[1] = true;
            else if(PlayGame.flag[2] == 3)
                image[2] = true;
            else if(PlayGame.flag[2] == 4)
                image[3] = true;
            else if(PlayGame.flag[2] == 5)
                image[4] = true;
            else if(PlayGame.flag[2] == 6)
                image[5] = true;
            else if(PlayGame.flag[2] == 7)
                image[6] = true;
            else if(PlayGame.flag[2] == 8)
                image[7] = true;
            else if(PlayGame.flag[2] == 9)
                image[8] = true;
            else if(PlayGame.flag[2] == 10)
                image[9] = true;
            else if(PlayGame.flag[2] == 11)
                image[10] = true;
            }
        }
        if(PlayGame.flag[3] == 1 || PlayGame.flag[3] == 2 || 
           PlayGame.flag[3] == 3 || PlayGame.flag[3] == 4 ||
           PlayGame.flag[3] == 5 || PlayGame.flag[3] == 6 || 
           PlayGame.flag[3] == 7 || PlayGame.flag[3] == 8 ||
           PlayGame.flag[3] == 9 || PlayGame.flag[3] == 10 ||
           PlayGame.flag[3] == 11)
        {
            if((e.getX()>=PlayGame.xs[3]) && (e.getX()<=(PlayGame.xs[3]+75)) && (e.getY()>=PlayGame.ys[3]-10) && (e.getY()<=(PlayGame.ys[3]+65))){
            t = 3;
            clicked = true;
            x1=e.getX();
            y1=e.getY();
            if(PlayGame.flag[3] == 1)
                image[0] = true;
            else if(PlayGame.flag[3] == 2)
                image[1] = true;
            else if(PlayGame.flag[3] == 3)
                image[2] = true;
            else if(PlayGame.flag[3] == 4)
                image[3] = true;
            else if(PlayGame.flag[3] == 5)
                image[4] = true;
            else if(PlayGame.flag[3] == 6)
                image[5] = true;
            else if(PlayGame.flag[3] == 7)
                image[6] = true;
            else if(PlayGame.flag[3] == 8)
                image[7] = true;
            else if(PlayGame.flag[3] == 9)
                image[8] = true;
            else if(PlayGame.flag[3] == 10)
                image[9] = true;
            else if(PlayGame.flag[3] == 11)
                image[10] = true;
            }
        }
        if(PlayGame.flag[4] == 1 || PlayGame.flag[4] == 2 || 
           PlayGame.flag[4] == 3 || PlayGame.flag[4] == 4 ||
           PlayGame.flag[4] == 5 || PlayGame.flag[4] == 6 || 
           PlayGame.flag[4] == 7 || PlayGame.flag[4] == 8 ||
           PlayGame.flag[4] == 9 || PlayGame.flag[4] == 10 ||
           PlayGame.flag[4] == 11)
        {
            if((e.getX()>=PlayGame.xs[4]) && (e.getX()<=(PlayGame.xs[4]+75)) && (e.getY()>=PlayGame.ys[4]-10) && (e.getY()<=(PlayGame.ys[4]+65))){
            t = 4;
            clicked = true;
            x1=e.getX();
            y1=e.getY();
            if(PlayGame.flag[4] == 1)
                image[0] = true;
            else if(PlayGame.flag[4] == 2)
                image[1] = true;
            else if(PlayGame.flag[4] == 3)
                image[2] = true;
            else if(PlayGame.flag[4] == 4)
                image[3] = true;
            else if(PlayGame.flag[4] == 5)
                image[4] = true;
            else if(PlayGame.flag[4] == 6)
                image[5] = true;
            else if(PlayGame.flag[4] == 7)
                image[6] = true;
            else if(PlayGame.flag[4] == 8)
                image[7] = true;
            else if(PlayGame.flag[4] == 9)
                image[8] = true;
            else if(PlayGame.flag[4] == 10)
                image[9] = true;
            else if(PlayGame.flag[4] == 11)
                image[10] = true;
            }
        }
        if(PlayGame.flag[5] == 1 || PlayGame.flag[5] == 2 || 
           PlayGame.flag[5] == 3 || PlayGame.flag[5] == 4 ||
           PlayGame.flag[5] == 5 || PlayGame.flag[5] == 6 || 
           PlayGame.flag[5] == 7 || PlayGame.flag[5] == 8 ||
           PlayGame.flag[5] == 9 || PlayGame.flag[5] == 10 ||
           PlayGame.flag[5] == 11)
        {
            if((e.getX()>=PlayGame.xs[5]) && (e.getX()<=(PlayGame.xs[5]+75)) && (e.getY()>=PlayGame.ys[5]-10) && (e.getY()<=(PlayGame.ys[5]+65))){
            t = 5;
            clicked = true;
            x1=e.getX();
            y1=e.getY();
            if(PlayGame.flag[5] == 1)
                image[0] = true;
            else if(PlayGame.flag[5] == 2)
                image[1] = true;
            else if(PlayGame.flag[5] == 3)
                image[2] = true;
            else if(PlayGame.flag[5] == 4)
                image[3] = true;
            else if(PlayGame.flag[5] == 5)
                image[4] = true;
            else if(PlayGame.flag[5] == 6)
                image[5] = true;
            else if(PlayGame.flag[5] == 7)
                image[6] = true;
            else if(PlayGame.flag[5] == 8)
                image[7] = true;
            else if(PlayGame.flag[5] == 9)
                image[8] = true;
            else if(PlayGame.flag[5] == 10)
                image[9] = true;
            else if(PlayGame.flag[5] == 11)
                image[10] = true;
            }
        }
    }

    /**
    * This method handles the appearance of dragged fish with mouse movement.
    * @param e refers to the mouse dragging event.
    */
    public void mouseDragged(MouseEvent e) {
        if(clicked == true)
        {
            x1=e.getX();
            y1=e.getY();
        }

    }
    
    /**
    * This method handles the actions of fish releasing at different positions.
    * @param e refers to the mouse releasing event.
    */
    public void mouseReleased(MouseEvent e) {
        if(clicked == true){
            
            /**
            * These if block checks if the player released the fish in the basket or not.
            * If so it plays the fish releasing sound.
            * It adds 10 points to the score if correct fishes are released.
            * It subtracts 10 points from the score if the wrong fish is released.
            */
            if(e.getX()>615 && e.getX()<777 && e.getY()>422 && e.getY()<570)
            if(resulta == false)
            {
                if(image[10] == true){
                    if(FishingGame.score>0)
                        FishingGame.score-=10;
                    image[10] = false;       
                    try {
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\FightingFish.wav").getAbsoluteFile());
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);
                            clip.start();
                    } 
                    catch (Exception ex) {}
                }
                else{
                    FishingGame.score+=10;
                    try {
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files\\Sounds\\Basket.wav").getAbsoluteFile());
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);
                            clip.start();
                    } 
                    catch (Exception ex) {}
                }
            }
            
            /**
            * These if blocks handle the situations if the fishes are not released in the basket.
            */
            if(image[0] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[0] = false;
            }
            else if(image[1] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[1] = false;
            }
            else if(image[2] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[2] = false;
            }
            else if(image[3] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[3] = false;
            }
            else if(image[4] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[4] = false;
            }
            else if(image[5] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[5] = false;
            }
            else if(image[6] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[6] = false;
            }
            else if(image[7] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[7] = false;
            }
            else if(image[8] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[8] = false;
            }
            else if(image[9] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[9] = false;
            }
            else if(image[10] == true)
            {
                x1 = s.getWidth();
                y1 = s.getHeight();
                image[10] = false;
            }
        }
        clicked = false;
        t = 100;
    }
    
    /**
    * The program Exits on pressing the escape key at anytime.
    * @param e refers to the key pressing event.
    */
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    /**
    * These are unused methods.
    * Bound to give blank bodies because of the implementations of
    * MouseListener, KeyListener, MouseMotionListener
    * @param e refers to the mouse & key events.
    */
    public void keyReleased(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
