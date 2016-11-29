package se.johsjo.jdbcmovie.model;

public final class Genre {
	int id;
	String genre;

	public Genre(int id, String genre) {
		this.id = id;
		this.genre = genre;
	}

	public int getId() {
		return id;
	}

	public String getGenre() {
		return genre;
	}

}
