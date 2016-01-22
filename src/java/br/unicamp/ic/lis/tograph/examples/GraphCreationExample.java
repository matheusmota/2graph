package br.unicamp.ic.lis.tograph.examples;

import br.unicamp.ic.lis.tograph.concretebuilder.neo4j.neo4jrest.Neo4jRestConcreteBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

public class GraphCreationExample {
	public static void main(String[] args) throws Exception {

		String serverRootUrl = "http://neo4j.lis.ic.unicamp.br/db/data";

		Neo4jRestConcreteBuilder builder = new Neo4jRestConcreteBuilder(serverRootUrl, "neo4j", "neo4j");
		// RDFTriplesBuilder builder = new RDFTriplesBuilder(serverRootUrl);

		builder.setDebubMessage(true);
		builder.testServer(true);

		/**
		 * Creating Nodes There a few options for creating nodes and for
		 * creating and adding properties. The following examples show the
		 * current options
		 * */

		GraphNode n1 = new GraphNode(builder);
		n1.addLabel("person");
		n1.addProperty("name", "Asdrubal");
		n1.addProperty("dob", "27-auf-1987");
		n1.addProperty("gender", "male");
		GraphElementProperty prop = new GraphElementProperty("cpf", "982384823");
		n1.addProperty(prop);
		builder.addProperty(n1, new GraphElementProperty("rg", "83294203888"));

		GraphNode n2 = builder.createNode("person");
		builder.addProperty(n2, new GraphElementProperty("name", "Doriana"));
		builder.addProperty(n2, new GraphElementProperty("cpf", "9898045023"));

		GraphNode n3 = builder.createNode("person");
		n3.addProperty(new GraphElementProperty("name", "Dino"));

		GraphNode n4 = builder.createNode("person");
		n4.addProperty("name", "Pedro");

		GraphNode n5 = builder.createNode("person");
		n5.addProperty("name", "Bruna");

		/*
		 * Creating Relationships The same happen with relationships The
		 * examples below show the different options for managing relationships
		 */

		GraphRelation rel1 = builder.createRelation(n1, "knows", n3);
		builder.addProperty(rel1, new GraphElementProperty("meeting_date", "01/01/2012"));

		GraphRelation rel2 = new GraphRelation(n1, "is_married_with", n2);
		rel2.addProperty(new GraphElementProperty("date", "12/05/1998"));
		rel2.addProperty("place", "canada");

		GraphRelation rel3 = builder.createRelation(n4, "knows", n1);

		GraphRelation rel4 = builder.createRelation(n5, "knows", n1);

	}

}