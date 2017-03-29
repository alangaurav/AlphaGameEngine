package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.OBJLoader;
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

        RawModel rawModel = OBJLoader.loadObjModel("stall",loader);
        ModelTexture modelTexture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel texturedModel = new TexturedModel(rawModel,modelTexture);
        Entity entity = new Entity(texturedModel, new Vector3f(0,-5.0f,-30.0f),0,0,0,1);
        Camera camera = new Camera();
        Light light = new Light(new Vector3f(-10.0f,10.0f,0), new Vector3f(1,1,1));


        while (!Display.isCloseRequested()) {
            //entity.increasePosition(0,0,0);
            entity.increaseRotation(0,0.5f,0);
            camera.move();
            rendered.prepare();
            staticShader.start();
            staticShader.loadLight(light);
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
