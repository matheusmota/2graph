package br.unicamp.ic.lis.tograph.graph.elements;

import java.net.URI;
import java.util.List;

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElement;

/**
 * @author matheus
 *
 */
public class GraphNode extends GraphElement {

	public GraphNode(URI uri, String label, IGraphBuilder builder) {
		super(uri, label, builder);

	}

	public GraphNode(IGraphBuilder builder) throws Exception {
		super(builder.createNode(null).getUri(), null, builder);

	}

	public List<GraphRelation> getRelations() throws Exception {
		return this.getBuilder().getNodeRelations(this);
	}

	public List<GraphRelation> getOutRelations() throws Exception {
		return this.getBuilder().getNodeOutRelations(this);
	}

	public List<GraphRelation> getInRelations() throws Exception {
		return this.getBuilder().getNodeInRelations(this);
	}

	public boolean addLabel(String label) throws Exception {

		this.getBuilder().addLabel(this, label);
		return true;

	}



}
