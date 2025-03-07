package shaders;

import entities.Camera;
import entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{
    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColor;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    protected void getAllUniformLocations(){
       location_transformationMatrix = super.getUniformLocation("transformationMatrix");
       location_projectionMatrix = super.getUniformLocation("projectionMatrix");
       location_viewMatrix = super.getUniformLocation("viewMatrix");
       location_lightPosition = super.getUniformLocation("lightPosition");
       location_lightColor = super.getUniformLocation("lightColor");
       location_shineDamper = super.getUniformLocation("shineDamper");
       location_reflectivity = super.getUniformLocation("reflectivity");
       location_useFakeLighting = super.getUniformLocation("useFakeLighting");
       location_skyColor = super.getUniformLocation("skyColor");
    }

    public void loadSkyColor(float r, float g, float b) {
        super.loadVector(location_skyColor,new Vector3f(r,g,b));
    }

    public void loadFakeLightingVariable(boolean useFake) {
        super.loadBoolean(location_useFakeLighting,useFake);
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }
    //Takes location of the Transformation Matrix and the Matrix to be loaded into it
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    //Takes location of the Projection Matrix and the Matrix to be loaded into it
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    //Takes location of the View Matrix and the Matrix to be loaded into it
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    //Load the light variables
    public void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }
}
