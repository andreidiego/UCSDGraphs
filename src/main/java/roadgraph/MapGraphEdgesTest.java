package roadgraph;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import geography.GeographicPoint;

public class MapGraphEdgesTest {

	private MapGraph graph;
	private GeographicPoint from;
	private GeographicPoint to;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		graph = new MapGraph();		
		from = new GeographicPoint(1, 1);
		to = new GeographicPoint(1, 2);
		
		graph.addVertex(from);
		graph.addVertex(to);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addEdgeThrowExceptionWhenAGeographicPointIsNotAVertexAlready() {
		GeographicPoint from = new GeographicPoint(2, 1);
		GeographicPoint to = new GeographicPoint(2, 2);
		
	    try {
			graph.addEdge(from, to, "Oziel Doria", "residential", 0.5);
	        fail("Expected an IllegalArgumentException to be thrown");
	        
	    } catch (IllegalArgumentException e) {
			assertEquals(0, graph.getNumEdges());
			assertFalse(graph.getOutNeighborsFromVertex(from).contains(to));
	    }		
	}
	
	@Test
	public void addEdgeThrowExceptionWhenGeographicPointIsNull() {
		
	    try {
			graph.addEdge(null, to, "Oziel Doria", "residential", 0.5);
	        fail("Expected an IllegalArgumentException to be thrown");
	        
	    } catch (IllegalArgumentException e) {
			assertEquals(0, graph.getNumEdges());
			assertFalse(graph.getOutNeighborsFromVertex(null).contains(to));
	    }		
	}

	@Test
	public void addEdgeThrowExceptionWhenRoadNameIsNull() {
		
	    try {
			graph.addEdge(from, to, null, "residential", 0.5);
	        fail("Expected an IllegalArgumentException to be thrown");
	        
	    } catch (IllegalArgumentException e) {
			assertEquals(0, graph.getNumEdges());
			assertFalse(graph.getOutNeighborsFromVertex(from).contains(to));
	    }		
	}

	@Test
	public void addEdgeThrowExceptionWhenRoadTypeIsNull() {
		
	    try {
			graph.addEdge(from, to, "Oziel Doria", "residential", -0.5);
	        fail("Expected an IllegalArgumentException to be thrown");
	        
	    } catch (IllegalArgumentException e) {
			assertEquals(0, graph.getNumEdges());
			assertFalse(graph.getOutNeighborsFromVertex(from).contains(to));
	    }		
	}
	
	@Test
	public void addEdgeThrowExceptionWhenRoadLengthIsNegative() {
		
	    try {
			graph.addEdge(from, to, "Oziel Doria", null, 0.5);
	        fail("Expected an IllegalArgumentException to be thrown");
	        
	    } catch (IllegalArgumentException e) {
			assertEquals(0, graph.getNumEdges());
			assertFalse(graph.getOutNeighborsFromVertex(from).contains(to));
	    }		
	}

	@Test
	public void addEdgeUpdatesTheNumberOfEdges() {
		graph.addEdge(from, to, "Oziel Doria", "residential", 0.5);
		
		assertEquals(1, graph.getNumEdges());
	}

	@Test
	public void addEdgePutsTheNewEdgeIntoTheAdjacencyList() {
		graph.addEdge(from, to, "Oziel Doria", "residential", 0.5);
		
		assertEquals(1, graph.getNumEdges());
		assertTrue(graph.getOutNeighborsFromVertex(from).contains(to));
	}
	
	@Test
	public void addEdgeDoNotAddRepeatedEdges() {
		graph.addEdge(from, to, "Oziel Doria", "residential", 0.5);
		graph.addEdge(from, to, "Oziel Doria", "residential", 0.5);
		
		assertEquals(1, graph.getNumEdges());
	}
	
}
