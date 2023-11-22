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
        HashSet<Point> closedList = new HashSet<>();
        Node currNode = new Node(null, 0, 0, 0, start);
        openList.add(currNode);

        while(!(withinReach.test(currNode.getLoc(), end) && openList.isEmpty())){
            List<Point> neighbors = potentialNeighbors.apply(currNode.getLoc())
                    .filter(point -> !closedList.contains(point)).filter(canPassThrough).toList();
            for(Point neighbor: neighbors){
                int g = currNode.getG() + 1;
                int h = neighbor.manDist(end);
                int f = g + h;
                openList.add(new Node(currNode, g, h, f, neighbor));
            }
            closedList.add(currNode.getLoc());

    }



        return path;
    }
}
