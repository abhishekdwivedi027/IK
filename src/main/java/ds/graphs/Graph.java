package ds.graphs;

import java.util.*;

public class Graph {

    /*

        Tree +

        1. connected/disconnected graph - there may be a few disconnected nodes
        2. directed/undirected graph - unidirectional or bidirectional
        3. cyclic/acyclic graph - there may be one or more cycles
        4. edge weight

        network of nodes/vertices and edges
        G = (V, E)

        We can represent Graph as Tree using a node object - node with val and list of neighbor nodes.
        But we will represent Graph as an array/array list of linked list - adjacencyList
        where array index represents the identifier of a vertex and its neighbors are stored in linked list

        Remember: array index represents the identifier of a vertex - not true for String vertices
        In that case we would need hashtable of linked list (and we still call it adjacencyList because the list denotes linked list)
        Also, we would need visited table instead of visited array

        undirected graph:
        degree(v) = num of edges from/to the vertex
        #degrees = 2 * edges

        undirected graph:
        out/in degree(v) = num of edges from/to the vertex
        #in-degrees = #out-degrees = edges

        connected graph:
        Eulerian Cycle => degree of every vertex must be even
        every vertex has even degree => must be Eulerian Cycle

        Eulerian Path => degree of every vertex must be even except start and end vertices
        impossible to have odd num of vertices with odd degrees (#degrees = 2 * edges)

        Implementation

        1. edge list
        2. adjacency list - array (list) of linked list
        3. adjacency matrix
        4. adjacency map

        Problem Solving Steps -

        1. build Graph
        2. write bfs/dfs function
        3. traverse Graph
     */

    private final List<List<Integer>> adjacencyList = new ArrayList<>();

    // used to prevent circular traversal
    // to track visited nodes
    private int[] visited;

    // used to find cycles in graph
    // to see if visited neighbor is parent or not
    private int[] parents;

    // used to determine levels in BFS
    // distance from src node
    // to find if cross edge is at same level (odd length cycle) or adjacent level (even length cycle)
    private int[] distances;

    private boolean isConnected = true;

    private boolean hasCycle = false;

    // Step 1: construct a Graph
    // given list of edges (relationships) build a graph - adjacency list/map
    public void buildGraph(int n, List<Integer[]> edgeList) {
        for (int i=0; i<n; i++) {
            adjacencyList.add(i, new LinkedList<>());
        }

        for (Integer[] edge: edgeList) {
            addEdge(edge[0], edge[1]);
        }

        visited = new int[adjacencyList.size()];
        Arrays.fill(visited, -1);

        parents = new int[adjacencyList.size()];
        Arrays.fill(parents, -1);

        distances = new int[adjacencyList.size()];
        Arrays.fill(distances, -1);
    }

    private void addEdge(int from, int to) {
        addEdge(from, to, false);
    }

    private void addEdge(int from, int to, boolean isDirected) {
        adjacencyList.get(from).add(to);
        if (!isDirected) {
            adjacencyList.get(to).add(from);
        }
    }

    // Step 2
    // trees edges and cross edges
    // cross edges: use for cycle detection
    public void bfs(int src) {
        visited[src] = 1;
        Queue<Integer> q = new LinkedList<>();
        q.add(src);

        while(!q.isEmpty()) {
            int node = q.poll();
            List<Integer> neighbors = adjacencyList.get(node);
            for (Integer neighbor: neighbors) {
                if (visited[neighbor] != -1) { // tree edge
                    visited[neighbor] = 1;
                    parents[neighbor] = node;
                    q.add(neighbor);
                } else {
                    // this neighbor is already visited
                    // if this visited neighbor is not parent of node
                    // then there must be a cross edge => cycle
                    if (neighbor != parents[node]) { // cross edge => cycle
                        hasCycle = true;
                    }
                }
            }
        }
    }

    // Step 2
    // trees edges and back edges
    /*
        visited[node] = 1;
        List<Integer> neighbors = adjacencyList.get(node);
        for (Integer neighbor: neighbors) {
            if (visited[neighbor] != -1) { // trees edge
                dfs(neighbor);
            }
        }

    */
    public void dfs(int node) {
        visited[node] = 1;
        List<Integer> neighbors = adjacencyList.get(node);
        for (Integer neighbor: neighbors) {
            if (visited[neighbor] != -1) { // trees edge
                parents[neighbor] = node;
                dfs(neighbor);
            } else {
                // this neighbor is already visited
                // if this visited neighbor is not parent of node
                // then there must be a back edge => cycle
                if (neighbor != parents[node]) { // back edge => cycle
                    hasCycle = true;
                }
            }
        }
    }

    // Step 3
    /*
     for (int i=0; i< visited.length; i++) {
            if (visited[i] == -1) {
                dfs(i);
            }
        }
     */
    public void traverseGraph() {
        int components = 0;
        for (int i=0; i< visited.length; i++) {
            if (visited[i] == -1) {
                components++;
                if (components > 1) {
                    isConnected = false;
                }
                // bfs(i);
                dfs(i);
            }
        }
    }

    // Eulerian Cycle => no vertex with odd degree
    public boolean hasEulerianCycle() {

        // # odd degree == 0
        boolean hasEulerianCycle = false;
        if (isConnected) {
            hasEulerianCycle = adjacencyList.stream().noneMatch(neighbors -> neighbors.size() % 2 == 1);
        }
        return hasEulerianCycle;
    }

    // Eulerian Path => zero or two vertices with odd degree
    public boolean hasEulerianPath() {

        // Eulerian Cycle or # odd degree == 2
        boolean hasEulerianPath = false;
        if (isConnected) {
            hasEulerianPath = hasEulerianCycle()
                    || adjacencyList.stream().filter(neighbors -> neighbors.size() % 2 == 1).count() == 2L;
        }
        return hasEulerianPath;
    }

    // can the graph be divided in two teams? if yes then it's bipartite
    // if A and B are connected by an edge, they cannot be in same team
    // cycle with even number of vertices => bipartite
    // two conditions - 1. cycle 2. with even number of vertices
    // even length cycle => BFS cross edge - adjacent layer
    // we would need to track layer, or distance from source
    public boolean isBipartite() {
        // by default - every tree is a bipartite
        boolean isBipartite = true;
        for (int i=0; i< visited.length; i++) {
            if (!isBipartiteHelper(i)) {
                isBipartite = false;
                break;
            }
        }
        return isBipartite;
    }

    // for DFS it's similar
    // let's assign a property - say color - to every node
    // because distance/level makes no sense in DFS
    // if back edge is present then we check
    // if it's between same colors then - isBipartite is false
    private boolean isBipartiteHelper(int src) {
        boolean isBipartite = true;

        Queue<Integer> q = new LinkedList<>();
        visited[src] = 1;
        distances[src] = 0;
        q.add(src);

        while(!q.isEmpty()) {
            int node = q.poll();
            List<Integer> neighbors = adjacencyList.get(node);
            for (Integer neighbor: neighbors) {
                if (visited[neighbor] == -1) { // tree edge
                    visited[neighbor] = 1;
                    parents[neighbor] = node;
                    // distance of child is one more than that of the parent
                    distances[neighbor] = distances[node] + 1;
                    q.add(neighbor);
                } else { // neighbor already visited
                    if (neighbor != parents[node]) { // cross edge - already visited neighbor is not parent
                        hasCycle = true;
                        // if length of cycle == odd
                        // if neighbor is at same level as node
                        // then isBipartite = false
                        if (distances[neighbor] == distances[node]) {
                            isBipartite = false;
                            break;
                        }
                    }
                }
            }
        }
        return isBipartite;
    }

    public List<List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }
}
