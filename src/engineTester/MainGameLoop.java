package engineTester;

import entities.Camera;
import entities.Entity;
import org.lwjgl.util.vector.Vector3f;
import shaders.StaticShader;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Rendered;
import textures.ModelTexture;

/*
    Index buffers are used to order the manner in which vertices are drawn, to reduce vertex data redundancy.
    Remember to go counter clockwise
 */
public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader staticShader = new StaticShader();
        Rendered rendered = new Rendered(staticShader);


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

        float[] textureCoords = {
            0,0, //V0
            0,1, //V1
            1,1, //V2
            1,0  //V3
        };

        RawModel rawModel = loader.loadToVAO(vertices,indices,textureCoords);
        ModelTexture modelTexture = new ModelTexture(loader.loadTexture("rocktexture"));
        TexturedModel texturedModel = new TexturedModel(rawModel,modelTexture);
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-5.0f),0,0,0,1);
        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
            //entity.increasePosition(0,0,0);
            entity.increaseRotation(0,0,0);
            camera.move();
            rendered.prepare();
            staticShader.start();
            staticShader.loadViewMatrix(camera);
            rendered.render(entity,staticShader);
            staticShader.stop();
            DisplayManager.updateDisplay();
        }
        staticShader.cleanUp();
        loader.cleanUp();
        DisplayManager.exitDisplay();
    }
}
