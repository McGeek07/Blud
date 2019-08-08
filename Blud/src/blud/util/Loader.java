package blud.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Loader<T> {
	private static final String
		INDEX = "index";
	protected final HashMap<String, Class<T>>
		index = new HashMap<String, Class<T>>();
	
	@SuppressWarnings("unchecked")
	public T load(String package_name, String class_name) {
		Class<T> type = index.get(class_name);
		if(type == null)
			try {
				type = (Class<T>)Class.forName(package_name + class_name);
				index.put(class_name, type);
			} catch(Exception e) {
				e.printStackTrace();
			}
		if(type != null)
			try {
				return (T)type.newInstance();
			} catch(Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public List<T> load(String package_name, List<T> list) {
		LinkedList<String> class_names = new LinkedList<>();
		Util.parseFromResource(getClass(), INDEX, class_names);		
		for(String class_name : class_names) 
			if(!class_name.startsWith("//"))
				list.add(load(package_name, class_name));
		return list;		
	}
}
