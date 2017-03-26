package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Alan on 26/03/2017.
 */

//Instance of a textured model
public class Entity {
    private TexturedModel texturedModel;
    private Vector3f position;
    private float rotX,rotY,rotZ;
    private float scale;

    public Entity(TexturedModel texturedModel, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.texturedModel = texturedModel;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    //Used to move entity in the world
    public void increasePosition(float dx, float dy, float dz) {
        position.x+=dx;
        position.y+=dy;
        position.z+=dz;
    }

    //Used to rotate entity in the world
    public void increaseRotation(float rx,float ry,float rz) {
        rotX+=rx;
        rotY+=ry;
        rotZ+=rz;
    }
    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
