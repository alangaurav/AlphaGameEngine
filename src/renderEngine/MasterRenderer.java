package renderEngine;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private StaticShader shader = new StaticShader();
    private Rendered rendered = new Rendered(shader);

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();

    public void render(Light sun, Camera camera){
        rendered.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        rendered.render(entities);
        shader.stop();
        entities.clear();
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getTexturedModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }
    public void cleanUp(){
        shader.cleanUp();
    }
}
