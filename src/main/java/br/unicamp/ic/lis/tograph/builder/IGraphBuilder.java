package br.unicamp.ic.lis.tograph.builder;

import java.util.List;

import br.unicamp.ic.lis.tograph.graph.GraphElement;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperties;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

//TODO add Exceptions

/**
 * Interface for building graphs
 * @author Matheus, labrax
 *
 */
public interface IGraphBuilder {
	/**
	 * Create a node in the database
	 * @param label is the label on the database
	 * @return the created node
	 * @throws Exception
	 */
	public GraphNode createNode(String label) throws Exception;

	/**
	 * Create a node in the database with properties
	 * @param label is the label on the database
	 * @param properties are the properties
	 * @return the created node
	 * @throws Exception
	 */
	public GraphNode createNode(String label, GraphElementProperties properties) throws Exception;
	
	public List<GraphRelation> getNodeRelations(GraphNode node) throws Exception;
	
	public List<GraphRelation> getNodeInRelations(GraphNode node) throws Exception;
	
	public List<GraphRelation> getNodeOutRelations(GraphNode node) throws Exception;

	/**
	 * Create a relation between two nodes on the database
	 * @param startNode the origin node
	 * @param label the label for the relationship
	 * @param endNode the destination node
	 * @return the created relation
	 * @throws Exception
	 */
	public GraphRelation createRelation(GraphNode startNode, String label, GraphNode endNode) throws Exception;

	public GraphNode getStartNodeOfRelation(GraphRelation relation) throws Exception;

	public GraphNode getEndNodeOfRelation(GraphRelation relation) throws Exception;

	public boolean addLabel(GraphElement element, String label) throws Exception;

	public List<String> getLabels(GraphElement element) throws Exception;

	/**
	 * Insert a property in an element
	 * @param element is the graph element
	 * @param property is the property
	 * @return true if successful 
	 * @throws Exception if failed
	 */
	public boolean addProperty(GraphElement element, GraphElementProperty property) throws Exception;

	/**
	 * Insert multiple properties in an element
	 * @param element is the graph element
	 * @param properties are the properties
	 * @return true if successful
	 * @throws Exception if failed
	 */
	public boolean addProperties(GraphElement element, GraphElementProperties properties) throws Exception;

	public boolean setProperty(GraphElement element, GraphElementProperty property) throws Exception;

	public boolean setProperties(GraphElement element, GraphElementProperties properties) throws Exception;

	public GraphElementProperties getProperties(GraphElement element) throws Exception;

	// public boolean setProperties(GraphElement element,
	// List<GraphElementProperty> properties) throws Exception;

	// public GraphElementProperties getProperties(GraphElement element) throws
	// Exception;

	// public List<GraphRelation> getNodeRelations(GraphNode node) throws
	// Exception;

	// public List<GraphRelation> getNodeOutRelations(GraphNode node) throws
	// Exception;

	// public List<GraphRelation> getNodeInRelations(GraphNode node) throws Exception;

	// public List<GraphNode> getNodesByProperties(List<GraphElementProperty>
	// properties) throws Exception;

	// public List<GraphNode> getNodesByLabel(String label) throws Exception;

	// public List<GraphRelation> getRelationsByLabel(String label) throws
	// Exception;

	// public List<GraphRelation>
	// getRelationsByProperties(List<GraphElementProperty> properties) throws
	// Exception;

	// public GraphNode getStartNodeOfRelation(GraphRelation relation) throws
	// Exception;

	// public GraphNode getEndNodeOfRelation(GraphRelation relation) throws
	// Exception;

	// public boolean deleteGraphElement(GraphElement element) throws Exception;

	// public boolean deleteAllProperties(GraphElement element) throws
	// Exception;

	// public boolean deleteProperty(GraphElement element, String key) throws
	// Exception;

}
