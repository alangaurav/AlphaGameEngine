package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    Index buffers are used to order the manner in which vertices are drawn, to reduce vertex data redundancy.
    Remember to go counter clockwise
 */

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        ModelData data = OBJFileLoader.loadOBJ("tree");

        RawModel model = loader.loadToVAO(data.getVertices(),data.getIndices(),data.getTextureCoords(),data.getNormals());

        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel",loader),new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel",loader),new ModelTexture(loader.loadTexture("flower")));
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern",loader),new ModelTexture(loader.loadTexture("fern")));
        TexturedModel lowPolyTree = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree",loader),new ModelTexture(loader.loadTexture("lowPolyTree")));

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<1000;i++){
            entities.add(new Entity(lowPolyTree, new Vector3f(random.nextFloat()*800,0,random.nextFloat() * 800),0,0,0,0.4f));
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800,0,random.nextFloat() * 800),0,0,0,3));
            grass.getModelTexture().setHasTransparency(true);
            grass.getModelTexture().setUseFakeLighting(true);
            entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800,0,random.nextFloat() * 800),0,0,0,1));
            fern.getModelTexture().setHasTransparency(true);
            entities.add(new Entity(fern, new Vector3f(random.nextFloat()*800,0,random.nextFloat() * 800),0,0,0,0.6f));
            flower.getModelTexture().setHasTransparency(true);
            flower.getModelTexture().setUseFakeLighting(true);
            entities.add(new Entity(flower, new Vector3f(random.nextFloat()*800,0,random.nextFloat() * 800),0,0,0,1));

        }

        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0,0, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(1,0, loader, texturePack, blendMap);

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        while(!Display.isCloseRequested()){
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            for(Entity entity:entities){
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.exitDisplay();
    }
}
