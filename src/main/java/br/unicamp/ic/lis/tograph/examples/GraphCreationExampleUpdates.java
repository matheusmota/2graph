package br.unicamp.ic.lis.tograph.examples;

import br.unicamp.ic.lis.tograph.concretebuilder.neo4j.neo4jrest.Neo4jRestConcreteBuilder;

public class GraphCreationExampleUpdates {
	public static void main(String[] args) throws Exception {

		String serverRootUrl = "http://neo4j.lis.ic.unicamp.br/db/data";

		Neo4jRestConcreteBuilder builder = new Neo4jRestConcreteBuilder(serverRootUrl, "neo4j", "neo4j");

		builder.setDebubMessage(true);
		builder.testServer(true);

		// * Creating Nodes */
		// GraphNode n1 = new GraphNode(builder);
		// n1.addLabel("person");
		// n1.addProperty("name", "p1");
		// n1.addProperty("age", 10);
		// n1.addProperty("flag","true");
		// GraphNode n2 = builder.createNode("person");
		// n2.addProperty("name", "p2");
		// n2.addProperty("flag","true");
		// GraphNode n3 = builder.createNode("person");
		// n3.addProperty("name", "p3");
		// n3.addProperty("flag","true");
		//
		// GraphRelation rel1 = builder.createRelation(n1, "knows", n2);
		// rel1.addProperty("date", "xxx");
		// rel1.addProperty("flag","true");
		// GraphRelation rel2 = builder.createRelation(n1, "knows", n3);
		// rel2.addProperty("date", "yyy");
		// rel2.addProperty("flag","true");

		// List<GraphElementProperty> properties = new
		// Vector<GraphElementProperty>();
		// properties.add(new GraphElementProperty("name", "p2"));
		//
		// GraphNode n1 = builder.getNodesByProperties(properties).get(0);
		// GraphNode n2 = new GraphNode(builder);
		// n2.addLabel("Novo");
		//
		// GraphRelation rel1 = builder.createRelation(n1, "criei_agora", n2);
		// rel1.addProperty("bla?", "blablabla");

	}
}