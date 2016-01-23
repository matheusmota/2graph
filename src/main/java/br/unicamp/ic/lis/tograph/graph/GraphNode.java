package br.unicamp.ic.lis.tograph.graph;

import java.net.URI;
import java.util.List;

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;
	
/**
 * @author matheus
 *
 */
public class GraphNode extends GraphElement {

	public GraphNode(URI uri, String label, IGraphBuilder builder) {
		super(uri, label, builder);

	}

	public GraphNode(IGraphBuilder builder, String label) throws Exception {
		super(builder.createNode(label).getUri(), null, builder);

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
