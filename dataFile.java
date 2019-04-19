import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.Collections;

public class dataFile implements file{

	
	private String name;
	private file parent;
	
	private Semaphore IO = new Semaphore(1);
	
	protected ArrayList<String> contents = new ArrayList<String>();
	
	private int size = 0;
	
	private int lastLineRead = 0;
	
	
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
	
	public file getParent(){
		return parent;
	}
	
	public void setParent(file f){
		parent = f;
	}
	
	public int getSize(){
		return size;
	}
	
	//constructor
	public dataFile( String s, file p){
		
		name = s;
		parent = p;
		updateSize(4);
	}
	
	//copyconstructor
	public dataFile(dataFile f, file p, int nameInUse){
	
		parent = p;
		
		if (nameInUse == 1) name = f.getName() + "-copy";
		else if (nameInUse > 1) name = f.getName() + "-copy-" + nameInUse;
		else name = f.getName();
		updateSize(4);
		for (int i = 0; i < f.contents.size(); i++){
			contents.add(f.contents.get(i));
			updateSize(1);
		}
	}
	
	public dataFile(dataFile f, file p){
		parent = p;
		
		name = f.getName();
		updateSize(4);
		for (int i = 0; i < f.contents.size() -4; i++){
			contents.add(f.contents.get(i));
			updateSize(1);
		}
	}
	
	
	//read
	public String read(int n){
		if (n < contents.size()){
			lastLineRead = n+1;
			return contents.get(n);
		}
		else return null; 	
	}
	
	public String read(){
		if (lastLineRead < contents.size())
			return contents.get(lastLineRead++);
		else return null;
	}
	
	public void reset(){
		lastLineRead = 0;
	}
	
	//write
	public void write(String s){
		contents.add(s);
		updateSize(1);
	}
	
	public void write(String s, int n){
		contents.add(n,s);
		updateSize(1);
	}
	
	//delete line
	public void deleteLine(int n){
		contents.remove(n);
		
		updateSize(-1);
	}
	
	//update size
	public int calcSize(boolean source){
		int n = 0;
		n = contents.size() + 3;
		size = n;
		return size;
	}
	
	public void updateSize(int n){
		size += n;
		parent.updateSize(n);
	}
	
	//Search
	public int searchPos(String s){
		for (int i = 0; i < size; i++){
			if (contents.get(i).contains(s)) return i; 
		}
		return -1;//Not found
	}
	
	public String searchLine(String s){
		for (int i = 0; i < size; i++){
			if (contents.get(i).contains(s)) return contents.get(i); 
		}
		return null;//not found
	}
	
	//Semaphore
	
	public void acquire(){
		try{
			IO.acquire();
		}catch(InterruptedException e){
		
		}
	}
	
	public void release(){
		IO.release();
		
	}
}
