package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

/**
 * Created by Alan on 23/03/2017.
 */
public class DisplayManager {

    private static final int WIDTH = 1280;
    private static final int HEIGHT  = 768;
    private static final int FPS_CAP = 120;

    public static void createDisplay() {

        ContextAttribs contextAttribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            //Set up height and width of display window
            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
            Display.create(new PixelFormat(),contextAttribs);
            //Set title
            Display.setTitle("GameEngine");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        //Set viewport to screen size in pixels
        GL11.glViewport(0,0,WIDTH,HEIGHT);
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    public static void exitDisplay() {
        Display.destroy();
    }

}
