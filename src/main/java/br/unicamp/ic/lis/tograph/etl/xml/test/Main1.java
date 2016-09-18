package br.unicamp.ic.lis.tograph.etl.xml.test;

import java.net.URI;

public class Main1 {

	public static void main(String[] args) {
		String filePath = "/home/matheus/Desktop/varanus/indicius.sdd.xml";

		try {

			URI uri = new URI("asd");

			System.out.println(uri.getPath());
			// XMLParser parser = new XMLParser(filePath);
			// parser.print();
			// parser.produceCypher(new FileOutputStream(new
			// File(filePath+".txt")));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
