package br.unicamp.ic.lis.tograph.graph;

import java.net.URI;
import java.util.List;

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;

public class GraphElement {

	private URI uri;
	private IGraphBuilder builder;

	public GraphElement(URI uri, String label, IGraphBuilder builder) {
		this.uri = uri;
		this.builder = builder;

	}	

	private void setLabel(String label) throws Exception {
		this.builder.addLabel(this, label);
	}

	public GraphElementProperties getProperties() throws Exception {
		return this.builder.getProperties(this);

	}

	public IGraphBuilder getBuilder() {
		return builder;
	}

	public String getLabel() throws Exception {
		return this.builder.getLabels(this).get(0);
	}

	public void setProperties(GraphElementProperties properties) throws Exception {
		this.builder.setProperties(this, properties);
	}

	public URI getUri() {
		return uri;
	}

	public boolean addProperty(GraphElementProperty prop) throws Exception {
		this.getBuilder().addProperty(this, prop);
		return true;

	}

	public boolean addProperty(String key, String value) throws Exception {
		this.getBuilder().addProperty(this, new GraphElementProperty(key, value));
		return true;
	}

	public boolean addProperty(String key, float value) throws Exception {
		this.getBuilder().addProperty(this, new GraphElementProperty(key, value));
		return true;
	}

	public String toString() {
		try {
			return this.getUri().toString() + " " + this.getProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
