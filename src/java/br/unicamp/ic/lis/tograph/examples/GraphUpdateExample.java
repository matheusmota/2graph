package br.unicamp.ic.lis.tograph.examples;

import java.util.List;
import java.util.Vector;

import br.unicamp.ic.lis.tograph.concretebuilder.neo4j.neo4jrest.Neo4jRestConcreteBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

public class GraphUpdateExample {
	public static void main(String[] args) throws Exception {

		String serverRootUrl = "http://neo4j.lis.ic.unicamp.br/db/data";
		List<GraphElementProperty> properties = null;

		Neo4jRestConcreteBuilder builder = new Neo4jRestConcreteBuilder(serverRootUrl, "neo4j", "neo4j");
		builder.setDebubMessage(true);
		builder.testServer(true);

		// Retrieving the nodes containing a specific set of properties
		// in this example, we gonna get the first one
		properties = new Vector<GraphElementProperty>();
		properties.add(new GraphElementProperty("name", "Asdrubal"));
		properties.add(new GraphElementProperty("cpf", "982384823"));
		GraphNode n1 = builder.getNodesByProperties(properties).get(0);

		// Another node
		properties = new Vector<GraphElementProperty>();
		properties.add(new GraphElementProperty("name", "Doriana"));
		GraphNode n2 = builder.getNodesByProperties(properties).get(0);

		// updating N2 (doriana) to add property
		// n2.addProperty("address", "Rua Roxo Moreira, 34");

		// now let's remove the relationship between Doriana and Asdrubal and
		// create another one
		n2.getInRelations().get(0).delete();
		GraphRelation rel1 = new GraphRelation(n1, "was_married_with", n2);
		rel1.addProperty(new GraphElementProperty("divorce_date", "02/09/2004"));

	}
}