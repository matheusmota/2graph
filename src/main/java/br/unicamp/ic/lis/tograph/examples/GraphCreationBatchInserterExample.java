package br.unicamp.ic.lis.tograph.examples;

import br.unicamp.ic.lis.tograph.concretebuilder.neo4j.neo4jbatchinserter.Neo4jBatchInserterConcreteBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperties;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

public class GraphCreationBatchInserterExample {
	public static void main(String[] args) throws Exception {
		Neo4jBatchInserterConcreteBuilder builder = new Neo4jBatchInserterConcreteBuilder();
		
		//builder.addSetting("delete_old_data", "true");
		builder.addSetting("db_dir", "/home/vroth/Downloads/neo4j-community-2.3.2/data/graph.db");
		builder.addSetting("restart_neo4j_with", "/home/vroth/Downloads/neo4j-community-2.3.2/bin/neo4j");
		
		builder.startServer();

		GraphElementProperties properties = new GraphElementProperties();
		properties.insertProperty("name", "Asdrubal");
		properties.insertProperty("dob", "27-auf-1987");
		properties.insertProperty("gender", "male");
		GraphElementProperty prop = new GraphElementProperty("cpf", "982384823");
		properties.insertProperty(prop);
		properties.insertProperty("rg", "83294203888");
		GraphNode n1 = builder.createNode("person", properties);
		
		GraphElementProperties properties2 = new GraphElementProperties();
		properties2.insertProperty("name", "Doriana");
		properties2.insertProperty("cpf", "9898045023");
		GraphNode n2 = builder.createNode("person", properties2);
		
		GraphElementProperties properties3 = new GraphElementProperties();
		properties3.insertProperty("name", "Dino");
		GraphNode n3 = builder.createNode("person", properties3);
		
		GraphElementProperties properties4 = new GraphElementProperties();
		properties4.insertProperty("name", "Pedro");
		GraphNode n4 = builder.createNode("person", properties4);
		
		GraphElementProperties properties5 = new GraphElementProperties();
		properties5.insertProperty("name", "Bruna");
		GraphNode n5 = builder.createNode("person", properties5);
		
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