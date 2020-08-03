package roadgraph;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import geography.GeographicPoint;
import util.GraphLoader;

public class MapGraphTest {

	private MapGraph graph;
	private GeographicPoint location0;
	private GeographicPoint location1;
	private GeographicPoint location2;
	private GeographicPoint location3;
	private GeographicPoint location4;
	private GeographicPoint location5;
	private GeographicPoint location6;
	private GeographicPoint location7;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		graph = new MapGraph();
		location0 = new GeographicPoint(0, 1);
		location1 = new GeographicPoint(1, 1);
		location2 = new GeographicPoint(2, 1);
		location3 = new GeographicPoint(3, 1);
		location4 = new GeographicPoint(4, 1);
		location5 = new GeographicPoint(5, 1);
		location6 = new GeographicPoint(6, 1);
		location7 = new GeographicPoint(7, 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getVerticesReturnsTheCorrectVertices() {
		graph.addVertex(location1);
		graph.addVertex(location2);
		graph.addVertex(location3);
		graph.addVertex(location4);
		
		Set<GeographicPoint> expectedVertices = new HashSet<>();
		expectedVertices.add(location1);
		expectedVertices.add(location2);
		expectedVertices.add(location3);
		expectedVertices.add(location4);
		
		assertThat(graph.getVertices(), is(expectedVertices));
	}

	@Test
	public void getOutNeighborsFromVertexReturnsTheCorrectNeighbors() {
		graph.addVertex(location1);
		graph.addVertex(location2);
		graph.addVertex(location3);
		graph.addVertex(location4);

		graph.addEdge(location1, location2, "", "", 1.0);
		graph.addEdge(location1, location3, "", "", 1.0);
		graph.addEdge(location1, location4, "", "", 1.0);
		graph.addEdge(location2, location1, "", "", 1.0);
		graph.addEdge(location2, location4, "", "", 1.0);

		assertTrue(graph.getOutNeighborsFromVertex(location1).contains(location2));
		assertTrue(graph.getOutNeighborsFromVertex(location1).contains(location3));
		assertTrue(graph.getOutNeighborsFromVertex(location1).contains(location4));
		assertFalse(graph.getOutNeighborsFromVertex(location1).contains(location5));
		assertTrue(graph.getOutNeighborsFromVertex(location2).contains(location1));
		assertTrue(graph.getOutNeighborsFromVertex(location2).contains(location4));
		assertFalse(graph.getOutNeighborsFromVertex(location2).contains(location3));
	}

	@Test
	public void getInNeighborsFromVertexReturnsTheCorrectNeighbors() {
		graph.addVertex(location1);
		graph.addVertex(location2);
		graph.addVertex(location3);
		graph.addVertex(location4);

		graph.addEdge(location1, location2, "", "", 1.0);
		graph.addEdge(location1, location3, "", "", 1.0);
		graph.addEdge(location1, location4, "", "", 1.0);
		graph.addEdge(location2, location1, "", "", 1.0);
		graph.addEdge(location2, location4, "", "", 1.0);

		List<GeographicPoint> expectedInNeighborsOf1 = new ArrayList<GeographicPoint>();
		List<GeographicPoint> expectedInNeighborsOf2 = new ArrayList<GeographicPoint>();
		List<GeographicPoint> expectedInNeighborsOf3 = new ArrayList<GeographicPoint>();
		List<GeographicPoint> expectedInNeighborsOf4 = new ArrayList<GeographicPoint>();
		
		expectedInNeighborsOf1.add(location2);
		expectedInNeighborsOf2.add(location1);
		expectedInNeighborsOf3.add(location1);
		expectedInNeighborsOf4.add(location1);
		expectedInNeighborsOf4.add(location2);
		
		assertThat(graph.getInNeighborsFromVertex(location1), is(expectedInNeighborsOf1));
		assertThat(graph.getInNeighborsFromVertex(location2), is(expectedInNeighborsOf2));
		assertThat(graph.getInNeighborsFromVertex(location3), is(expectedInNeighborsOf3));
		assertThat(graph.getInNeighborsFromVertex(location4), is(expectedInNeighborsOf4));
	}

	@Test
	public void addVertexUpdatesTheNumberOfVertices() {
		graph.addVertex(location1);

		assertEquals(1, graph.getNumVertices());
	}

	@Test
	public void addVertexPutsTheNewVertexIntoTheAdjacencyList() {
		assertTrue(graph.addVertex(location1));
		assertTrue(graph.getVertices().contains(location1));
	}

	@Test
	public void addVertexDoNotAddNullVertices() {
		assertFalse(graph.addVertex(null));
		assertEquals(0, graph.getNumVertices());
		assertFalse(graph.getVertices().contains(null));
	}

	@Test
	public void addVertexDoNotAddRepeatedVertices() {
		assertTrue(graph.addVertex(location1));
		assertFalse(graph.addVertex(location1));
		assertEquals(1, graph.getNumVertices());
	}

	@Test
	public void bfsManuallyBuiltMap() {
		graph.addVertex(location0);
		graph.addVertex(location1);
		graph.addVertex(location2);
		graph.addVertex(location3);
		graph.addVertex(location4);
		graph.addVertex(location5);
		graph.addVertex(location6);
		graph.addVertex(location7);

		graph.addEdge(location0, location1, "", "", 1.0);
		graph.addEdge(location0, location4, "", "", 1.0);
		graph.addEdge(location0, location5, "", "", 1.0);
		graph.addEdge(location1, location0, "", "", 1.0);
		graph.addEdge(location1, location2, "", "", 1.0);
		graph.addEdge(location1, location3, "", "", 1.0);
		graph.addEdge(location2, location1, "", "", 1.0);
		graph.addEdge(location3, location1, "", "", 1.0);
		graph.addEdge(location3, location4, "", "", 1.0);
		graph.addEdge(location4, location3, "", "", 1.0);
		graph.addEdge(location4, location0, "", "", 1.0);
		graph.addEdge(location5, location0, "", "", 1.0);
		graph.addEdge(location5, location6, "", "", 1.0);
		graph.addEdge(location5, location7, "", "", 1.0);
		graph.addEdge(location6, location5, "", "", 1.0);
		graph.addEdge(location7, location5, "", "", 1.0);
		
		List<GeographicPoint> expectedVertices = new ArrayList<>();
		expectedVertices.add(location0);
		expectedVertices.add(location5);
		expectedVertices.add(location7);
		
		assertThat(graph.bfs(location0, location7), is(expectedVertices));
	}
	
	@Test
	public void bfsMap1() {
        GraphLoader.loadRoadMap("src/main/resources/data/graders/mod2/map1.txt", graph);
		
		List<GeographicPoint> expectedVertices = new ArrayList<>();
		expectedVertices.add(new GeographicPoint(0, 0));
		expectedVertices.add(new GeographicPoint(1, 1));
		expectedVertices.add(new GeographicPoint(2, 2));
		expectedVertices.add(new GeographicPoint(3, 3));
		expectedVertices.add(new GeographicPoint(4, 4));
		expectedVertices.add(new GeographicPoint(5, 5));
		expectedVertices.add(new GeographicPoint(6, 6));

		assertThat(graph.bfs(new GeographicPoint(0, 0), new GeographicPoint(6, 6)), is(expectedVertices));
	}
	
	@Test
	public void bfsMap2() {
        GraphLoader.loadRoadMap("src/main/resources/data/graders/mod2/map2.txt", graph);
		assertNull(graph.bfs(new GeographicPoint(6, 6), new GeographicPoint(0, 0)));
	}
	
	@Test
	public void bfsMap3() {
        GraphLoader.loadRoadMap("src/main/resources/data/graders/mod2/map3.txt", graph);
		
		List<GeographicPoint> expectedVertices = new ArrayList<>();
		expectedVertices.add(new GeographicPoint(0, 0));
		expectedVertices.add(new GeographicPoint(0, 1));
		expectedVertices.add(new GeographicPoint(0, 2));
		expectedVertices.add(new GeographicPoint(1, 2));

		assertThat(graph.bfs(new GeographicPoint(0, 0), new GeographicPoint(1, 2)), is(expectedVertices));
	}
	
	@Test
	public void bfsUCSDMap() {
        GraphLoader.loadRoadMap("src/main/resources/data/graders/mod2/ucsd.map", graph);
		
		List<GeographicPoint> expectedVertices = new ArrayList<>();
		expectedVertices.add(new GeographicPoint(32.8756538, -117.2435715));
		expectedVertices.add(new GeographicPoint(32.8757495, -117.2430819));
		expectedVertices.add(new GeographicPoint(32.8741164, -117.2382689));
		expectedVertices.add(new GeographicPoint(32.8742087, -117.2381344));
		 		
		assertThat(graph.bfs(new GeographicPoint(32.8756538, -117.2435715), new GeographicPoint(32.8742087, -117.2381344)), is(expectedVertices));
	}
}