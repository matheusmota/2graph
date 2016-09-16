package br.unicamp.ic.lis.tograph.concretebuilder.neo4j.neo4jbatchinserter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserterIndex;
import org.neo4j.unsafe.batchinsert.BatchInserterIndexProvider;

import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;
import br.unicamp.ic.lis.tograph.builder.IGraphDatabase;
import br.unicamp.ic.lis.tograph.graph.GraphElement;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperties;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;
import br.unicamp.ic.lis.tograph.graph.GraphRelation;

/**
 * This concrete builder is used to create a database lightning fast using Neo4j
 * Batch Inserter.
 * 
 * @author labrax
 *
 */
public class Neo4jBatchInserterConcreteBuilder extends IGraphDatabase implements IGraphBuilder {
	private BatchInserter inserter = null;
	private BatchInserterIndexProvider indexProvider;
	private BatchInserterIndex index;

	/**
	 * 
	 * @param databaseOutputDirectory
	 *            requires that the output folder is empty and you have
	 *            permission to write.
	 */
	public Neo4jBatchInserterConcreteBuilder() throws Exception {

	}

	// from:
	// http://stackoverflow.com/questions/3775694/deleting-folder-from-java
	private static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	public void startServer() throws Exception {
		if (!this.contains("db_dir"))
			throw new Exception("Need to add server directory.");
		else
			System.out.println("Database output dir is: " + this.getSetting("db_dir"));

		if (this.contains("restart_neo4j_with"))
			System.out.println("Will restart neo4j after use." + this.getSetting("restart_neo4j_with"));

		if (this.contains("delete_old_data") && this.getSetting("delete_old_data").equals("true")) {
			System.out.println("Will delete old data.");
			deleteDirectory(new File(this.getSetting("db_dir")));

			new File(this.getSetting("db_dir")).mkdir();
		}

		if (this.contains("restart_neo4j_with")) {
			Process p = Runtime.getRuntime().exec(this.getSetting("restart_neo4j_with") + " stop");
			p.waitFor();
			System.out.println("Stopped Neo4j!");
		}

		// inserter = BatchInserters.inserter(new
		// File(this.getSetting("db_dir")).getAbsolutePath());

		indexProvider = new LuceneBatchInserterIndexProvider(inserter);
		// index = indexProvider.nodeIndex("Node-exact",
		// MapUtil.stringMap("type", "exact"));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void stopServer() throws Exception {
		shutdown();
	}

	public boolean isRunning() throws Exception {
		return false;
	}

	/**
	 * This is required to properly save the generated graph. Not needed to run
	 * if program closes.
	 */
	private void shutdown() throws Exception {
		if (inserter != null) {
			inserter.shutdown();
			inserter = null;
		}
		if (indexProvider != null) {
			indexProvider.shutdown();
			indexProvider = null;
		}

		if (this.contains("restart_neo4j_with")) {
			Process p = Runtime.getRuntime().exec(this.getSetting("restart_neo4j_with") + " start");
			p.waitFor();
			System.out.println("Started Neo4j!");
		}
	}

	public GraphNode createNode(String label, GraphElementProperties properties) throws Exception {
		HashMap<String, Object> nodeProperties = new HashMap<String, Object>();
		for (GraphElementProperty property : properties) {
			nodeProperties.put(property.getKey(), property.getValue());
		}

		// Long created = inserter.createNode(nodeProperties,
		// DynamicLabel.label(label));
		// return new GraphNode(new URI(String.valueOf(created)), label, this);
		return null;
	}

	public GraphRelation createRelation(GraphNode startNode, String label, GraphNode endNode) throws Exception {
		// Long created =
		// inserter.createRelationship(Long.parseLong(startNode.getUri().toString()),
		// Long.parseLong(endNode.getUri().toString()),
		// DynamicRelationshipType.withName(label), null);
		// return new GraphRelation(new URI(String.valueOf(created)), label,
		// this);
		return null;	
	}

	@Override
	public GraphNode createNode(String label) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphRelation> getNodeRelations(GraphNode node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphRelation> getNodeInRelations(GraphNode node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphRelation> getNodeOutRelations(GraphNode node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphNode getStartNodeOfRelation(GraphRelation relation) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphNode getEndNodeOfRelation(GraphRelation relation) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addLabel(GraphElement element, String label) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getLabels(GraphElement element) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addProperty(GraphElement element, GraphElementProperty property) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProperties(GraphElement element, GraphElementProperties property) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setProperty(GraphElement element, GraphElementProperty property) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setProperties(GraphElement element, GraphElementProperties properties) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GraphElementProperties getProperties(GraphElement element) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
