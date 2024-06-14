package MovieRatingProject;

public class Documentary extends NonFiction{
	public Documentary(String initialName, String initialYear, String initialGenre, String initialRate) {
        super(initialName, initialYear, initialGenre, initialRate);
    }
    public Documentary(String initialName, String initialYear, String initialGenre) {
        super(initialName, initialYear, initialGenre);
    }
    @Override
    public String toString() {
        return "Documentary  " + super.toString();
    }

}
