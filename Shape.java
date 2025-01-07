import java.util.ArrayList;

public class Shape {
    public ArrayList<Geometry> geometries;
    public Vector3 position;

    public Shape(ArrayList<Geometry> geometries){
        this.geometries = geometries;

        // get the average of the positions
        Vector3 averagePosition = new Vector3(0,0,0);

        for(int i = 0; i < geometries.size(); i++){
            averagePosition = Vector3.addVectors(averagePosition, geometries.get(i).position);
        }

        averagePosition = Vector3.multiplyVector(averagePosition, 1.0 / geometries.size());
        this.position = averagePosition;

        for(int i = 0; i < geometries.size(); i++){
            Geometry g = geometries.get(i);
            for(int j = 0; j < g.vertices.size(); j++){
                Vertex vertex = g.vertices.get(j);
                Vector3 diff = Vector3.subtractVectors(Vector3.addVectors(vertex.position, g.position), averagePosition);
                g.vertices.get(j).position = diff;
            }
            g.position = averagePosition;
        }
    }

    public void moveTo(Vector3 position){
        this.position = new Vector3(position);
        for(int i = 0; i < geometries.size(); i++){
            geometries.get(i).position = position;
        }
    }

    public void Rotate(Vector3 angle){
        for(int i = 0; i < geometries.size(); i++){
            geometries.get(i).Rotate(angle);
        }
    }
}
