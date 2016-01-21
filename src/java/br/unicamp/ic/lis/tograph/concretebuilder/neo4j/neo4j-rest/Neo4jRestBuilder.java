package br.unicamp.ic.lis.tograph.builder.concretbuilder.neo4j;

import java.net.ConnectException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElement;
import br.unicamp.ic.lis.tograph.graph.elements.GraphElementProperties;
import br.unicamp.ic.lis.tograph.graph.elements.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.elements.GraphNode;
import br.unicamp.ic.lis.tograph.graph.elements.GraphRelation;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class Neo4jRestBuilder implements IGraphBuilder {

	private String serverRootUrl;

	private String nodeEntryPointUri;
	private String cypherEntryPointUri;
	private String nodeLabelsEntryPointUriAppendex;
	private URI mainNode;
	private boolean debubMessage;
	private WebResource webResource;
	private HTTPBasicAuthFilter httpBasicAuthFilter;

	public Neo4jRestBuilder(String serverRootUrl, String username, String password) {
		this.httpBasicAuthFilter = new HTTPBasicAuthFilter(username, password);
		// setting parameters related to neo4j up
		this.serverRootUrl = serverRootUrl;
		this.nodeEntryPointUri = this.serverRootUrl + "/node";
		this.cypherEntryPointUri = this.serverRootUrl + "/cypher";
		this.nodeLabelsEntryPointUriAppendex = "/labels";

	}

	public boolean isDebubMessage() {
		return debubMessage;
	}

	public void setDebubMessage(boolean debubMessage) {
		this.debubMessage = debubMessage;
	}

	public String getServerRootUrl() {
		return serverRootUrl;
	}

	private int getEntryPointGetStatus(String entryPoint) throws ConnectException {
		this.webResource = Client.create().resource(entryPoint);
		this.webResource.addFilter(this.httpBasicAuthFilter);

		ClientResponse response = this.webResource.get(ClientResponse.class);

		if (this.debubMessage)
			System.out.println(String.format("GET on [%s], status code [%d]", this.serverRootUrl, response.getStatus()));

		this.webResource = null;

		return response.getStatus();
	}

	private int getEntryPointPostStatus(String entryPoint) throws ConnectException {

		this.webResource = Client.create().resource(entryPoint);
		this.webResource.addFilter(this.httpBasicAuthFilter);

		ClientResponse response = this.webResource.post(ClientResponse.class);

		if (this.debubMessage)
			System.out.println(String.format("POST on [%s], status code [%d]", this.serverRootUrl, response.getStatus()));

		this.webResource = null;
		return response.getStatus();
	}

	private ClientResponse executeGetJson(String entryPoint, String jason) throws ConnectException {
		this.webResource = Client.create().resource(entryPoint);
		this.webResource.addFilter(this.httpBasicAuthFilter);

		ClientResponse response = this.webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		if (this.debubMessage)
			System.out.println(String.format("GET on [%s], status code [%d]", this.serverRootUrl, response.getStatus()));

		this.webResource = null;

		return response;
	}

	private ClientResponse executeDeleteJson(String entryPoint, String jason) throws ConnectException {
		this.webResource = Client.create().resource(entryPoint);
		this.webResource.addFilter(this.httpBasicAuthFilter);

		ClientResponse response = this.webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		if (this.debubMessage)
			System.out.println(String.format("DELETE on [%s], status code [%d]", this.serverRootUrl, response.getStatus()));

		this.webResource = null;

		return response;
	}

	private ClientResponse executePostJson(String entryPoint, String jason) {

		this.webResource = Client.create().resource(entryPoint);
		this.webResource.addFilter(this.httpBasicAuthFilter);

		// POST {arguments} to the node entry point URI
		ClientResponse response = this.webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(jason)
				.post(ClientResponse.class);

		URI location = response.getLocation();

		if (this.debubMessage)
			System.out.println(String.format("POST to [%s], status code [%d]", entryPoint, response.getStatus()));

		this.webResource = null;
		return response;

	}

	private ClientResponse executeCypherPostJson(String cypher) {
		String entryPoint = this.serverRootUrl + "/cypher";
		this.webResource = Client.create().resource(entryPoint);
		this.webResource.addFilter(this.httpBasicAuthFilter);

		JSONObject obj = new JSONObject();
		obj.put("query", cypher);

		String jason = obj.toJSONString();

		// POST {arguments} to the node entry point URI
		ClientResponse response = this.webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(jason)
				.post(ClientResponse.class);

		URI location = response.getLocation();

		if (this.debubMessage)
			System.out.println(String.format("POST to [%s], status code [%d]", entryPoint, response.getStatus()));

		this.webResource = null;
		return response;

	}

	private ClientResponse executePutJson(String entryPoint, String jason) {
		this.webResource = Client.create().resource(entryPoint);
		this.webResource.addFilter(this.httpBasicAuthFilter);

		// POST {arguments} to the node entry point URI
		ClientResponse response = this.webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(jason)
				.put(ClientResponse.class);

		URI location = response.getLocation();

		if (this.debubMessage)
			System.out.println(String.format("Put to [%s], status code [%d]", entryPoint, response.getStatus()));

		this.webResource = null;
		return response;

	}

	public int testServer(boolean verbose) throws ConnectException {
		int status = this.getEntryPointGetStatus(this.serverRootUrl);

		if (status == 200) {
			if (verbose || this.debubMessage)
				System.out.println("Server connection status: Success!");
		} else {
			if (verbose || this.debubMessage)
				System.out.println("Server connection status: No success!");
			throw new ConnectException("Could not connect to server " + this.serverRootUrl + ". Error code: " + status);
		}

		return status;
	}

	public GraphNode createNode(String label) {

		GraphNode newNode = null;

		URI newNodeUri = this.executePostJson(this.nodeEntryPointUri, "{}").getLocation();

		if (newNodeUri != null)// if it was created correctly
			newNode = new GraphNode(newNodeUri, label, this);

		if (label != null) {

			label = label.trim();
			if (!label.equals(""))
				this.addLabel(newNode, label);
		}

		return newNode;

	}

	public GraphRelation createRelation(GraphNode startNode, String label, GraphNode endNode) throws Exception {
		GraphRelation newRel = null;

		JSONObject obj = new JSONObject();
		obj.put("to", endNode.getUri() + "");
		obj.put("type", label);
		if (this.debubMessage)
			System.out.println(obj);

		URI newRelUri = this.executePostJson(startNode.getUri() + "/relationships", obj.toString()).getLocation();

		if (newRelUri != null)// if it was created correctly
			newRel = new GraphRelation(newRelUri, label, this);

		if (label == null || label.equals("")) {
			throw new Exception("Can not create relation without label");
		}

		return newRel;

	}

	public boolean addLabel(GraphElement element, String label) {

		this.executePostJson(element.getUri() + "/labels", "\"" + label + "\"");

		return true;
	}

	public boolean addProperty(GraphElement element, GraphElementProperty property) throws Exception {
		if (property.isValueFloat())
			this.executePutJson(element.getUri() + "/properties/" + property.getKey(), "" + property.getValueFloat() + "");
		else
			this.executePutJson(element.getUri() + "/properties/" + property.getKey(), "\"" + property.getValue() + "\"");

		return true;
	}

	@Override
	public boolean setProperties(GraphElement element, List<GraphElementProperty> properties) throws Exception {

		JSONObject jsonObj = new JSONObject();

		for (GraphElementProperty property : properties) {
			jsonObj.put(property.getKey(), property.getValue());
		}

		if (this.debubMessage)
			System.out.println(jsonObj);

		this.executePutJson(element.getUri() + "/properties", jsonObj.toJSONString());

		return false;
	}

	@Override
	public GraphElementProperties getProperties(GraphElement element) throws Exception {

		ClientResponse response = this.executeGetJson(element.getUri() + "/properties", "");
		String jsonStr = response.getEntity(String.class) + "";
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);

		GraphElementProperties props = new GraphElementProperties();

		for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = (String) jsonObject.get(key);
			props.put(key, value);
		}

		return props;
	}

	@Override
	public List<GraphNode> getNodesByProperties(List<GraphElementProperty> properties) throws Exception {

		List<GraphNode> list = new Vector<GraphNode>();

		StringBuffer cypher = new StringBuffer();
		cypher.append("match (n) where ");

		for (GraphElementProperty prop : properties) {
			cypher.append(" n." + prop.getKey() + "=\"" + prop.getValue() + "\" and ");

		}
		cypher.append(" true  return n ");

		ClientResponse response = this.executeCypherPostJson(cypher.toString());
		String jsonStr = response.getEntity(String.class) + "";

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		JSONArray array = (JSONArray) jsonObject.get("data");
		Iterator i = array.iterator();

		while (i.hasNext()) {
			JSONArray subArray = (JSONArray) i.next();

			JSONObject node = (JSONObject) subArray.get(0);
			String uri = (String) node.get("self");

			GraphNode newNode = new GraphNode(new URI(uri), "", this);

			list.add(newNode);
		}

		return list;
	}

	@Override
	public List<GraphNode> getNodesByLabel(String label) throws Exception {

		List<GraphNode> list = new Vector<GraphNode>();

		StringBuffer cypher = new StringBuffer();
		cypher.append("match (n:" + label + ") return n ");

		ClientResponse response = this.executeCypherPostJson(cypher.toString());
		String jsonStr = response.getEntity(String.class) + "";

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		JSONArray array = (JSONArray) jsonObject.get("data");
		Iterator i = array.iterator();

		while (i.hasNext()) {
			JSONArray subArray = (JSONArray) i.next();

			JSONObject node = (JSONObject) subArray.get(0);
			String uri = (String) node.get("self");

			GraphNode newNode = new GraphNode(new URI(uri), "", this);

			list.add(newNode);
		}

		return list;
	}

	@Override
	public List<GraphRelation> getRelationsByProperties(List<GraphElementProperty> properties) throws Exception {

		List<GraphRelation> list = new Vector<GraphRelation>();

		StringBuffer cypher = new StringBuffer();
		cypher.append("match (a)-[r]-(b) where  ");

		for (GraphElementProperty prop : properties) {
			cypher.append(" r." + prop.getKey() + "=\"" + prop.getValue() + "\" and ");

		}
		cypher.append(" true  return r ");

		ClientResponse response = this.executeCypherPostJson(cypher.toString());
		String jsonStr = response.getEntity(String.class) + "";

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		JSONArray array = (JSONArray) jsonObject.get("data");
		Iterator i = array.iterator();

		while (i.hasNext()) {
			JSONArray subArray = (JSONArray) i.next();

			JSONObject node = (JSONObject) subArray.get(0);
			String uri = (String) node.get("self");
			GraphRelation newRelation = new GraphRelation(new URI(uri), "", this);

			list.add(newRelation);
		}

		return list;
	}

	@Override
	public List<GraphRelation> getRelationsByLabel(String label) throws Exception {

		List<GraphRelation> list = new Vector<GraphRelation>();

		StringBuffer cypher = new StringBuffer();
		cypher.append("match ()-[r:" + label + "]->() return r  ;");

		ClientResponse response = this.executeCypherPostJson(cypher.toString());
		String jsonStr = response.getEntity(String.class) + "";
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		JSONArray array = (JSONArray) jsonObject.get("data");
		Iterator i = array.iterator();

		while (i.hasNext()) {
			JSONArray subArray = (JSONArray) i.next();
			JSONObject node = (JSONObject) subArray.get(0);
			String uri = (String) node.get("self");
			GraphRelation newRelation = new GraphRelation(new URI(uri), "", this);
			list.add(newRelation);
		}

		return list;
	}

	private String getNodeLabel(URI uri) throws Exception {
		String label = null;

		ClientResponse response = this.executeGetJson(uri + "/labels", " ");
		String jsonStr = response.getEntity(String.class) + "";
		JSONArray labels = (JSONArray) new JSONParser().parse(jsonStr);
		label = labels.get(0).toString();

		return label;
	}

	private String getRelationLabel(URI uri) throws Exception {
		String label = null;

		ClientResponse response = this.executeGetJson(uri.toString(), " ");
		String jsonStr = response.getEntity(String.class) + "";
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		label = jsonObject.get("type").toString();

		return label;
	}

	@Override
	public String getLabel(GraphElement element) throws Exception {
		String label = null;
		if (element.getClass() == GraphNode.class) {
			ClientResponse response = this.executeGetJson(element.getUri() + "/labels", " ");
			String jsonStr = response.getEntity(String.class) + "";
			JSONArray labels = (JSONArray) new JSONParser().parse(jsonStr);
			label = labels.get(0).toString();
		} else {
			ClientResponse response = this.executeGetJson(element.getUri().toString(), " ");
			String jsonStr = response.getEntity(String.class) + "";
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
			label = jsonObject.get("type").toString();
		}

		return label;
	}

	@Override
	public List<GraphRelation> getNodeRelations(GraphNode node) throws Exception {
		int index = node.getUri().toString().lastIndexOf("/");
		String id = node.getUri().toString().substring(index + 1);

		List<GraphRelation> list = new Vector<GraphRelation>();

		StringBuffer cypher = new StringBuffer();
		cypher.append("match (a)-[r]-(b) where id(a)=" + id + " return r");

		ClientResponse response = this.executeCypherPostJson(cypher.toString());
		String jsonStr = response.getEntity(String.class) + "";

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		JSONArray array = (JSONArray) jsonObject.get("data");
		Iterator i = array.iterator();

		while (i.hasNext()) {
			JSONArray subArray = (JSONArray) i.next();

			JSONObject newNode = (JSONObject) subArray.get(0);
			String uri = (String) newNode.get("self");
			GraphRelation newRelation = new GraphRelation(new URI(uri), "", this);

			list.add(newRelation);
		}

		return list;
	}

	@Override
	public GraphNode getStartNodeOfRelation(GraphRelation relation) throws Exception {
		ClientResponse response = this.executeGetJson(relation.getUri().toString(), " ");
		String jsonStr = response.getEntity(String.class) + "";
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		URI uri = new URI(jsonObject.get("start").toString());
		return new GraphNode(uri, this.getNodeLabel(uri), this);
	}

	@Override
	public GraphNode getEndNodeOfRelation(GraphRelation relation) throws Exception {
		ClientResponse response = this.executeGetJson(relation.getUri().toString(), " ");
		String jsonStr = response.getEntity(String.class) + "";
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		URI uri = new URI(jsonObject.get("end").toString());
		return new GraphNode(uri, this.getNodeLabel(uri), this);
	}

	@Override
	public List<GraphRelation> getNodeOutRelations(GraphNode node) throws Exception {
		int index = node.getUri().toString().lastIndexOf("/");
		String id = node.getUri().toString().substring(index + 1);

		List<GraphRelation> list = new Vector<GraphRelation>();

		StringBuffer cypher = new StringBuffer();
		cypher.append("match (a)-[r]->(b) where id(a)=" + id + " return r");

		ClientResponse response = this.executeCypherPostJson(cypher.toString());
		String jsonStr = response.getEntity(String.class) + "";

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		JSONArray array = (JSONArray) jsonObject.get("data");
		Iterator i = array.iterator();

		while (i.hasNext()) {
			JSONArray subArray = (JSONArray) i.next();

			JSONObject newNode = (JSONObject) subArray.get(0);
			String uri = (String) newNode.get("self");
			GraphRelation newRelation = new GraphRelation(new URI(uri), "", this);

			list.add(newRelation);
		}

		return list;
	}

	@Override
	public List<GraphRelation> getNodeInRelations(GraphNode node) throws Exception {
		int index = node.getUri().toString().lastIndexOf("/");
		String id = node.getUri().toString().substring(index + 1);

		List<GraphRelation> list = new Vector<GraphRelation>();

		StringBuffer cypher = new StringBuffer();
		cypher.append("match (a)<-[r]-(b) where id(a)=" + id + " return r");

		ClientResponse response = this.executeCypherPostJson(cypher.toString());
		String jsonStr = response.getEntity(String.class) + "";

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
		JSONArray array = (JSONArray) jsonObject.get("data");
		Iterator i = array.iterator();

		while (i.hasNext()) {
			JSONArray subArray = (JSONArray) i.next();

			JSONObject newNode = (JSONObject) subArray.get(0);
			String uri = (String) newNode.get("self");
			GraphRelation newRelation = new GraphRelation(new URI(uri), "", this);

			list.add(newRelation);
		}

		return list;
	}

	@Override
	public boolean deleteGraphElement(GraphElement element) throws Exception {
		if (this.debubMessage)
			System.out.println(element.getUri() );
		this.executeDeleteJson(element.getUri() + "", "");
		return false;
	}

}
