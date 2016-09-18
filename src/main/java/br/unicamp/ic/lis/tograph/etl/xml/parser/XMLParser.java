package br.unicamp.ic.lis.tograph.etl.xml.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	private PrintWriter out;
	private Node rootNode;
	private long nodeCount;

	public XMLParser(String filePath) throws ParserConfigurationException, SAXException, IOException {
		// Get the DOM Builder Factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// Get the DOM Builder
		DocumentBuilder builder = factory.newDocumentBuilder();
		// Load and Parse the XML document
		FileInputStream is = new FileInputStream(filePath);
		Document document = builder.parse(is);
		// Iterating through the nodes and extracting the data.
		this.rootNode = document;

		System.out.println(this.scapeStringForCypher("(a) {b} 'c' \"d\""));
	}

	public void print() {
		this.out = new PrintWriter(System.out);
		this.printFromNode(this.rootNode);
		this.out.flush();
		this.out.close();

	}

	private void printFromNode(Node root) {
		if (root == null) {
			return;
		}

		echo(root);

		NodeList nodeList = root.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			this.printFromNode(node);

		}

	}

	public void produceCypher(OutputStream os) {
		this.out = new PrintWriter(os);
		this.produceCypherFromNode(this.rootNode);
		this.out.flush();
		this.out.close();

	}

	private void produceCypherFromNode(Node root) {
		if (root == null) {
			return;
		}
		this.nodeCount = 0;
		cypherEcho(root, this.nodeCount);
		// NodeList nodeList = root.getChildNodes();
		// for (int i = 0; i < nodeList.getLength(); i++) {
		//
		// Node node = nodeList.item(i);
		// this.produceCypherFromNode(node);
		//
		// }

	}

	private void printlnCommon(Node n) {
		out.print(" nodeName=\"" + n.getNodeName() + "\"");

		String val = n.getNamespaceURI();
		if (val != null) {
			out.print(" uri=\"" + val + "\"");
		}

		val = n.getPrefix();

		if (val != null) {
			out.print(" pre=\"" + val + "\"");
		}

		val = n.getLocalName();
		if (val != null) {
			out.print(" local=\"" + val + "\"");
		}

		val = n.getNodeValue();
		if (val != null) {
			out.print(" nodeValue=");
			if (val.trim().equals("")) {
				// Whitespace
				out.print("[WS]");
			} else {
				out.print("\"" + n.getNodeValue() + "\"");
			}
		}
		out.println();
	}

	private void echo(Node n) {
		//
		// int type = n.getNodeType();
		//
		// switch (type) {
		// case Node.ATTRIBUTE_NODE:
		// out.print("ATTR:");
		// printlnCommon(n);
		// break;
		//
		// case Node.CDATA_SECTION_NODE:
		// out.print("CDATA:");
		// printlnCommon(n);
		// break;
		//
		// case Node.COMMENT_NODE:
		// out.print("COMM:");
		// printlnCommon(n);
		// break;
		//
		// case Node.DOCUMENT_FRAGMENT_NODE:
		// out.print("DOC_FRAG:");
		// printlnCommon(n);
		// break;
		//
		// case Node.DOCUMENT_NODE:
		// out.print("DOC:");
		// printlnCommon(n);
		// break;
		//
		// case Node.DOCUMENT_TYPE_NODE:
		// out.print("DOC_TYPE:");
		// printlnCommon(n);
		// NamedNodeMap nodeMap = ((DocumentType) n).getEntities();
		// indent += 2;
		// for (int i = 0; i < nodeMap.getLength(); i++) {
		// Entity entity = (Entity) nodeMap.item(i);
		// echo(entity);
		// }
		// indent -= 2;
		// break;
		//
		// case Node.ELEMENT_NODE:
		// out.print("ELEM:");
		//
		// printlnCommon(n);
		//
		// NamedNodeMap atts = n.getAttributes();
		// indent += 2;
		// for (int i = 0; i < atts.getLength(); i++) {
		// Node att = atts.item(i);
		// echo(att);
		// }
		// indent -= 2;
		// break;
		//
		// case Node.ENTITY_NODE:
		// out.print("ENT:");
		// printlnCommon(n);
		// break;
		//
		// case Node.ENTITY_REFERENCE_NODE:
		// out.print("ENT_REF:");
		// printlnCommon(n);
		// break;
		//
		// case Node.NOTATION_NODE:
		// out.print("NOTATION:");
		// printlnCommon(n);
		// break;
		//
		// case Node.PROCESSING_INSTRUCTION_NODE:
		// out.print("PROC_INST:");
		// printlnCommon(n);
		// break;
		//
		// case Node.TEXT_NODE:
		// out.print("TEXT:");
		// printlnCommon(n);
		// break;
		//
		// default:
		// out.print("UNSUPPORTED NODE: " + type);
		// printlnCommon(n);
		// break;
		// }
		//
		// indent++;
		// for (Node child = n.getFirstChild(); child != null; child = child
		// .getNextSibling()) {
		// echo(child);
		// }
		// indent--;
	}

	private String scapeStringForCypher(String string) {

		String scapedString = string;
		// scapedString = scapedString.replace("(", "\\(");
		// scapedString = scapedString.replace(")", "\\)");
		// scapedString = scapedString.replace("}", "\\}");
		// scapedString = scapedString.replace("{", "\\{");
		scapedString = scapedString.replace("'", "\\'");
		scapedString = scapedString.replace("\"", "\\\"");

		return scapedString;
	}

	private void cypherEcho(Node node, long idFather) {

		this.nodeCount++;

		String nodeName = node.getNodeName();
		String namespaceURI = node.getNamespaceURI();
		String prefix = node.getPrefix();
		String localName = node.getLocalName();
		String value = node.getNodeValue();
		NamedNodeMap atts;
		NodeList nodeList;
		int type = node.getNodeType();

		switch (type) {
		case Node.ATTRIBUTE_NODE:
			out.print("CREATE (N" + this.nodeCount + " {xmlNodeType : 'Attribute' , ontoscaleType : 'Structural' , tagLabel : '" + nodeName + "' ");
			out.print(", value : '" + this.scapeStringForCypher(value) + "' } )");
			long idFatherAttr = this.nodeCount;
			atts = node.getAttributes();

			if (atts != null)
				for (int i = 0; i < atts.getLength(); i++) {

					Node att = atts.item(i);
					cypherEcho(att, idFatherAttr);
				}

			out.println();

			// relations

			out.println("CREATE N" + this.nodeCount + " -[r:BELONGS_TO]->N" + idFather);

			break;

		case Node.CDATA_SECTION_NODE:
			out.print("CDATA:");

			out.println();
			break;

		case Node.COMMENT_NODE:
			out.print("COMM:");

			out.println();
			break;

		case Node.DOCUMENT_FRAGMENT_NODE:
			out.print("DOC_FRAG:");

			out.println();
			break;

		case Node.DOCUMENT_NODE:
			out.print("CREATE (N" + this.nodeCount + " {xmlNodeType : 'Document' , ontoscaleType : 'Structural' ");
			out.println(" } )");
			long idFatherTempDoc = this.nodeCount;

			// handling attributes
			atts = node.getAttributes();
			if (atts != null)
				for (int i = 0; i < atts.getLength(); i++) {
					Node att = atts.item(i);
					cypherEcho(att, idFatherTempDoc);
				}

			// handling child nodes
			nodeList = node.getChildNodes();
			if (nodeList != null)
				for (int i = 0; i < nodeList.getLength(); i++) {

					Node nodeTemp = nodeList.item(i);
					cypherEcho(nodeTemp, idFatherTempDoc);

				}

			break;

		case Node.DOCUMENT_TYPE_NODE:

			System.out.println("Document type!!");

			break;

		case Node.ELEMENT_NODE:
			out.print("CREATE (N" + this.nodeCount + " {xmlNodeType : 'Element' , ontoscaleType : 'Structural' , tagLabel : '" + nodeName + "' ");
			out.println(" } )");
			out.println("CREATE N" + this.nodeCount + " -[r:BELONGS_TO]->N" + idFather);

			long idFatherTempElem = this.nodeCount;

			// handling attributes
			atts = node.getAttributes();
			if (atts != null)
				for (int i = 0; i < atts.getLength(); i++) {
					Node att = atts.item(i);
					cypherEcho(att, idFatherTempElem);
				}

			// handling child nodes
			nodeList = node.getChildNodes();
			if (nodeList != null)
				for (int i = 0; i < nodeList.getLength(); i++) {

					Node nodeTemp = nodeList.item(i);
					cypherEcho(nodeTemp, idFatherTempElem);
				}

			break;

		case Node.ENTITY_NODE:
			out.print("ENT:");

			out.println();
			break;

		case Node.ENTITY_REFERENCE_NODE:
			out.print("ENT_REF:");

			out.println();
			break;

		case Node.NOTATION_NODE:
			out.print("NOTATION:");

			out.println();
			break;

		case Node.PROCESSING_INSTRUCTION_NODE:
			out.print("PROC_INST:");

			out.println();
			break;

		case Node.TEXT_NODE:

			if (value != null) {
				if (value.trim().equals("")) {
					//
				} else {
					out.print("CREATE (N" + this.nodeCount + " {xmlNodeType : 'Text' , ontoscaleType : 'Structural' ");

					out.print(" , value : '" + this.scapeStringForCypher(value) + "'");
					out.println(" } )");
					// relations
					out.println("CREATE N" + this.nodeCount + " -[r:BELONGS_TO]->N" + idFather);

				}
			}

			break;

		default:
			out.print("UNSUPPORTED NODE: " + type);

			out.println();
			break;
		}

	}
}
