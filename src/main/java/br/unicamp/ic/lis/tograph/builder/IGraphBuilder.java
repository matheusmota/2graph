package br.unicamp.ic.lis.tograph.builder;

import java.util.List;

import br.unicamp.ic.lis.tograph.graph.GraphElement;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperties;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

/**
 * To do: add exceptions
 * 
 * @author Matheus, Victor
 */
public interface IGraphBuilder {

	public GraphNode createNode(String label) throws Exception;

	public GraphNode createNode(String label, GraphElementProperties properties) throws Exception;
	
	public List<GraphRelation> getNodeRelations(GraphNode node) throws Exception;
	
	public List<GraphRelation> getNodeInRelations(GraphNode node) throws Exception;
	
	public List<GraphRelation> getNodeOutRelations(GraphNode node) throws Exception;

	public GraphRelation createRelation(GraphNode startNode, String label, GraphNode endNode) throws Exception;

	public GraphNode getStartNodeOfRelation(GraphRelation relation) throws Exception;

	public GraphNode getEndNodeOfRelation(GraphRelation relation) throws Exception;

	public boolean addLabel(GraphElement element, String label) throws Exception;

	public List<String> getLabels(GraphElement element) throws Exception;

	public boolean addProperty(GraphElement element, GraphElementProperty property) throws Exception;

	public boolean addProperties(GraphElement element, GraphElementProperties property) throws Exception;

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
