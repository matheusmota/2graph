/**
 * 
 */
package br.unicamp.ic.lis.tograph.graph;

import java.net.URI;	

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;

/**
 * @author matheus
 *
 */
public class GraphRelation extends GraphElement {

	public GraphRelation(URI uri, String label, IGraphBuilder builder) {
		super(uri, label, builder);
	}

	public GraphRelation(GraphNode startNode, String label, GraphNode endNode) throws Exception {
		super(startNode.getBuilder().createRelation(startNode, label, endNode).getUri(), label, startNode.getBuilder());

	}

	public boolean addProperty(GraphElementProperty prop) throws Exception {

		this.getBuilder().addProperty(this, prop);
		return true;

	}

	public boolean delete() throws Exception {
		//this.getBuilder().deleteGraphElement(this);
		return true;
	}

	public GraphNode getStartNode() throws Exception {
		return this.getBuilder().getStartNodeOfRelation(this);
	}

	public GraphNode getEndNode() throws Exception {
		return this.getBuilder().getEndNodeOfRelation(this);
	}

	public boolean addProperty(String propertyLabel, String propertyValue) throws Exception {

		this.getBuilder().addProperty(this, new GraphElementProperty(propertyLabel, propertyValue));
		return true;

	}

}
