package br.unicamp.ic.lis.tograph.examples;

import br.unicamp.ic.lis.tograph.concretebuilder.neo4j.neo4jrest.Neo4jRestConcreteBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

public class GraphCreationSimpleExample {
	public static void main(String[] args) throws Exception {

		String serverRootUrl = "http://neo4j.lis.ic.unicamp.br/db/data";

		Neo4jRestConcreteBuilder builder = new Neo4jRestConcreteBuilder(serverRootUrl, "neo4j", "neo4j");
		//RDFTriplesTextBuilder builder = new RDFTriplesTextBuilder(serverRootUrl);

		builder.setDebubMessage(true);
		builder.testServer(true);

		// /* Creating Nodes */
		GraphNode n1 = new GraphNode(builder, "TestRelation");
		n1.addProperty("Idvideo", "1");
		n1.addProperty("Type", "Video");
		n1.addProperty("Title", "DBMS Storage");

		GraphNode n2 = new GraphNode(builder, "SlideResource");
		n2.addProperty("Idslide", "19");
		n2.addProperty("Type", "Slide");
		n2.addProperty("Title", "Introduction to DBMS");

		GraphRelation rel = new GraphRelation(n1, "SQL", n2);
		rel.addProperty("data", "2015");

	}

}