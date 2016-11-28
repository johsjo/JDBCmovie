package se.johsjo.jdbcmovie.model;

public class Movie {
	private int id;
	private String title;
	private String production_year;
	private String genre;

	public Movie(int id, String title, String production_year, String genre) {
		this.id = id;
		this.title = title;
		this.production_year = production_year;
		this.genre = genre;

	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getProduction_year() {
		return production_year;
	}

	public String getGenre() {
		return genre;
	}

}
