/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections between
 *
 */
public class MapGraph {
	// TODO: Make it final or return a copy instead:
	//	getVertices
	//    	return adjacencyList.keySet();
	//	getOutNeighborsFromVertex
	//		adjacencyList.get(vertex)
	//	getInNeighborsFromVertex
	//		inNeighbors carries references to the GeographicPoints of adjacencyList.keySet() 
	
	private Map<GeographicPoint, ArrayList<GeographicPoint>> adjacencyList;
	private int numberOfVertices;
	private int numberOfEdges;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		adjacencyList = new HashMap<GeographicPoint, ArrayList<GeographicPoint>>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return numberOfVertices;
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		return adjacencyList.keySet();
	}

	public List<GeographicPoint> getOutNeighborsFromVertex(GeographicPoint vertex) {
		return adjacencyList.get(vertex) != null ? adjacencyList.get(vertex) : new ArrayList<GeographicPoint>();
	}

	public List<GeographicPoint> getInNeighborsFromVertex(GeographicPoint vertex) {
		List<GeographicPoint> inNeighbors = new ArrayList<GeographicPoint>();

		for (GeographicPoint possibleInNeighbor : adjacencyList.keySet()) {
			for (GeographicPoint possibleOutNeighborOfInNeighbor : adjacencyList.get(possibleInNeighbor)) {
				if (possibleOutNeighborOfInNeighbor.equals(vertex)) {
					inNeighbors.add(possibleInNeighbor);
				}
			}
		}

		return inNeighbors;
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return numberOfEdges;
	}

	/**
	 * Add a node corresponding to an intersection at a Geographic Point If the
	 * location is already in the graph or null, this method does not change the
	 * graph.
	 * 
	 * @param location
	 *            The location of the intersection
	 * @return true if a node was added, false if it was not (the node was
	 *         already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {

		if (location != null && !adjacencyList.containsKey(location)) {
			adjacencyList.put(location, new ArrayList<GeographicPoint>());
			numberOfVertices++;
			return true;
		}

		return false;
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
	 * GeographicPoints have already been added to the graph
	 * 
	 * @param from
	 *            The starting point of the edge
	 * @param to
	 *            The ending point of the edge
	 * @param roadName
	 *            The name of the road
	 * @param roadType
	 *            The type of the road
	 * @param length
	 *            The length of the road, in km
	 * @throws IllegalArgumentException
	 *             If the points have not already been added as nodes to the
	 *             graph, if any of the arguments is null, or if the length is
	 *             less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length)
			throws IllegalArgumentException {

		if (from == null || to == null || roadName == null || roadType == null || length < 0
				|| !getVertices().contains(from) || !getVertices().contains(to)) {
			throw new IllegalArgumentException();
		}

		if (!getOutNeighborsFromVertex(from).contains(to)) {
			// RoadSegment edge = new RoadSegment(from, to, null, roadName,
			// roadType, length);
			adjacencyList.get(from).add(to);
			numberOfEdges++;
		}
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *         path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return bfs(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *         path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {


		if (getVertices().containsAll(Arrays.asList(start, goal))) {
			Queue<GeographicPoint> whereToNext = new LinkedList<GeographicPoint>();
			HashSet<GeographicPoint> visited = new HashSet<>();
			HashMap<GeographicPoint, GeographicPoint> parent = new HashMap<>();

			whereToNext.add(start);
			visited.add(start);

			while (!whereToNext.isEmpty()) {
				GeographicPoint currentNode = whereToNext.poll();
				nodeSearched.accept(currentNode);
				
				if (currentNode.equals(goal)) {
					ArrayList<GeographicPoint> shortestPath = new ArrayList<>();
					GeographicPoint nextHop = parent.get(goal);
					shortestPath.add(goal);
					
					while(nextHop != null) {
						shortestPath.add(nextHop);
						nextHop = parent.get(nextHop);
					}
					
					Collections.reverse(shortestPath);
					
					return shortestPath;
				}

				for (GeographicPoint outNeighbor : getOutNeighborsFromVertex(currentNode)) {
					if (!visited.contains(outNeighbor)) {
						visited.add(outNeighbor);
						parent.put(outNeighbor, currentNode);
						whereToNext.add(outNeighbor);
					}
				}
			}

		}

		return null;
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return dijkstra(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3

		// Hook for visualization. See writeup.
		// nodeSearched.accept(next.getLocation());

		return null;
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return aStarSearch(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3

		// Hook for visualization. See writeup.
		// nodeSearched.accept(next.getLocation());

		return null;
	}

	public static void main(String[] args) {
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("src/main/resources/data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");

		// You can use this method for testing.

		/*
		 * Use this code in Week 3 End of Week Quiz MapGraph theMap = new
		 * MapGraph(); System.out.print("DONE. \nLoading the map...");
		 * GraphLoader.loadRoadMap("src/main/resources/data/maps/utc.map", theMap);
		 * System.out.println("DONE.");
		 * 
		 * GeographicPoint start = new GeographicPoint(32.8648772,
		 * -117.2254046); GeographicPoint end = new GeographicPoint(32.8660691,
		 * -117.217393);
		 * 
		 * 
		 * List<GeographicPoint> route = theMap.dijkstra(start,end);
		 * List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		 * 
		 */

	}

}
