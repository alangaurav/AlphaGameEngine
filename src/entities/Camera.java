package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Alan on 26/03/2017.
 */

//Virtual camera simulation
public class Camera {
    private Vector3f position  = new Vector3f(0,0,0);
    private float pitch; //The high or low viewing anfle of the camera
    private float yaw; //The left or right viewing angle of the camera
    private float roll; // Tilt of the camera to a specific side

    public Camera() {

    }

    //Method to move the camera around
    public void move() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z-=0.002f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z+=0.002f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x-=0.002f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x+=0.002f;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
