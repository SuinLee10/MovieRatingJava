package MovieRatingProject;

public class History extends NonFiction{
	public History(String initialName, String initialYear, String initialGenre, String initialRate) {
        super(initialName, initialYear, initialGenre, initialRate);
    }
    public History(String initialName, String initialYear, String initialGenre) {
        super(initialName, initialYear, initialGenre);
    }
    @Override
    public String toString() {
        return "History      " + super.toString();
    }

}
