public class filetest{

	public static void main(String args[]){
	
		System.out.println("Creating a new file manager with space of '1000'");
		fileManager fm = new fileManager(1000);
		System.out.print("Current used space: ");
		System.out.println(fm.getUsed() + "\n");
		
		
		System.out.println("Creating a Data file named 'text'");
		fm.createData("text");
		System.out.print("Current used space: ");
		System.out.println(fm.getUsed() + "\n");
		
		
		System.out.print("Creating a directory named 'folder' and setting it to be the working directory. Current size: ");
		fm.createDirectory("folder");;
		fm.setCWD("folder");
		System.out.println(fm.getUsed() + "\n");
		
		System.out.print("Creating a data file 'words' in the current directory. Used space: ");
		fm.createData("words");
		System.out.println(fm.getUsed() + "\n");
		
		System.out.println("Opening the file 'words' and writing text into it.");
		fm.openFile("words");
		fm.write("stuff stuff");
		fm.write("test");
		
		System.out.println("Reading the contents of the current file");
		System.out.println(fm.read());
		System.out.println(fm.read());
		
		System.out.println("Reseting the cursor, rereading the file, then reading the second line(line 1). Finally closing the open file");
		fm.reset();
		System.out.println(fm.read());
		System.out.println(fm.read());
		System.out.println(fm.read(1));
		fm.close();
		
		System.out.println("Current used space: " + fm.getUsed() + "\n");
		
		System.out.println("Printing the current working directory");
		String words[] = fm.list();
		for (int i = 0; i < words.length; i++){
			System.out.println(words[i]);
		}
		
		System.out.println("Attempting to read with no file open: " + fm.read(0) + "\n");
		
		System.out.println("Copying the words file, moving back to root directory, and pasting");
		fm.copy("words");
		fm.moveUp();
		fm.paste();
		
		System.out.println("Current directory: " + fm.getCWD() + "\n");
		fm.close();
		words = fm.list();
		
		System.out.println("Contents of current directory: ");
		for (int i = 0; i < words.length; i++){
			System.out.println(words[i]);
		}
		System.out.println("Current space used: " + fm.getUsed() + "\n");
		
		System.out.println("Opening the file words in the root directory");
		fm.openFile("words");
		System.out.println("Current open file: " + fm.getOpen());
		System.out.println("The second line of the current open file: " + fm.read(1));
		fm.close();
		
		System.out.println();
		
		System.out.println("Deleting the file 'text' and then reprinting the current contents of this directory\n");
		fm.deleteFile("text");
		words = fm.list();
		
		for (int i = 0; i < words.length; i++){
			System.out.println(words[i]);
		}
		
		System.out.println();
		System.out.println("Current used space: " + fm.getUsed() + "\n");
		
		System.out.print("Recalculating the used size and outputting the result: ");
		fm.calcSize();
		System.out.println(fm.getUsed() + "\n");
		
		System.out.println("Searching for the location of the file 'words'. Result: " + fm.whereIsFile("words"));
		
		System.out.println("Copying the directory 'folder', pasting it twice, and then printing the contents of the current working directory");
		fm.copy("folder");
		fm.paste();
		fm.paste();
		words = fm.list();
		
		for (int i = 0; i < words.length; i++){
			System.out.println(words[i]);
		}
		
		System.out.println();
		fm.setCWD("folder-copy");
		System.out.println("Current working directory: " + fm.getCWD());
		words = fm.list();
		
		for (int i = 0; i < words.length; i++){
			System.out.println(words[i]);
		}
		
	
	}

}
