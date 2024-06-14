package MovieRatingProject;

public class Romance extends Fiction{
    public Romance(String initialName, String initialYear, String initialGenre, String initialRate) {
        super(initialName, initialYear, initialGenre, initialRate);
    }

    public Romance(String initialName, String initialYear, String initialGenre) {
        super(initialName, initialYear, initialGenre);
    }
    @Override
    public String toString() {
        return "Romance      " + super.toString();
    }
}
