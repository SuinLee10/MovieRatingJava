import java.io.Serializable;

public class Movie implements Comparable<Object>, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String year;
    private String genre;
    private String rate;

    public Movie(String initialName, String initialYear, String initialGenre, String initialRate){
        name = initialName.trim();
        year = initialYear.trim();
        genre = initialGenre.trim();
        rate = initialRate.trim();
    }

    public Movie(String initialName, String initialYear, String initialGenre){
        name = initialName.trim();
        year = initialYear.trim();
        genre = initialGenre.trim();
        rate = "Not Rated Yet";
    }

    public String getRate() {
        return rate;
    }
    public String getGenre() {
        return genre;
    }
    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("<").append(name).append("> ");
        str.append("Year: ").append(year).append(" ");
        str.append("Rate: ");
        if(rate.length() == 1) {
            for (int i = 0; i < Integer.parseInt(rate); i++) {
                str.append("*");
            }
        }
        else str.append(rate);
        return str.toString();
    }

    @Override
    public int compareTo(Object other) {
        if((other != null) && (other instanceof Movie)){
         Movie otherMovie = (Movie)other;
         int compareNum = this.name.compareTo(otherMovie.name);
         if(compareNum == 0){
             return this.rate.compareTo(otherMovie.rate);
         }else{
             return compareNum;
         }
        }
        return -1;
    }
}