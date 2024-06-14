package MovieRatingProject;

public interface ICRUD {
    public void addMovie();
    public void addToList(String[] movie);
    public void loadText();
    public void printMovie();
    public void findGenre();
    public void addRate();
    public void deleteMovie();
    public void sortName();
    public void sortRate();
    public void save();
}
