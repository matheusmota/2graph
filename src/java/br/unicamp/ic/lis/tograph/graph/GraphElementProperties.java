/**
 * 
 */
package br.unicamp.ic.lis.tograph.graph;

import java.util.LinkedHashMap;

/**
 * @author matheus
 *
 */
public class GraphElementProperties extends LinkedHashMap<String, Object> {

	private float valueFloat;

	public GraphElementProperties() {
		super();	
	}

	public GraphElementProperties(String key, Object value) {
		super();
		this.put(key, value);
	}

}
