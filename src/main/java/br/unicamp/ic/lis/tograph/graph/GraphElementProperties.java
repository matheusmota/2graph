/**
 * 
 */
package br.unicamp.ic.lis.tograph.graph;

import java.util.ArrayList;

/**
 * @author matheus
 *
 */
public class GraphElementProperties extends ArrayList<GraphElementProperty>  {

	public GraphElementProperties() {
		super();	
	}

	public GraphElementProperties(GraphElementProperty p) {
		super();
		this.add(p);
	}
	
	public GraphElementProperty getProperty(String key)
	{
		for(GraphElementProperty e : this)
		{
			if(e.getKey() == key)
				return e;
		}
		return null;
	}
	
	public boolean contains(String key)
	{
		for(GraphElementProperty e : this)
		{
			if(e.getKey() == key)
				return true;
		}
		return false;
	}
	
	public void insertProperty(String key, String value)
	{
		this.add(new GraphElementProperty(key, value));
	}
}
