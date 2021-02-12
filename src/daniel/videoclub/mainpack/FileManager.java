package daniel.videoclub.mainpack;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Pelicano
 *
 */
public class FileManager {
	
	private FileManager() {}
	
	
	
	
	public static void addNewDataToFile(String dataToAdd, FileList fileToEdit) {
			
			
		try (BufferedWriter dataWriter = new BufferedWriter(new FileWriter(fileToEdit.getPath(), true))) {
			
			dataWriter.write(dataToAdd+"\n");
					
		} catch (IOException error) {

			System.out.println("writer error");
			
		}			
		
	}

		
	
	public static String extractDataFromProductWithFilter(String lineToExtract, Filter filter) {
		
	
		
		return applySeparator("#",lineToExtract,filter);
	
		
	}
	

	
	private static String applySeparator(String separator,String lineToExtract, Filter filter) {
		
		String[] stringSplited = lineToExtract.split(separator);
		
		return stringSplited[filter.getPosition()];
	}	
	
	
	public static void editFieldFromFileWithFilter(int numOfLineTarget ,FileList fileToRead,String stringForEditing,String newField, Filter filter) {
		
		
		List<String> fieldList = getFieldsFromLine('#',stringForEditing,fileToRead);
		
		StringBuilder editedString = new StringBuilder();
		
		for(int i = 0;i<fieldList.size();i++) {
			
			if(filter.getPosition() == i) {
				
				
				editedString.append(newField+"#");
				continue;	
			}
			
			editedString.append(fieldList.get(i)+"#");

		}
		
		editLineFromFileWithString(numOfLineTarget, fileToRead, editedString.toString());

	}
	
	public static List<String> getFieldsFromLine(char separator,String line, FileList file) {
		
		List<String> fieldList = new ArrayList<>();
		
		List<Filter> filterList = null;
		
		switch(file) {
		
		case FILMS:
			
			filterList = Filter.getFilterForFilms();
			break;
			
		case TVSHOWS:
			
			filterList = Filter.getFilterForTVShows();
			break;
			
		case USERLOGIN:
			
			filterList = Filter.getFilterForUserLogin();
			break;
		default:
			throw new RuntimeException("Failed to load filter list");
		}
		

		for(Filter filter:	filterList) {
			
			fieldList.add(applySeparator("#", line, filter));
			
		}
		return fieldList;  

	}

	
	public static void removeLineFromFile(int numOfLineTarget ,FileList fileToRead) {

		int countOfLines = 0;

		File tempFile = new File("Sources/Catalog/Products" + ".tmp");
		File originalFile = new File(fileToRead.getPath());

		try (
				BufferedReader dataReader = new BufferedReader(new FileReader(originalFile));
				PrintWriter dataWriter = new PrintWriter(new FileWriter(tempFile));
		) {

			String line = null;

			while ((line = dataReader.readLine()) != null) {

				if (countOfLines != numOfLineTarget) {

					dataWriter.println(line);
					dataWriter.flush();
				}
				
				countOfLines++;
			}
		
			
			
				
				System.out.println("peta");
			

		} catch (IOException error) {

			System.out.println(error.getMessage());
		}
		
		originalFile.delete();
		
		tempFile.renameTo(new File(fileToRead.getPath()));

	}

	public static void editLineFromFileWithString(int lineTarget, FileList fileToRead, String newLine) {

		int countOfLines = 0;

		File tempFile = new File("Sources/Catalog/Products" + ".tmp");
		File originalFile = new File(fileToRead.getPath());

		try (
				BufferedReader dataReader = new BufferedReader(new FileReader(originalFile));
				PrintWriter dataWriter = new PrintWriter(new FileWriter(tempFile));
		) {
			String line = null;

			while ((line = dataReader.readLine()) != null) {

				if (countOfLines != lineTarget) {

					dataWriter.println(line);
					dataWriter.flush();
				} else {
					dataWriter.println(newLine);
					dataWriter.flush();
				}

				countOfLines++;
			}

		
		} catch (IOException error) {

			System.out.println(error.getMessage());
		}

		originalFile.delete();
		
		tempFile.renameTo(new File(fileToRead.getPath()));
		
	}
	
	public static List<String> loadFileDataIntoList(FileList fileToRead){
		
		List<String> dataFile = new ArrayList<>();
 
		try (BufferedReader dataReader = new BufferedReader(new FileReader(fileToRead.getPath()))){
		
			String line = null;

			while ((line = dataReader.readLine()) != null) {

				dataFile.add(line);
			}

		} catch (IOException error) {

			System.out.println(error.getMessage());
		}
		return dataFile;
	}

}
