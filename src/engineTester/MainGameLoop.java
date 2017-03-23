package engineTester;

import Shaders.StaticShader;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Rendered;

/**
 * Created by Alan on 23/03/2017.
 */

/*
    Index buffers are used to prder the manner in which vertices are drawn, to reduce vertex data redundancy.
    Remember to go counter clockwise
 */
public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Rendered rendered = new Rendered();
        StaticShader staticShader = new StaticShader();


        float[] vertices = {
                //Left bottom triangle
                -0.5f,0.5f,0,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,0.5f,0
        };

        int[] indices = {
                0,1,3, //Top triangle
                3,1,2  //Bottom triangle
        };

        RawModel rawModel = loader.loadToVAO(vertices,indices);


        while (!Display.isCloseRequested()) {
            rendered.prepare();
            staticShader.start();
            //game logic
            //render
            rendered.render(rawModel);
            staticShader.stop();
            DisplayManager.updateDisplay();
        }
        staticShader.cleanUp();
        loader.cleanUp();
        DisplayManager.exitDisplay();
    }
}
