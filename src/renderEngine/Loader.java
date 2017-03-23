package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 23/03/2017.
 */

//Class to load 3d data onto the memeory by storing this data in a VAO
public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();


    public RawModel loadToVAO(float[] positions, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,positions);
        unbindVAO();
        return new RawModel(vaoID,indices.length);
    }

    public void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
    }

    private int createVAO() {
        //Creates empty VAO and returns ID of that VAO
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        //Activate VAO by binding
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList (int attributeNumber, float[] data) {
        //Create a VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        //Bind the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        //Store data in VBO and the last argument is for read/write settings
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);
        //Move VBO to VAO attribute list
        //attribute number, length of vectors, type of vector, normalized, distance between vertices, offset
        GL20.glVertexAttribPointer(attributeNumber,3, GL11.GL_FLOAT, false, 0, 0);
        //Unbind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }

    public void unbindVAO () {
        //To unbind current VAO
        GL30.glBindVertexArray(0);
        cleanUp();
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        //FLip from reading to writing mode
        buffer.flip();
        return buffer;
    }

    //Bind indices to VAO
    private void bindIndicesBuffer(int[] indices) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,vboId);
        IntBuffer intBuffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER,intBuffer,GL15.GL_STATIC_DRAW);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        //Flip from reading to writing mode of the buffer
        buffer.flip();
        return buffer;
    }
}
