package models;

public abstract class Model implements Cloneable {
	/**
	 * Get the value of the property for the given key
	 * @param key of the property
	 * @return  property value for the given key
     */
	public abstract Object get(String key) throws IllegalArgumentException;
	/**
	 * Set the value of the property for the given key
	 * @param key of the property
     */
	public abstract void set(String key, Object value) throws IllegalArgumentException;
}
