import java.util.TreeMap;

public class Task{
	private TreeMap<String, Object> map;

	public Task(){
		map = new TreeMap<>();
	}

	public void add(String s, Object o){
		map.put(s, o);
	}

	public Object get(String s){
		return map.get(s);
	}
}

