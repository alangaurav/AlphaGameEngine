package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

    //Function which takes the translation, rotation and scale values to create a transformation matrix
    public static Matrix4f createTransformationMatrix(Vector3f translation,float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f(); // Create a 4 X 4 matrix
        matrix.setIdentity(); //Set it as the identity matrix
        Matrix4f.translate(translation, matrix, matrix); // For applying the translation
        Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix); //Applying rotation in x
        Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix); //Applying rotation in y
        Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix); //Applying rotation in z
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix); //Applying Scale
        return matrix;
    }
}
