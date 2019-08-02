package blud.util;

import java.util.HashMap;

public class Loader<T> {
	protected final String
		package_name;
	protected final HashMap<String, Class<T>>
		index = new HashMap<String, Class<T>>();
	
	public Loader(String package_name) {
		this.package_name = package_name;	
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> load(String name) {
		Class<T> type = index.get(name);
		if(type == null) {
			try {
				type = (Class<T>)Class.forName(this.package_name + name);
				index.put(name, type);
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		}
		return type;
	}	
	
	public T get(String name) {
		Class<T> type = load(name);
		try {
			return type.newInstance();
		} catch (InstantiationException ie) {
			ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return null;
	}	
}
