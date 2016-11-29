package se.johsjo.jdbcmovie.model;

public final class ReviewUser {
	private int id;
	private String username;

	public ReviewUser(int id, String username) {
		this.id = id;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
}
