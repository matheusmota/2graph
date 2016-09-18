package br.unicamp.ic.lis.tograph.etl.xml.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.util.cypher.CypherUtil;

public class XMLtoNeo4jBuilder {

	private Node rootNode;
	private String xmlFilePath;
	private boolean debubMessage = false;
	private IGraphBuilder builder;

	public XMLtoNeo4jBuilder(String filePath, IGraphBuilder builder)
			throws ParserConfigurationException, SAXException, IOException {

		this.builder = builder;

		this.xmlFilePath = filePath;
		// Get the DOM Builder Factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// Get the DOM Builder
		DocumentBuilder xmlDocbuilder = factory.newDocumentBuilder();
		// Load and Parse the XML document
		FileInputStream is = new FileInputStream(filePath);
		Document document = xmlDocbuilder.parse(is);
		// Iterating through the nodes and extracting the data.
		this.rootNode = document;

	}

	public GraphNode loadXMLOnGraph() throws URISyntaxException {
		return this.loadXmlFromNode(this.rootNode);

	}

	private GraphNode loadXmlFromNode(Node root) throws URISyntaxException {
		try {

			if (root == null) {
				return null;
			}
			GraphNode resourceNode = this.builder.createNode("resource");
			this.builder.addProperty(resourceNode, new GraphElementProperty("ls_filePath", this.xmlFilePath));
			this.builder.addProperty(resourceNode, new GraphElementProperty("ls_file_mime", "application/xml"));

			if (this.debubMessage)
				System.out.println("Starting dom navigation...");

			loadNode(root, resourceNode);

			System.out.println("Status: Success. Root node URI:" + resourceNode);
			return resourceNode;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;

	}

	private static String generateJsonRelationship(URI endNode, String relationshipType, String... jsonAttributes) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"to\" : \"");
		sb.append(endNode.toString());
		sb.append("\", ");

		sb.append("\"type\" : \"");
		sb.append(relationshipType);
		if (jsonAttributes == null || jsonAttributes.length < 1) {
			sb.append("\"");
		} else {
			sb.append("\", \"data\" : ");
			for (int i = 0; i < jsonAttributes.length; i++) {
				sb.append(jsonAttributes[i]);
				if (i < jsonAttributes.length - 1) { // Miss off the final comma
					sb.append(", ");
				}
			}
		}

		sb.append(" }");
		return sb.toString();
	}

	private void loadNode(Node node, GraphNode fatherNode) throws Exception {

		String nodeName = node.getNodeName();
		String namespaceURI = node.getNamespaceURI();
		String prefix = node.getPrefix();
		String localName = node.getLocalName();
		String value = node.getNodeValue();
		NamedNodeMap atts;
		NodeList nodeList;
		GraphNode newGraphNode;
		int type = node.getNodeType();

		switch (type) {
		case Node.ATTRIBUTE_NODE:

			newGraphNode = this.builder.createNode("xmlAttribute");
			this.builder.addProperty(newGraphNode, new GraphElementProperty("xmlNodeType", "Attribute"));
			this.builder.addProperty(newGraphNode, new GraphElementProperty("scaleBehavior", "Structural"));
			this.builder.addProperty(newGraphNode, new GraphElementProperty("tagLabel", nodeName));
			this.builder.addProperty(newGraphNode, new GraphElementProperty("value", CypherUtil.escapeStringForCypher(value)));
			atts = node.getAttributes();

			if (atts != null)
				for (int i = 0; i < atts.getLength(); i++) {

					Node att = atts.item(i);
					loadNode(att, newGraphNode);
				}

			// relations
			this.builder.createRelation(newGraphNode, "BELONGS_TO",  fatherNode);

			break;

		case Node.CDATA_SECTION_NODE:
			System.out.println("CDATA_SECTION_NODE: Not implemented");
			break;

		case Node.COMMENT_NODE:
			System.out.println("COMMENT_NODE: Not implemented");
			break;

		case Node.DOCUMENT_FRAGMENT_NODE:
			System.out.println("DOCUMENT_FRAGMENT_NODE: Not implemented");
			break;

		case Node.DOCUMENT_NODE:

			newGraphNode = this.builder.createNode("xmlDocument");
			this.builder.addProperty(newGraphNode, new GraphElementProperty("xmlNodeType", "Document"));
			this.builder.addProperty(newGraphNode, new GraphElementProperty("scaleBehavior", "Structural"));
			this.builder.createRelation(newGraphNode, "REPRESENTS",fatherNode);

			// handling attributes 
			atts = node.getAttributes();
			if (atts != null)
				for (int i = 0; i < atts.getLength(); i++) {
					Node att = atts.item(i);
					this.loadNode(att, newGraphNode);
				}

			// handling child nodes
			nodeList = node.getChildNodes();
			if (nodeList != null)
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node nodeTemp = nodeList.item(i);
					this.loadNode(nodeTemp, newGraphNode);
				}

			break;

		case Node.DOCUMENT_TYPE_NODE:

			System.out.println("DOCUMENT_TYPE_NODE: Not implemented");
			break;

		case Node.ELEMENT_NODE:

			newGraphNode = this.builder.createNode("xmlElement");
			this.builder.addProperty(newGraphNode, new GraphElementProperty("xmlNodeType", "Element"));
			this.builder.addProperty(newGraphNode, new GraphElementProperty( "scaleBehavior", "Structural"));
			this.builder.addProperty(newGraphNode, new GraphElementProperty("tagLabel", nodeName));
			this.builder.createRelation(newGraphNode, "BELONGS_TO", fatherNode);

			// handling attributes
			atts = node.getAttributes();
			if (atts != null)
				for (int i = 0; i < atts.getLength(); i++) {
					Node att = atts.item(i);
					loadNode(att, newGraphNode);
				}

			// handling child nodes
			nodeList = node.getChildNodes();
			if (nodeList != null)
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node nodeTemp = nodeList.item(i);
					loadNode(nodeTemp, newGraphNode);
				}

			break;

		case Node.ENTITY_NODE:
			System.out.println("ENTITY_NODE: Not implemented");
			break;

		case Node.ENTITY_REFERENCE_NODE:
			System.out.println("ENTITY_REFERENCE_NODE: Not implemented");
			break;

		case Node.NOTATION_NODE:
			System.out.println("NOTATION_NODE: Not implemented");
			break;

		case Node.PROCESSING_INSTRUCTION_NODE:
			System.out.println("PROCESSING_INSTRUCTION_NODE: Not implemented");
			break;

		case Node.TEXT_NODE:

			if (value != null) {
				if (value.trim().equals("")) {
					//
				} else {

					newGraphNode = this.builder.createNode("xmlTextNode");
					this.builder.addProperty(newGraphNode, new GraphElementProperty("xmlNodeType", "Text"));
					this.builder.addProperty(newGraphNode, new GraphElementProperty("scaleBehavior", "Structural"));
					this.builder.addProperty(newGraphNode, new GraphElementProperty("value", CypherUtil.escapeStringForCypher(value)));
					this.builder.createRelation(newGraphNode, "BELONGS_TO", fatherNode);

				}
			}

			break;

		default:
			// out.print("UNSUPPORTED NODE: " + type);

			// out.println();
			break;
		}

	}

}
