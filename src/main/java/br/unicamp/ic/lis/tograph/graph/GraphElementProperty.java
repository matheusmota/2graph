/**
 * 
 */
package br.unicamp.ic.lis.tograph.graph;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Matheus Mota
 *
 */
public class GraphElementProperty implements Map.Entry<String, String>{

	private String key;
	private String value;
	private float valueFloat;
	private boolean isValueFloat;

	public GraphElementProperty(String key, String value) {
		this.isValueFloat = false;
		this.key = key;	
		this.value = value;
	}

	public GraphElementProperty(String key, float value) {
		this.isValueFloat = true;
		this.key = key;
		this.valueFloat = value;
	}

	public float getValueFloat() {
		return valueFloat;
	}

	public boolean isValueFloat() {
		return isValueFloat;
	}

	public String getValue() {
		return value;
	}
	
	public String setValue(String value) {
		//this.value = value;
		return this.value;
	}

	public String getKey() {
		return key;
	}

	public String toString() {
		return this.key + " =  \"" + this.value + "\"";

	}
}
