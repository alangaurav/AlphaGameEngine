package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;

/*
    Index buffers are used to order the manner in which vertices are drawn, to reduce vertex data redundancy.
    Remember to go counter clockwise
 */
public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel rawModel = OBJLoader.loadObjModel("tree",loader);
        TexturedModel texturedModel = new TexturedModel(rawModel,  new ModelTexture(loader.loadTexture("tree")));
        ModelTexture texture = texturedModel.getModelTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);


        Entity entity = new Entity(texturedModel, new Vector3f(0,-5.0f,-30.0f),0,0,0,1);
        Camera camera = new Camera();
        Light light = new Light(new Vector3f(100.0f,10.0f,0), new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));



        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
            //entity.increasePosition(0,0,0);
            //entity.increaseRotation(0,0.5f,0);
            camera.move();
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            //renderer.processEntity(entity);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.exitDisplay();
    }
}
