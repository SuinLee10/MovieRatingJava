package MovieRatingProject;

public class ScienceFiction extends Fiction{
	 public ScienceFiction(String initialName, String initialYear, String initialGenre, String initialRate) {
	        super(initialName, initialYear, initialGenre, initialRate);
	    }

	    public ScienceFiction(String initialName, String initialYear, String initialGenre) {
	        super(initialName, initialYear, initialGenre);
	    }
	    @Override
	    public String toString() {
	        return "Science Fic " + super.toString();
	    }

}
