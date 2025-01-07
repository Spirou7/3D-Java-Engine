import java.util.ArrayList;
import java.awt.Color;

public class Geometry {
    public Vector3 position;
    public Vector3 rotation;
    public Color color;

    public ArrayList<Vertex> vertices;

    public Geometry(Vector3 p) {
        position = new Vector3(p);
        vertices = new ArrayList<Vertex>();
    }

    // Move in a circle around an axis
    public void Rotate(Vector3 angle){
        for(int i = 0; i < vertices.size(); i++){
            Vector3 diff = vertices.get(i).position;
            double distance = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
            distance = Math.sqrt(diff.y * diff.y + distance * distance);
            
            Vector3 currentRot = Vector3.angleFromVector(diff);
            currentRot = Vector3.addVectors(angle, currentRot);
            
            if(currentRot.y < -Math.PI / 2 || currentRot.y > Math.PI * 3 / 2){
                int bruh = 0;
            }
            Vector3 newPosition = Vector3.multiplyVector(Vector3.vectorFromAngle(currentRot), distance);
            vertices.get(i).position = newPosition;
        }
    }

    public static Geometry CreateRect(double x, double y, double z, double width, double height, double depth) {

        Vertex v000 = new Vertex(new Vector3(-width / 2, -height / 2, -depth / 2));
        Vertex v001 = new Vertex(new Vector3(-width / 2, -height / 2, depth / 2));
        Vertex v010 = new Vertex(new Vector3(-width / 2, height / 2, -depth / 2));
        Vertex v011 = new Vertex(new Vector3(-width / 2, height / 2, depth / 2));
        Vertex v100 = new Vertex(new Vector3(width / 2, -height / 2, -depth / 2));
        Vertex v101 = new Vertex(new Vector3(width / 2, -height / 2, depth / 2));
        Vertex v110 = new Vertex(new Vector3(width / 2, height / 2, -depth / 2));
        Vertex v111 = new Vertex(new Vector3(width / 2, height / 2, depth / 2));

        v000.addConnection(v001, true);
        v000.addConnection(v100, true);
        v000.addConnection(v010, true);

        v001.addConnection(v011, true);
        v001.addConnection(v101, true);

        v101.addConnection(v111, true);
        v101.addConnection(v100, true);

        v100.addConnection(v110, true);

        v010.addConnection(v011, true);
        v010.addConnection(v110, true);

        v011.addConnection(v111, true);

        v111.addConnection(v110, true);

        Geometry g = new Geometry(new Vector3(x, y, z));

        g.vertices.add(v000);
        g.vertices.add(v001);
        g.vertices.add(v010);
        g.vertices.add(v011);
        g.vertices.add(v100);
        g.vertices.add(v101);
        g.vertices.add(v110);
        g.vertices.add(v111);

        Color color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        g.color = color;

        return g;
    }

    public static Geometry CreatePyramid(double x, double y, double z, double width, double height, double depth) {
        Vertex v00 = new Vertex(new Vector3(-width / 2, -height / 2, -depth / 2));
        Vertex v01 = new Vertex(new Vector3(-width / 2, -height / 2, depth / 2));
        Vertex v10 = new Vertex(new Vector3(width / 2, -height / 2, -depth / 2));
        Vertex v11 = new Vertex(new Vector3(width / 2, -height / 2, depth / 2));
        Vertex point = new Vertex(new Vector3(0, height / 2, 0));

        v00.addConnection(v01, true);
        v00.addConnection(v10, true);
        
        v10.addConnection(v11, true);
        v01.addConnection(v11, true);

        point.addConnection(v00, true);
        point.addConnection(v01, true);
        point.addConnection(v10, true);
        point.addConnection(v11, true);

        Geometry g = new Geometry(new Vector3(x, y, z));
        g.vertices.add(v00);
        g.vertices.add(v01);
        
        g.vertices.add(v10);
        g.vertices.add(v11);
        g.vertices.add(point);

        Color color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        g.color = color;

        return g;
    }
}
