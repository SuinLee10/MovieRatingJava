package MovieRatingProject;

public interface iCRUD {
    public void addMovie();
    public void addToList(String[] movie);
    public void loadText();
    public void printMovie();
    public void findGenre();
    public void printMovieByGenre(String genre);
    public void addRate();
    public void delete();
    public void sortName();
    public void sortRate();
    public void save();
}
