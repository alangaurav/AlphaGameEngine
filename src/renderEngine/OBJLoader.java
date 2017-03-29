package renderEngine;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 29/03/2017.
 */

/*
    This class is meant to parse the OBJ files and extract the vertex, normal and texture data
 */
public class OBJLoader {
    public static RawModel loadObjModel(String filename, Loader loader) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("res/" + filename + ".obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray = null;
        float[] texturesArray = null;
        float[] normalsArray = null;
        int[] indicesArray = null;
        //Parsing based on OBJ format
        try {
            while(true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if(line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if(line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if(line.startsWith("f ")) {
                    texturesArray = new float[vertices.size()*2];
                    normalsArray = new float[vertices.size()*3];
                    break;
                }
            }

            while (line!=null) {
                if(!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }

                //Current triangle processing

                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1,indices,textures,normals,texturesArray,normalsArray);
                processVertex(vertex2,indices,textures,normals,texturesArray,normalsArray);
                processVertex(vertex3,indices,textures,normals,texturesArray,normalsArray);

                line = reader.readLine();
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        //Convert lists to arrays

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for (int i=0;i<indices.size();i++) {
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray,indicesArray,texturesArray,normalsArray);

    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals,float[] texturesArray, float[] normalsArray) {
        //OBJ index starts from 1
        int currentVertexPointer = Integer.parseInt(vertexData[0])-1;
        indices.add(currentVertexPointer);
        //Texture index is at the second bit of the data
        Vector2f currentTexturePointer = textures.get(Integer.parseInt(vertexData[1])-1);
        //Mapping textures to vertices
        texturesArray[currentVertexPointer*2] = currentTexturePointer.x;
        //OpenGL starts from top left corner
        texturesArray[currentVertexPointer*2+1] = 1-currentTexturePointer.y;
        //Getting the normal
        Vector3f currentNormalPointer = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*3] = currentNormalPointer.x;
        normalsArray[currentVertexPointer*3+1] = currentNormalPointer.y;
        normalsArray[currentVertexPointer*3+2] = currentNormalPointer.z;
    }
}
