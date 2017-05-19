/**
 * Created by Sankalpa 
 * student ID - 2015318
 *  on 4/02/2017.
 */

import java.awt.*;
import java.util.*;

public class PathFindingOnSquaredGrid {

    Node start;
    Node end;
    Node[][] gridArea;

    
    
    // horzontal and vertical distance
    double H_V_Distance = 1.0;

    // Diagonal Distance
    //Manhattan values.
    public static double Manhattan() {

        double dDistance = 2;
        return dDistance;
    }

    //Euclidean values.
    public static double Euclidean() {
        double dDistance = 1.4;
        return dDistance;
    }

    //Chebyshev values

    public static double Chebyshev() {
        double dDistance = 1;
        return dDistance;
    }

    
    
    
    //starti statrt row value
    //startj start coloum value
    //endi end row value
    // endj end coloum value
    ArrayList<Node> distance(boolean[][] matrix, int starti, int startj, int endi, int endj,double dDistance,String name) {

        int size = matrix.length;
       


        start = new Node(starti, startj); // statrt point
        end = new Node(endi, endj); //end point
        
        // nodes are stored in following grid
        gridArea = new Node[size][size];
        
        // Creating nodes and finding blocked cells in matrix and mapping accordingly to our grid
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                gridArea[i][j] = new Node(i, j);
                if (matrix[i][j] == false) {

                    gridArea[i][j].blocked = true;


                }
            }
        }

        
        // All nodes distance are set to infinity at begining
        
        start.distance =0;// set start distance to zero

        
        // a comparator object to deal with Priority Queue
        Comparator<Node> adjacencyComparator = (left, right) -> {
            if (left.distance > (right.distance)) {
                return 1;

            }
            return -1;
        };

        // Queue to store visiting nodes
        Queue<Node> queueVisit = new PriorityQueue(size, adjacencyComparator);

        queueVisit.add(start);

        while (queueVisit.size() > 0) {
            Node visit = queueVisit.remove();
            Node data;

            // Top
            if (visit.x - 1 >= 0) {

                // Top Top cell
            	data = gridArea[visit.x - 1][visit.y];
                
               
                if (!data.visited && !data.blocked && data.distance > visit.distance + H_V_Distance) {
                	data.distance = visit.distance + H_V_Distance;
                	data.parent = visit;
                    queueVisit.add(data);
                }

                // Top Left cell
                if (visit.y - 1 > 0) {
                	data = gridArea[visit.x - 1][visit.y - 1];
                    if (!data.visited && !data.blocked && data.distance > visit.distance + dDistance) {
                    	data.distance = visit.distance + dDistance;
                    	data.parent = visit;
                        queueVisit.add(data);
                    }
                }

                // Top Right cell
                if (visit.y + 1 < size) {
                	data = gridArea[visit.x - 1][visit.y + 1];
                    if (!data.visited && !data.blocked && data.distance > visit.distance + dDistance) {
                    	data.distance = visit.distance + dDistance;
                    	data.parent = visit;
                        queueVisit.add(data);
                    }
                }
            }

            // Left cell
            if (visit.y - 1 > 0) {
            	data = gridArea[visit.x][visit.y - 1];
                if (!data.visited && !data.blocked && data.distance > visit.distance + H_V_Distance) {
                	data.distance = visit.distance + H_V_Distance;
                	data.parent = visit;
                    queueVisit.add(data);
                }
            }

            // Right cell
            if (visit.y + 1 < size) {
            	data = gridArea[visit.x][visit.y + 1];
                if (!data.visited && !data.blocked && data.distance > visit.distance + H_V_Distance) {
                	data.distance = visit.distance + H_V_Distance;
                	data.parent = visit;
                    queueVisit.add(data);
                }
            }
            
            // Down 
            if (visit.x + 1 < size) {

                // Down Down cell
            	data = gridArea[visit.x + 1][visit.y];
                if (!data.visited && !data.blocked && data.distance > visit.distance + H_V_Distance) {
                	data.distance = visit.distance + H_V_Distance;
                	data.parent = visit;
                    queueVisit.add(data);
                }

                // Down Left cell
                if (visit.y - 1 >= 0) {
                	data = gridArea[visit.x + 1][visit.y - 1];
                    if (!data.visited && !data.blocked && data.distance > visit.distance + dDistance) {
                    	data.distance = visit.distance + dDistance;
                    	data.parent = visit;
                        queueVisit.add(data);
                    }
                }

                // Down Right cell
                if (visit.y + 1 < size) {
                	data = gridArea[visit.x + 1][visit.y + 1];
                    if (!data.visited && !data.blocked && data.distance > visit.distance + dDistance) {
                    	data.distance = visit.distance + dDistance;
                    	data.parent = visit;
                        queueVisit.add(data);
                    }
                }
            }
            visit.visited = true;
        }

        ArrayList<Node> path = new ArrayList<>();

        // Checking there is a path exists
        if (!(gridArea[end.x][end.y].distance == Integer.MAX_VALUE)) {
            //Trace back the path
            Node current = gridArea[end.x][end.y];
            System.out.println(name+":"+current.distance);
            while (current.parent != null) {
                path.add(current.parent);

                current = current.parent;
            }
        } else System.out.println("No possible path");


        return path;
    }


    class Node {
        int x;
        int y;
        double distance = Integer.MAX_VALUE;
        Node parent = null;
        boolean visited;
        boolean blocked;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2,ArrayList<Node> path) {
        int N = a.length;
        int s=path.size();
        int count=0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.setPenColor(Color.BLUE);
                        StdDraw.circle(j, N - i - 1, .5);

                    }


        for (PathFindingOnSquaredGrid.Node node : path) {
            if(s-count==1){
                return;
            }
            count++;

                StdDraw.setPenColor(Color.RED);

            StdDraw.circle(node.y,  N- node.x - 1, .5);
            //path.remove(node.y);

        }
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

    public static void main(String[] args){
        // boolean[][] open = StdArrayIO.readBoolean2D();

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(10, 0.4);

        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        // Reading the coordinates for points A and B on the input squared grid.

        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Start the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis
        Stopwatch timerFlow = new Stopwatch();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();

        ArrayList<PathFindingOnSquaredGrid.Node> path1 = new PathFindingOnSquaredGrid().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Manhattan(),"Manhattan");
        
        double time=timerFlow.elapsedTime();
        //System.out.println("Miliseconds taken - "+timerFlow.elapsedTime());
        ArrayList<PathFindingOnSquaredGrid.Node> path2 = new PathFindingOnSquaredGrid().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Euclidean(),"Euclidean");
        ArrayList<PathFindingOnSquaredGrid.Node> path3= new PathFindingOnSquaredGrid().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Chebyshev(),"Chebyshev");

        System.out.println("seconds taken - "+(time/1000));
        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path2);
       // show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path2);
       // show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path3);

    }
}
