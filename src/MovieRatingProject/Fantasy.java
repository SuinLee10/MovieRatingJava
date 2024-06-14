public class Fantasy extends Fiction{
	public Fantasy(String initialName, String initialYear, String initialGenre, String initialRate) {
        super(initialName, initialYear, initialGenre, initialRate);
    }

    public Fantasy(String initialName, String initialYear, String initialGenre) {
        super(initialName, initialYear, initialGenre);
    }
    
    public String toString() {
        return "Fantasy      " + super.toString();
    }

}
