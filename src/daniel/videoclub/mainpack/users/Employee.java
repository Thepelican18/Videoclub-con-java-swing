package daniel.videoclub.mainpack.users;

import daniel.videoclub.mainpack.FileManager;
import daniel.videoclub.mainpack.Filter;

public class Employee {

	private int ID;
	private String username;
	private String password;
	private int posInFile = 0;
	



	private static int lastID;
	private static int lastPos;

	static {
		lastPos = 0;
	}
	
	
	public Employee() {
		this.ID = 1000;
		}
	
	public Employee(String line){
		
		this.username = FileManager.extractDataFromProductWithFilter(line, Filter.USERNAME);
		this.password = FileManager.extractDataFromProductWithFilter(line, Filter.PASSWORD);
		posInFile=lastPos;
		lastPos++;
		this.ID = 0;
	
		
		this.ID = 1000 + posInFile;
		
		lastID++;
		
	}



	@Override
	public String toString() {
		return  ID + " " + username + " " + password;
	}



	public int getID() {
		
		return ID;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getposInFile() {
		
		return posInFile;
	}
public String getEmployeeWrited() {
		
		return ID +"#"+username + "#"+  password + "#";
		
	}

public static void reset() {
	lastPos = 0;
}

public static int getLastID() {
	
	return lastPos;
}
	
	
}
