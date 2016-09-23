package br.unicamp.ic.lis.tograph.tests.etl.spreadsheet;

import java.io.File;
import java.util.Arrays;

import br.unicamp.ic.lis.ddex.spreadsheet.ReadingStrategies;
import br.unicamp.ic.lis.ddex.spreadsheet.director.xls.XLSReader;
import br.unicamp.ic.lis.tograph.concretebuilder.neo4j.neo4jrest.Neo4jRestConcreteBuilder;
import br.unicamp.ic.lis.tograph.etl.spreadsheet.SpreadsheetToGraph;

public class Test1 {

	public static void main(String[] args) {

		String neo4juri = "http://localhost:7474/db/data";
		String csvFolderPath = "/home/matheus/temp/misc/xls/";
		String extension = ".xls";

		Neo4jRestConcreteBuilder neo4j = new Neo4jRestConcreteBuilder(neo4juri, "neo4j", "neo4j");
		neo4j.setDebubMessage(true);

		try {
			String fileName;
			File folder = new File(csvFolderPath);
			File[] files = folder.listFiles();
			Arrays.sort(files);

			for (int i = 0; i < files.length; i++) { 

				if (files[i].isFile()) {
					fileName = files[i].getName();
					if (fileName.toLowerCase().endsWith(extension)) {

						System.out.println("Running for file: " + csvFolderPath + fileName);

						XLSReader csvReader = new XLSReader(csvFolderPath + fileName);
						SpreadsheetToGraph neo4jbuilder = new SpreadsheetToGraph(neo4j, "xls");
						csvReader.build(neo4jbuilder, ReadingStrategies.RowAndCell);

						System.out.println("Done! URI:" + neo4jbuilder.getObjectURI());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
