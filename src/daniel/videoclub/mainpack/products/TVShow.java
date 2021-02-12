package daniel.videoclub.mainpack.products;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import daniel.videoclub.mainpack.FileList;
import daniel.videoclub.mainpack.FileManager;
import daniel.videoclub.mainpack.Filter;

public class TVShow extends Product {
	
	private List<Season> seasons;
	private Integer totalSeasons;
	
	public TVShow() {
		
		super();
		this.totalSeasons = 1;
	}
	
	
	public TVShow(String lineToExtractData) {
		
		super(lineToExtractData);
		
		seasons = new ArrayList<>();
		
		totalSeasons = Integer.parseInt(FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.NUMOFSEASON));
		String[] seasonExtracted = FileManager.extractDataFromProductWithFilter(lineToExtractData, Filter.SEASONS).split("@");
		
		for (int i = 1; i < seasonExtracted.length ;i++) {
			
			String chapter[] =seasonExtracted[i].split("_");
			seasons.add(new Season(chapter,scoreAverage));
		}		
	}
	public String createSeasons(Integer numOfSeason) {
		
		seasons = new ArrayList<>();

		for (int i = 0;i<numOfSeason;i++) {
			
			int numOfChapter =Integer.parseInt(JOptionPane.showInputDialog("How many episodes will season "+ (i+1) +" have?" ));
			seasons.add(new Season(numOfChapter,0.0));
		}
		return getTVShowSeasonWrited();
	}
	
	
	public String getTVShowSeasonWrited() {

		String stringAux = "";

		for (Season season : getSeasons()) {
			stringAux += "@";

			for (daniel.videoclub.mainpack.products.TVShow.Season.Chapter chapter : season.chapters) {

				stringAux += chapter;

				if (!season.chapters.get(season.chapters.size() - 1).equals(chapter)) {

					stringAux += "_";
				}

			}

		}
		return stringAux;
	}
	
	public String getProductWrited() {
		
		String season = getTVShowSeasonWrited();
		
		return name + "#"+  releaseYear + "#" + director + "#" + totalSeasons +"#"+ "too much" +"#" + available +"#"+ stock + "#" +numOfRented + "#"+ totalScore + "#" + totalquantScore + "#" + season + "#";
		
	}
	public int getNumOfSeasons(){
		return totalSeasons;
	}
	
	@Override
	public String toString() {
		
		if(String.valueOf(scoreAverage).equals("NaN") ) {
			
			return   " " + name + " " +  "("+releaseYear+")" + " " + director + " "  + "Seasons: "+totalSeasons + " " + "Stock: "+stock;
		}
		
		return   " " + name + " " +  "("+releaseYear+")" + " " + director + " " + "Seasons: "+totalSeasons +  " "+ "Stock: "+stock+ " Score: " + String.format("%.2f",scoreAverage);
	}

	public List<Season> getSeasons() {

		return seasons;
	}
	public String getChapterFromSeason(int season, int numOfChapter) {

		return seasons.get(season).getChaptersList().get(numOfChapter).getTittle();
	}
	public String getAllChaptersFromSeason(int season) {
		
		StringBuilder chapterList = new StringBuilder();
		for(daniel.videoclub.mainpack.products.TVShow.Season.Chapter chapter: seasons.get(season).getChaptersList()) {
			
			chapterList.append("tittle: "+ chapter.getTittle()+ " score: "+ chapter.getScore() + " \n");
		}

		return chapterList.toString();
	}
	public  Integer getNumOfChaptersFromSeason(Integer seasonNum) {
		
		return seasons.get(seasonNum).getNumOfChapters();
	}
	

	private class Season {

		private List<Chapter> chapters;
		private Double score;
		private Integer totalChapters;

		public Season(Integer totalChapters, Double score) {
			this.score = score;
			this.totalChapters = totalChapters;

			createChapters();
		}

		public Season(String[] chapters, Double score) {
			this.chapters = new ArrayList<>();

			for (int i = 0; i < chapters.length; i++) {

				this.chapters.add(new Chapter(chapters[i], score));
			}

		}

		public Integer getNumOfChapters() {
			return totalChapters;
		}

		public List<Chapter> getChaptersList() {
			return chapters;
		}

		private void createChapters() {
			chapters = new ArrayList<>();

			for (int i = 0; i < totalChapters; i++) {
				String tittleToChapter = JOptionPane.showInputDialog("Chapter " + (i + 1) + " tittle");
				chapters.add(new Chapter(tittleToChapter, score));
			}

		}

		@Override
		public String toString() {

			return "@" + this.score + getChaptersList();

		}

		private class Chapter {

			private String tittle;
			private Double score;

			public Chapter(String tittle, Double score) {

				this.tittle = tittle;
				this.score = score;
			}

			public Chapter(String tittle) {

				this.tittle = tittle;
			}

			public String getTittle() {
				return tittle;
			}

			public Double getScore() {
				return score;
			}

			public String toString() {

				return tittle;

			}

		}
	}
}
