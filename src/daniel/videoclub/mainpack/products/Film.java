package daniel.videoclub.mainpack.products;

import daniel.videoclub.mainpack.FileList;
import daniel.videoclub.mainpack.FileManager;
import daniel.videoclub.mainpack.Filter;

public class Film extends Product {
	
	private String genre;
	private String subGenre;

	public Film() {
		
		super();
		
		this.genre = "";
		
		this.subGenre = "";
	}
	
	public Film(String lineToExtractData) {
		
		super(lineToExtractData);
		
		this.genre =  FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.GENRE);
		this.subGenre =  FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.SUBGENRE);

		if(!thereIsStock()) {
			
			this.available = false;
			FileManager.editFieldFromFileWithFilter(getposInFile(), FileList.FILMS, getProductWrited(), "false", Filter.AVAILABILITY);
		}	
	}

	

	public String getGenre() {
		
		return genre;
	}
	
	public String getSubGenre() {
		
		return subGenre;
	}
	
	public String getProductWrited() {
		
		return name + "#"+  releaseYear + "#" + director + "#" + genre +"#" + subGenre +"#" + available +"#"+ stock +"#" + numOfRented + "#"+ totalScore + "#" + totalquantScore + "#";
		
	}
	
	
	@Override
	public String toString() {
		
		if(String.valueOf(scoreAverage).equals("NaN") ) {
			
			return   " " + name + " " +  "("+releaseYear+")" + " " + director + " " + genre + " " +subGenre +  " " + "Stock: "+stock;
			
		}
		
		
		return   " " + name + " " +  "("+releaseYear+")" + " " + director + " " + genre + " " + subGenre + " "+ "Stock: "+stock+ " Score: " + String.format("%.2f",scoreAverage);
	}

}
