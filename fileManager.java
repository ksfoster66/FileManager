//Have reference to open file, keep track of where the program is in reading the file in.


public class fileManager{

	private final int capacity;
	
	private int usedSpace;
	
	private int unusedSpace;
	
	private directoryFile root;
	
	private directoryFile cwd;
	
	private dataFile openFile;
	
	private file copybuffer;
	
	//constructor
	public fileManager(int cap){
		capacity =  cap;
		root = new directoryFile("root", null);
		usedSpace = root.getSize();
		unusedSpace = capacity - root.getSize();
		
		cwd = root;
	}
	
	//add file to open directory.
	public boolean createData(String name){
		if ( usedSpace + 4 > capacity) return false;
		cwd.createData(name);
		updateSpace();
		return true;
	}
	
	public boolean createDirectory(String name){
		if ( usedSpace + 3 > capacity) return false;
		cwd.createDirectory(name);
		updateSpace();
		return true;
	}
	
	//remove file from open directory.
	public void deleteFile(String s){
		if(cwd.findFile(s) != null);
		cwd.deleteFile(s);
		updateSpace();
	}
	
	//search for a file
		//within open directory
		public boolean hasDirectoryFile(String s){
			file f = cwd.findFile(s);
			if (f instanceof directoryFile) return true;
			return false;
		}
		
		public boolean hasDataFile(String s){
			file f = cwd.findFile(s);
			if (f instanceof dataFile) return true;
			return false;
		}
		
		//within entire system. Searches for file and returns location
		public String whereIsFile(String s){
			String[] l = root.list();
			for (int i = 0; i < l.length; i++){
				if (s.equals(l[i])) return "root/" +l[i]; 
			}
			for (int i = 0; i < l.length; i++){
				if (root.findFile(l[i]) instanceof directoryFile){
					directoryFile f = (directoryFile) root.findFile(l[i]);
					String t = whereIsFile(f, s);
					if (t != null) return "root/" + t;
				}
			}
			return null;//If file is not found null is returned
		}
		
		public String whereIsFile(directoryFile f, String s){
			String[] l = f.list(); 
			for (int i = 0; i < l.length; i++){
				if (s.equals(l[i])) return f.getName() + "/" +l[i]; 
			}
			for (int i = 0; i < l.length; i++){
				if (f.findFile(l[i]) instanceof directoryFile){
					directoryFile d = (directoryFile) f.findFile(l[i]);
					String t = whereIsFile(d, s);
					if (t != null) return  f.getName() + "/" + t;
				}
			}
			return null;
		}
		
		
		//list everything in current directory
		
		public String[] list(){
			
			return cwd.list();
		}
	
	//change working directory
	public boolean moveUp(){
		if (cwd.getParent() != null){
			cwd = (directoryFile)cwd.getParent();
			return true;
		}
		return false;
	}
	
	public void moveRoot(){
		cwd = root;
	}
	
	public boolean setCWD(String s){
		file f = cwd.findFile(s);
		if (f instanceof directoryFile){
			cwd = (directoryFile)f;
			return true;	
		}
		return false;
	}
	
	//open a data file
	public boolean openFile(String s){
		file f = cwd.findFile(s);
		if (f instanceof dataFile){
			openFile = (dataFile)f;
			return true;	
		}
		return false;
	}
	
	//Validate size
	public void calcSize(){
		root.calcSize(true);
		updateSpace();
	}
	
	
	//close a data file
	public void close(){
		openFile = null;
	}
	//getters
	public int getCapacity(){
		return capacity;
	}
	
	public int getUsed(){
		updateSpace();
		return usedSpace;
	}
	
	public int getUnused(){
		updateSpace();
		return unusedSpace;
	}
	
	public String getCWD(){
		return cwd.getName();
	}
	
	public String getOpen(){
		return openFile.getName();
	}
	
	//CWD getters for basic info
	public int getCWDSize(){
		return cwd.getSize();
	}
	
	//Open file getters for basic info
	public int getOpenSize(){
		return openFile.getSize();
	}
	
	
	//open file modifiers
		
		//read from an open file
		public String read(){//If nothing is there to be read will return null
			if (openFile == null) return null;
			return openFile.read();
		}
		
		public String read(int n){//If nothing is there to be read will return null
			if (openFile == null) return null;
			return openFile.read(n);
		}
		
		public void reset(){
			if(openFile != null)
				openFile.reset();
		}
		
		//write to a file
		public boolean write(String s){
			if (openFile == null) return false;
			if (usedSpace + 1 > capacity) return false;
			openFile.acquire();
			openFile.write(s);
			openFile.release();
			updateSpace();
			return true;
		}
		
		public boolean write(String s, int n){
			if (openFile == null) return false;
			if (usedSpace + 1 > capacity) return false;
			openFile.acquire();
			openFile.write(s, n);
			openFile.release();
			updateSpace();
			return true;
		}
		
		public boolean replaceline(String s, int n){
			if (openFile == null) return false;
			openFile.acquire();
			openFile.deleteLine(n);
			openFile.write(s,n);
			openFile.release();
			return true;
		}
		
		public boolean deleteLine(int n){
			if (openFile == null) return false;
			openFile.acquire();
			openFile.deleteLine(n);
			openFile.release();
			updateSpace();
			return true;
		}
		
		public int getLineNum(String s){
			if (openFile == null) return -1;
			return openFile.searchPos(s);
		}
		
	//copy/paste file methods
		
		public void copy(String s){
			if(cwd.findFile(s) != null) copybuffer = cwd.findFile(s);
		}
		
		public void copy(){
			if(openFile != null) copybuffer = openFile;
		}
		
		public boolean paste(){
			if (copybuffer == null) return false;
			if (copybuffer.getSize() + usedSpace > capacity) return false;
			cwd.paste(copybuffer);
			updateSpace();
			return true;
		}
	
	//update space
	private void updateSpace(){
		usedSpace = root.getSize();
		unusedSpace = capacity - usedSpace;
	}	

}
