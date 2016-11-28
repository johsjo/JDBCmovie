package se.johsjo.jdbcmovie.model;

public final class Review {
	private int id;
	private int movieId;
	private String review;
	private int reviewUserId;

	public Review(int id, int movieId, String review, int reviewUserId) {
		super();
		this.id = id;
		this.movieId = movieId;
		this.review = review;
		this.reviewUserId = reviewUserId;
	}

	public int getId() {
		return id;
	}

	public int getMovieId() {
		return movieId;
	}

	public String getReview() {
		return review;
	}

	public int getReviewUserId() {
		return reviewUserId;
	}

}
