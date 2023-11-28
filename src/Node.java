import java.util.Objects;

public class Node {

    private final Node priorNode;
    private int g;
    private final int h;
    private int f;
    private final Point loc;

    public Node(Node priorNode, int g, int h, int f, Point loc){
        this.priorNode = priorNode;
        this.g = g;
        this.h = h;
        this.f = f;
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return g == node.g && h == node.h && f == node.f && Objects.equals(priorNode, node.priorNode) && Objects.equals(loc, node.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priorNode, g, h, f, loc);
    }

    public Point getLoc(){return loc;}

    public int getG (){return g;}

    public int getH (){return h;}


    public void setG(int a){
        this.g = a;
    }

    public int getF(){return f;}

    public void setF(int a){
        this.f = a;
    }

    public Node getPrior(){return priorNode;}
}
