package daniel.videoclub.mainpack;
/**
 * Enum with the resource paths
 * @author Pelicano
 *
 */
public enum FileList{
	
	USERLOGIN("Sources/UserLog/UsersLogin.txt"),
	FILMS("Sources/Catalog/Films.txt"),
	TVSHOWS("Sources/Catalog/TVShows.txt"),
	IMAGES("Sources/Images/");
	
	 
	private String path;
	
	
	private FileList(String path) {
		
		this.path = path;
	}
	
	public String getPath() {
		
		return path;
	}
	
}