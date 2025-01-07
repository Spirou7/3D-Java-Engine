import java.util.ArrayList;
public class Vertex {
    public Vector3 position;
    public ArrayList<Vertex> connections;

    public Vertex(Vector3 position){
        this.position = new Vector3(position);
        connections = new ArrayList<Vertex>();
    }

    public void addConnection(Vertex v, boolean connectBack){
        connections.add(v);
        if(connectBack){
            v.addConnection(this, false);
        }
    }
}
