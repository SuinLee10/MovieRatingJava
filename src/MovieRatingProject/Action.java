public class Action extends Fiction{
	public Action(String initialName, String initialYear, String initialGenre, String initialRate) {
        super(initialName, initialYear, initialGenre, initialRate);
    }

    public Action(String initialName, String initialYear, String initialGenre) {
        super(initialName, initialYear, initialGenre);
    }
    @Override
    public String toString() {
        return "Action       " + super.toString();
    }


}
