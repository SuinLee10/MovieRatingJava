package MovieRatingProject;

public class NonFiction extends Movie{
	public NonFiction(String initialName, String initialYear, String initialGenre, String initialRate) {
        super(initialName, initialYear, initialGenre, initialRate);
    }
    public NonFiction(String initialName, String initialYear, String initialGenre) {
        super(initialName, initialYear, initialGenre);
    }
    @Override
    public String toString() {
        return super.toString();
    }

}
