package br.unicamp.ic.lis.tograph.concretebuilder.textual;

import java.net.URI;
import java.util.List;

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElement;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperties;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

public class RDFTriplesTextBuilder implements IGraphBuilder {

	private int nodeCounter = 0;
	private int relCounter = 0;

	private String url;

	public RDFTriplesTextBuilder(String url) {
		this.url = url + "/";

	}

	public void setDebubMessage(boolean flag) {

	}

	public void testServer(boolean flag) {

	}

	@Override
	public GraphNode createNode(String label) throws Exception {

		String uri = url + "node" + nodeCounter++;
		if (label != null)
			System.out.println(uri + "   " + "rdfs:label" + "   " + "\"" + label + "\"");
		return new GraphNode(new URI(uri), label, this);
	}

	@Override
	public GraphRelation createRelation(GraphNode startNode, String label, GraphNode endNode) throws Exception {
		String uriRel = url + "relation" + relCounter++;
		System.out.println(startNode.getUri() + "   " + label + "   " + endNode.getUri());

		return new GraphRelation(new URI(uriRel), label, this);
	}

	@Override
	public boolean addProperty(GraphElement element, GraphElementProperty property) throws Exception {
		if (element.getClass() == GraphNode.class)
			System.out.println(element.getUri() + "   " + property.getKey() + "   " + "\"" + property.getValue() + "\"");

		return false;
	}

	@Override
	public boolean addLabel(GraphElement node, String label) {
		if (label != null)
			System.out.println(node.getUri() + "   " + "rdfs:label" + "   " + "\"" + label + "\"");
		return false;
	}

	@Override
	public GraphElementProperties getProperties(GraphElement element) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GraphNode> getNodesByProperties(List<GraphElementProperty> properties) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GraphRelation> getRelationsByProperties(List<GraphElementProperty> properties) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel(GraphElement element) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphRelation> getNodeRelations(GraphNode node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphRelation> getNodeOutRelations(GraphNode node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphRelation> getNodeInRelations(GraphNode node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GraphNode> getNodesByLabel(String label) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GraphRelation> getRelationsByLabel(String label) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphNode getStartNodeOfRelation(GraphRelation relation) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphNode getEndNodeOfRelation(GraphRelation relation) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLabels(GraphElement element) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addProperties(GraphElement element, GraphElementProperties property) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GraphNode createNode(String label, GraphElementProperties properties)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setProperty(GraphElement element,
			GraphElementProperty property) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setProperties(GraphElement element,
			GraphElementProperties properties) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
