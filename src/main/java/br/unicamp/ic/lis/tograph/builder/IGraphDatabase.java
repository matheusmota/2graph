package br.unicamp.ic.lis.tograph.builder;

import java.util.HashMap;

/**
 * Interface for server commands
 * @author Matheus, labrax
 *
 */
public abstract class IGraphDatabase{
	HashMap<String, String> settings = new HashMap<String, String>();
/**
 * Start the server
 * @throws Exception
 */
	public abstract void startServer() throws Exception;
/**
 * Stop the server
 * @throws Exception
 */
	public abstract void stopServer() throws Exception;
/**
 * Check if the server is running
 * @return true if running
 * @throws Exception
 */
	public abstract boolean isRunning() throws Exception;
/**
 * Add a setting to the server
 * @param key is the setting
 * @param value is the value
 * @throws Exception if fail
 */
	public void addSetting(String key, String value) throws Exception
	{
		settings.put(key, value);
	}
/**
 * Get server setting
 * @param key is the key searched
 * @return is the value
 * @throws Exception if fail
 */
	public String getSetting(String key) throws Exception
	{
		return settings.get(key);
	}
/**
 * Check if has a setting
 * @param key the setting
 * @return true if contains
 * @throws Exception if fail
 */
	public boolean contains(String key) throws Exception
	{
		return settings.containsKey(key);
	}
}
