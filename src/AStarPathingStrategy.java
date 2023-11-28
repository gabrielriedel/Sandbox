import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        HashMap<Point, Node> openMap = new HashMap<>();
        HashSet<Point> closedList = new HashSet<>();
        int initialH = start.manDist(end);
        Node currNode = new Node(null, 0, initialH, initialH, start);
        openList.add(new Node(null, 0, initialH, initialH, start));
        openMap.put(currNode.getLoc(), currNode);


        while(!withinReach.test(currNode.getLoc(), end)){
            System.out.println("Here");
            currNode = openList.poll();
            if(currNode==null) break;
            closedList.add(currNode.getLoc());

            List<Point> neighbors = potentialNeighbors.apply(currNode.getLoc())
                    .filter(point -> !closedList.contains(point))
                    .filter(canPassThrough)
                    .toList();

            for(Point neighbor: neighbors){
                int g = currNode.getG() + 1;
                Node neighborNode = openMap.get(neighbor);
                if(openList.contains(neighborNode)){
                    if(g>=neighborNode.getG()) break;
                    else{
                        openList.remove(neighborNode);
                        neighborNode.setG(g);
                        neighborNode.setF(neighborNode.getG() + neighborNode.getH());
                        openList.add(neighborNode);
                    }
                }
                int h = neighbor.manDist(end);
                int f = g + h;
                openList.offer(new Node(currNode, g, h, f, neighbor));
                openMap.put(neighbor, new Node(currNode, g, h, f, neighbor));
            }

    }

        while(currNode != null){
            path.add(currNode.getLoc());
            currNode = currNode.getPrior();
        }
        if(!withinReach.test(path.get(0), end)){
            path.clear();
        }

        Collections.reverse(path);
        if(path.size()>0)
        path.remove(0);

        return path;

    }
}
