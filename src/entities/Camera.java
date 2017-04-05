package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

//Virtual camera simulation
public class Camera {
    private Vector3f position  = new Vector3f(0,5,0);
    private float pitch = 10; //The high or low viewing angle of the camera
    private float yaw = 180; //The left or right viewing angle of the camera
    private float roll; // Tilt of the camera to a specific side
    private float speed;

    public Camera() {
        this.speed = 0.5f;
    }

    //Method to move the camera around
    public void move() {

        yaw = -(Display.getWidth() - Mouse.getX() / 2);
        pitch = (Display.getHeight() / 2) - Mouse.getY();

        if (pitch >= 90)
        {

            pitch = 90;

        }
        else if (pitch <= -90)
        {

            pitch = -90;

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {

            position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
            position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;

        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
            position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;


        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D))
        {

            position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
            position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;

        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_A))
        {

            position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
            position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;

        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=0.2f;
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
