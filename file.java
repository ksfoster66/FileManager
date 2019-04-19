//File interface, Determines all common attributes that directory and data files should have

public interface file{

	
	public String getName();
	public void setName(String s);
	public file getParent();
	public void setParent(file f);
	public int getSize();
	public void updateSize(int n);
	public int calcSize(boolean source);

}
