package daniel.videoclub.mainpack.users;



import daniel.videoclub.mainpack.FileList;
import daniel.videoclub.mainpack.FileManager;

public class Chief extends Employee{

	public Chief() {
		
		//EL 0 ES SIEMPRE EL JEFE
		
		super(FileManager.loadFileDataIntoList(FileList.USERLOGIN).get(0)); 
		
	}

}
