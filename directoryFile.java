import java.util.HashMap;
import java.util.Set;
import java.util.Collections;

public class directoryFile implements file{

	
	private String name;
	private file parent;
	
	private HashMap<String, file> contents = new HashMap<String, file>();
	
	private int size = 0;
	
	//Overriden methods
	
	
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
	
	public file getParent(){
		return parent;
	}
	
	public void setParent(file p){
		parent = p;
	}
	
	public int getSize(){
		return size;
	}
	
	//Constructor
	public directoryFile(String s, file p){
		
		name = s;
		parent = p;
		
		updateSize(3);
	}
	
	//Copy constructor
	public directoryFile( directoryFile f, file p, int nameInUse){
		if (nameInUse == 1) name = f.getName() + "-copy";
		else if (nameInUse > 1) name = f.getName() + "-copy-" + nameInUse;
		else name = f.getName();
		parent = p;
		updateSize(3);
		Set<String> t = contents.keySet();
		String s[] = t.toArray(new String[0]);
		for (int i = 0; i < s.length; i++){
			
			if (contents.get(s[i]) instanceof dataFile){
				dataFile c = (dataFile)contents.get(s[i]);
				file n = new dataFile(c, this);
				contents.put(n.getName(), n);
			}
			if( contents.get(s[i]) instanceof directoryFile){
				directoryFile c = (directoryFile) contents.get(s[i]);
				file n = new directoryFile(c, this);
				contents.put(n.getName(), n);
			}
		}
	}
	
	public directoryFile( directoryFile f, file p){//To be used by  the other copy constructor
		name = f.getName();
		parent = p;
		updateSize(3);
		Set<String> t = contents.keySet();
		String s[] = t.toArray(new String[0]);
		for (int i = 0; i < s.length; i++){
			
			if (contents.get(s[i]) instanceof dataFile){
				dataFile c = (dataFile)contents.get(s[i]);
				file n = new dataFile(c, this);
				contents.put(n.getName(), n);
			}
			if( contents.get(s[i]) instanceof directoryFile){
				directoryFile c = (directoryFile) contents.get(s[i]);
				file n = new directoryFile(c, this);
				contents.put(n.getName(), n);
			}
		}
	}
	
	//create
	public boolean createData(String s){
		if (contents.containsKey(s)) return false;
		file f = new dataFile(s, this);
		contents.put(s, f);
		return true;
	}
	
	public boolean createDirectory( String s){
		if (contents.containsKey(s)) return false;
		file f = new directoryFile(s, this);
		contents.put(s, f);
		return true;
	}
	
	//Paste
	public void paste(file f){
		int exists = 0;
		Set<String> t = contents.keySet();
		String s[] = t.toArray(new String[0]);
		String temp = f.getName();
		for (int i = 0; i < s.length; i++){
			if (temp.equals(s[i])) exists++;
			else if (s[i].contains(temp + "-copy")) exists++;
		}
		if (f instanceof dataFile){
			dataFile d = (dataFile)f;
			file x = new dataFile(d, this, exists);
			contents.put(x.getName(), x);
			
		}
		else if(f instanceof directoryFile){
			directoryFile d = (directoryFile)f;
			file x = new directoryFile(d, this, exists);
			contents.put(x.getName(), x);
		}
		
	}
	
	//search and get
	public file findFile(String s){
		if (contents.containsKey(s)) return contents.get(s);
		return null;
	}
	
	//List all contents by name
	public String[] list(){
		
		Set<String> t = contents.keySet();
		String s[] = t.toArray(new String[0]);
		return s;
	}
	
	//update
	public void updateSize(int n){
		size += n;
		if (parent != null)
			parent.updateSize(n);
	}
	
	public int calcSize(boolean source){	
		int temp = size;
		size = 3;
		//recursize size calcultation.
		Set<String> t = contents.keySet();
		String s[] = t.toArray(new String[0]);
		for (int i = 0; i < s.length; i++){
			size += contents.get(s[i]).calcSize(false);
		}
		temp -= size;
		if(source == true){
			
			if (parent != null) parent.updateSize(temp);	
			return -1;
		}
		else return size;
		
	}
	
	
	//delete
	
	public void deleteFile(String s){
		updateSize(-contents.get(s).getSize());
		contents.remove(s);
	}
	
	public void deleteThis(){
		contents.clear();
		parent.updateSize(-size);
	}
	

}
