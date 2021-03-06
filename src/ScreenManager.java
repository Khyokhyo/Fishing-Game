import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

/**
 * @Author: U Khyoi Nu
 * @Reg. No. : 2012331064
 * @Class: This class handles the display.
 */

public class ScreenManager 
{
    private GraphicsDevice vc;
    
    /**
     * Constructor for the class.
     */
    public ScreenManager ()
    {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = e.getDefaultScreenDevice();    
    }
    
    /**
     * @return CompatibleDisplayMode
     */
    public DisplayMode[] getCompatibleDisplayModes()
    {
        return vc.getDisplayModes();
    }
    
    /**
     * @param modes: The display modes from FishingGame Class.
     * @return Matched CompatibleDisplayMode
     */
    public DisplayMode findFirstCompatibleMode(DisplayMode modes[])
    {
        DisplayMode goodModes[] = vc.getDisplayModes();
        for (int x=0;x<modes.length;++x)
        {
            for (int y=0;y<goodModes.length;++y)
            {
                if (displayModesMatch(modes[x],goodModes[y]))
                {
                    return (modes[x]);
                }
            }   
        }
        return null;
    }
    
    /**
     * @return CurrentDisplayMode
     */
    public DisplayMode getCurrentDisplayMode()
    {
        return vc.getDisplayMode();
    }
    
    /**
     * @param m1: Heights & Widths
     * @param m2: Heights & Widths
     * @return: displayModesMatch
     */
    public boolean displayModesMatch(DisplayMode m1,DisplayMode m2)
    {
        if (m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight())
        {
            return false;
        }
        if (m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth())
        {
            return false;
        }
        if (m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate())
        {
            return false;
        }
        return true;
    }
    
    /**
     * Sets the window to FullScreen.
     * @param dm : currentDisplayMode
     */
    public void setFullScreen (DisplayMode dm)
    {
        JFrame f = new JFrame();
        f.setUndecorated(true);
        f.setIgnoreRepaint(true);
        f.setResizable(false);
        vc.setFullScreenWindow(f);
        
        if (dm!=null && vc.isDisplayChangeSupported())
        {
            try 
            {
                vc.setDisplayMode(dm);    
            }
            catch(Exception e){}
        }
        f.createBufferStrategy(2);
    }
    
    /**
     * @return Graphics2D object
     */
    public Graphics2D getGraphics ()
    {
        Window w = vc.getFullScreenWindow();
        if (w!=null)
        {
            BufferStrategy s = w.getBufferStrategy();
            return (Graphics2D)s.getDrawGraphics();
        }
        else 
            return null;
    }
    
    /**
     * Refreshes the screen.
     */
    public void update()
    {
        Window w = vc.getFullScreenWindow();
        if (w!=null)
        {
            BufferStrategy s = w.getBufferStrategy();
            if (!s.contentsLost())
            {
                s.show();
            }
        }
    }
    
    /**
     * @return: FullScreenWindow 
     */
    public Window getFullScreenWindow()
    {
        return vc.getFullScreenWindow();
    }
    
    /**
     * @return: Width 
     */
    public int getWidth ()
    {
        Window w = vc.getFullScreenWindow();
        if (w!=null)
        {
            return w.getWidth();
        }
        else 
        {
            return 0;
        }
    }
    
    /**
     * @return: Height
     */
    public int getHeight ()
    {
        Window w = vc.getFullScreenWindow();
        if (w!=null)
        {
            return w.getHeight();
        }
        else 
        {
            return 0;
        }
    }
    
    /**
     * Restores the screen at the end of the program.
     */
    public void restoreScreen ()
    {
        Window w = vc.getFullScreenWindow();
        if (w!=null){
            w.dispose();
        }
        vc.setFullScreenWindow(null);
    }
    
    /**
     * @param w: width
     * @param h: height
     * @param t: 
     * @return : CompatibleImage
     */
    public BufferedImage createCompatibleImage(int w,int h,int t)
    {
        Window win = vc.getFullScreenWindow();
        if (win!=null)
        {
            GraphicsConfiguration gc = win.getGraphicsConfiguration();
            return (gc.createCompatibleImage(w, h, t));
        }
        return null;
    }
}

