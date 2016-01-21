package br.unicamp.ic.lis.tograph.examples;

import br.unicamp.ic.lis.tograph.builder.concretbuilder.neo4j.Neo4jRestBuilder;
import br.unicamp.ic.lis.tograph.graph.elements.GraphNode;
import br.unicamp.ic.lis.tograph.graph.elements.GraphRelation;

public class GraphCreationSimpleExample {
	public static void main(String[] args) throws Exception {

		String serverRootUrl = "http://neo4j.lis.ic.unicamp.br/db/data";

		Neo4jRestBuilder builder = new Neo4jRestBuilder(serverRootUrl, "neo4j", "neo4j");
		// RDFTriplesBuilder builder = new RDFTriplesBuilder(serverRootUrl);

		builder.setDebubMessage(true);
		builder.testServer(true);

		// /* Creating Nodes */
		GraphNode n1 = new GraphNode(builder);
		n1.addLabel("VideoResource");
		n1.addProperty("Idvideo", "1");
		n1.addProperty("Type", "Video");
		n1.addProperty("Title", "DBMS Storage");

		GraphNode n2 = new GraphNode(builder);
		n2.addLabel("SlideResource");
		n2.addProperty("Idslide", "19");
		n2.addProperty("Type", "Slide");
		n2.addProperty("Title", "Introduction to DBMS");

	GraphRelation rel = new GraphRelation(n1, "SQL", n2);
	rel.addProperty("data", "2015");
	
	

	}

}