package se.johsjo.jdbcmovie.repository;

public final class MovieRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 1833772463028619757L;

	public MovieRepositoryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MovieRepositoryException(String message) {
		super(message);
	}

}
