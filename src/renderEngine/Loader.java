package renderEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

//Class to load 3d data onto the memory by storing this data in a VAO
public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();


    public RawModel loadToVAO(float[] positions, int[] indices, float[] texture) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,3,positions);
        storeDataInAttributeList(1,2,texture);
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
        for(int texture : textures) {
            GL11.glDeleteTextures(texture);
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

    private void storeDataInAttributeList (int attributeNumber, int coordinates, float[] data) {
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
        GL20.glVertexAttribPointer(attributeNumber,coordinates, GL11.GL_FLOAT, false, 0, 0);
        //Unbind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }

    public void unbindVAO () {
        //To unbind current VAO
        GL30.glBindVertexArray(0);
        //For some reason using clean up here messes up everything, I think it's because it clears all values.
        //cleanUp();
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

    /*  Finally adding some texture
        This function takes in a png and returns an id of that texture
     */
    public int loadTexture(String filename) {
        //Texture is always a png
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+filename+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int textureId = texture.getTextureID();
        textures.add(textureId);
        return textureId;
    }
}
