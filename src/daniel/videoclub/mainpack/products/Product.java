package daniel.videoclub.mainpack.products;

import java.util.ArrayList;
import java.util.List;

import daniel.videoclub.mainpack.FileList;
import daniel.videoclub.mainpack.FileManager;
import daniel.videoclub.mainpack.Filter;

public  class Product {
	
	protected String name;
	protected String releaseYear;
	protected String director;
	protected String stock;
	
	protected double scoreAverage;
	protected double totalScore;
	protected double totalquantScore;
	
	private int posInFile = 0;
	private int rating;
	protected int numOfRented;
	
	protected boolean available;
	
	private static int lastPos;
	
	static {
		lastPos = 0;
	}
	
	public Product() {
		
		this.name = "";
		this.releaseYear = "";
		this.director = "";
		this.available = true;
		this.numOfRented = 0;
	
		this.stock = "0";
		posInFile=lastPos;
		lastPos++;
	}
	
	protected Product(String lineToExtractData) {
		
		this.name = FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.TITTLE);
		this.releaseYear = FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.RELEASEYEAR);
		this.director = FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.DIRECTOR);	
		this.stock = FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.STOCK);
		this.numOfRented = Integer.parseInt(FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.NUMRENTED));
		this.totalScore= Double.parseDouble((FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.SCORESUM)));
		this.totalquantScore = Double.parseDouble(FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.NUMOFRESULT));
		this.scoreAverage = totalScore /totalquantScore;
		
		posInFile=lastPos;
		lastPos++;
		
		if(FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.AVAILABILITY).equals("true")) {
			
			this.available = true;
		}else {
			
			this.available = false;
		}	
	}
	
	public int getNumOfRented() {
		
		return numOfRented;
	}
	public boolean thereIsStock() {
		
		
		return Integer.parseInt(stock) > 0;
		
	}
	public  void SubsPosition() {
		
		posInFile--;
	}
	
	public int getposInFile() {
		
		return posInFile;
	}
	
	public String getName() {
		
		return name;
	}
	public String getDirector() {
		
		return director;
	}
	public String getReleaseYear() {
		
		return releaseYear;
	}
	public int getRating() {
		
		return rating;
	}
	public void setAvailable(boolean bool){
		
		this.available = bool;
	}
	public boolean isAvailable() {
		
		return available;
	}
	
	public void addResult(Double newScore, FileList file) {
		
		totalScore+= newScore;
		totalquantScore++;
		
		FileManager.editFieldFromFileWithFilter(getposInFile(), file, getProductWrited(), String.valueOf(totalScore), Filter.SCORESUM);	
		FileManager.editFieldFromFileWithFilter(getposInFile(), file, getProductWrited(), String.valueOf(totalquantScore), Filter.NUMOFRESULT);	
	}

	public int getStock() {
		
		return Integer.parseInt(stock);
	}
	public String getProductWrited() {
		
		return name + "#"+  releaseYear + "#" + director +"#" + available +"#"+ stock + "#"+ numOfRented + "#"+ totalScore + "#" + totalquantScore + "#";
		
	}
	public void sumStock(FileList file) {
		
		this.stock = String.valueOf( Integer.parseInt(stock) + 1);
		
		FileManager.editFieldFromFileWithFilter(getposInFile(), file, getProductWrited(), stock, Filter.STOCK);
		FileManager.editFieldFromFileWithFilter(getposInFile(), file, getProductWrited(), String.valueOf(numOfRented-1), Filter.NUMRENTED);
		
		this.numOfRented--;
		
	}
	public void subStock(FileList file) {
		
		this.stock = String.valueOf( Integer.parseInt(stock) - 1);

		FileManager.editFieldFromFileWithFilter(getposInFile(), file, getProductWrited(), stock, Filter.STOCK);
		FileManager.editFieldFromFileWithFilter(getposInFile(), file, getProductWrited(), String.valueOf(numOfRented+1), Filter.NUMRENTED);
	}
	public static void reset() {
		
		lastPos = 0;	
	}
	@Override
	public String toString() {
		
		if(String.valueOf(scoreAverage).equals("NaN") ) {
			
			return   " " + name + " " +  "("+releaseYear+")" + " " + director +  " " + "Stock: "+stock;	
		}
		
		return   " " + name + " " +  "("+releaseYear+")" + " " + director + " "+ "Stock: "+stock+ " Score: " + String.format("%.2f",scoreAverage);
	}
	

}
