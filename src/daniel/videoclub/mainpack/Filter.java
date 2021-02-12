package daniel.videoclub.mainpack;

import java.util.ArrayList;
import java.util.List;
/**
 *Enum that references the position of the element in the line of the file according to where # is found
 * @author Pelicano
 *
 */
public enum Filter {

	TITTLE(0), ID(0), USERNAME(1), RELEASEYEAR(1), PASSWORD(2), DIRECTOR(2), GENRE(3), NUMOFSEASON(3), SUBGENRE(4),
	FINISHED(4), AVAILABILITY(5), STOCK(6), NUMRENTED(7), SCORESUM(8), NUMOFRESULT(9), SEASONS(10);

	private Integer position;

	private Filter(Integer position) {

		this.position = position;
	}

	public int getPosition() {

		return position;
	}

	public static List<Filter> getFilterForFilms() {

		List<Filter> filters = new ArrayList<>();
		filters.add(TITTLE);
		filters.add(RELEASEYEAR);
		filters.add(DIRECTOR);
		filters.add(GENRE);
		filters.add(SUBGENRE);
		filters.add(AVAILABILITY);
		filters.add(STOCK);
		filters.add(NUMRENTED);
		filters.add(SCORESUM);
		filters.add(NUMOFRESULT);

		return filters;

	}

	public static List<Filter> getFilterForTVShows() {

		List<Filter> filters = new ArrayList<>();
		filters.add(TITTLE);
		filters.add(RELEASEYEAR);
		filters.add(DIRECTOR);
		filters.add(NUMOFSEASON);
		filters.add(FINISHED);
		filters.add(AVAILABILITY);
		filters.add(STOCK);
		filters.add(NUMRENTED);
		filters.add(SCORESUM);
		filters.add(NUMOFRESULT);
		filters.add(SEASONS);

		return filters;

	}

	public static List<Filter> getFilterForUserLogin() {
		
		List<Filter> filters = new ArrayList<>();
		filters.add(ID);
		filters.add(USERNAME);
		filters.add(PASSWORD);
		
		return filters;
	}
}
