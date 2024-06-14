import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class CRUD implements iCRUD{
    private ArrayList<Movie>list;
    public CRUD(){
        this.list = new ArrayList<Movie>();
    }
    public void addMovie(){
        String title, year, rateFlag, genre, rate;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Add a Movie information\n" + "Enter the name: ");
        title = keyboard.nextLine().trim();
        System.out.print("Enter the movie release year: ");
        year = keyboard.nextLine().trim();
        System.out.println("Enter Movie Genre: ");
        genre = keyboard.nextLine().trim();
        System.out.print("Would you rate the movie? (Y/N): ");
        rateFlag = keyboard.nextLine().trim();
        if(rateFlag.equals("Y")){
            System.out.print("Enter the rate(1 ~ 5): ");
            rate = keyboard.nextLine().trim();
            if(Double.parseDouble(rate)% 1 != 0.0) {
                System.out.println("::Rate has to be an Integer number!::");
            }
            String[] movieInfo = {title, year, genre, rate};
            addToList(movieInfo);
        }else{
            Movie movie = new Movie(title, year, genre);
            String[] movieInfo = {title, year, genre};
            addToList(movieInfo);
        }
        System.out.println("Add Successfully!");
    }
    public void addToList(String[] movie)
    {
        switch (movie[2].trim().toLowerCase()) {
            case "action":
                if(movie.length == 4) list.add(new Action(movie[0], movie[1], movie[2], movie[3]));
                else list.add(new Action(movie[0], movie[1], movie[2]));
                break;
            case "documentary":
                if(movie.length == 4) list.add(new Documentary(movie[0], movie[1], movie[2], movie[3]));
                else list.add(new Documentary(movie[0], movie[1], movie[2]));
                break;
            case "fantasy":
                if(movie.length == 4) list.add(new Fantasy(movie[0], movie[1], movie[2], movie[3]));
                else list.add(new Fantasy(movie[0], movie[1], movie[2]));
                break;
            case "history":
                if(movie.length == 4) list.add(new History(movie[0], movie[1], movie[2], movie[3]));
                else list.add(new History(movie[0], movie[1], movie[2]));
                break;
            case "romance":
                if(movie.length == 4) list.add(new Romance(movie[0], movie[1], movie[2], movie[3]));
                else list.add(new Romance(movie[0], movie[1], movie[2]));
                break;
            case "sf":
                if(movie.length == 4) list.add(new ScienceFiction(movie[0], movie[1], movie[2], movie[3]));
                else list.add(new ScienceFiction(movie[0], movie[1], movie[2]));
                break;
        }
    }
    public void loadText() {
        String fileName = "MovieRatingProject/movie.txt";
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new File(fileName));
        } catch(FileNotFoundException e) {
            System.out.println("Error opening the file " + fileName);
            System.exit(0);
        }
        while(inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            String[] inputLine =line.split(",");
            addToList(inputLine);
        }
        System.out.println("\n>> List of Movies loaded <<\n");
        inputStream.close();
    }
    public void printMovie()
    {
        if (list.isEmpty()) {
            System.out.println("The movie list is empty.");
        } else {
            for(Movie movie : list){
                System.out.println(movie.toString());
            }
        }
    }
    public void findGenre() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("	Action / Documentary / Fantasy / History / Romance / SF");
        System.out.print("	Enter the genre to find: ");
        String insertGenre = keyboard.nextLine();

        switch(insertGenre.toLowerCase()) {
            case "action":
                printMovieByGenre("Action");
                break;
            case "documentary":
                printMovieByGenre("Documentary");
                break;
            case "fantasy":
                printMovieByGenre("Fantsy");
                break;
            case "history":
                printMovieByGenre("History");
                break;
            case "romance":
                printMovieByGenre("Romance");
                break;
            case "sf":
                printMovieByGenre("SF");
                break;

        }
    }
    public void printMovieByGenre(String genre)
    {
        if (list.isEmpty()) {
            System.out.println("The movie list is empty.");
        } else {
            for (Movie movie : list) {
                if (movie.getGenre().equalsIgnoreCase(genre)) {
                    System.out.println(movie.toString());
                }
                else continue;
            }
        }
    }
    public void addRate(){
        Scanner keyboard = new Scanner(System.in);
        String movieName, movieRate;
        System.out.print("Movie title for rating: ");
        movieName = keyboard.nextLine().trim();
        int flag = 0;
        for(Movie movie: list) {
            if (movie.getName().equalsIgnoreCase(movieName)) {
                flag++;
                System.out.println(movie.toString());
                while(true) {
                    System.out.print("Enter the rate(1 ~ 5): ");
                    movieRate = keyboard.nextLine().trim();
                    if(Double.parseDouble(movieRate)% 1 != 0.0) {
                        System.out.println("::Rate has to be an Integer number!::");
                    }
                    else {
                        movie.setRate(movieRate);
                        System.out.println("Rate is updated.");
                        break;
                    }
                }
            }
        }
        if(flag == 0) System.out.println("Not Found");
    }
    public void delete(){
        Scanner keyboard = new Scanner(System.in);
        String movieNameDelete, userConfirmation;
        boolean deleteConfirmed = false;
        System.out.print("Movie title for deleting: ");
        movieNameDelete = keyboard.nextLine().trim();
        for(Movie movie: list) {
            if (movie.getName().equalsIgnoreCase(movieNameDelete)) {
                System.out.println(movie.toString());
                while(true) {
                    System.out.print("Are you sure deleting <"+movie.getName()+ ">? (Y/N) : ");
                    userConfirmation = keyboard.nextLine();
                    if(userConfirmation.equalsIgnoreCase("y")) {
                        deleteConfirmed = true;
                        break;
                    }
                    else if(userConfirmation.equalsIgnoreCase("n")) {
                        System.out.println("~ Deletion cancelled ~");
                        break;
                    }
                    System.out.println("::Invaild input. Try again.::");
                }
            }
        }
        if(deleteConfirmed) {
            list.removeIf((movie)->movie.getName().equalsIgnoreCase(movieNameDelete));
            System.out.println("::Successfully Deleted::");
        }
    }
    public void sortName(){
        Collections.sort(list);
        System.out.println("Name Sorted Successfully!");
    }

    public void sortRate() {
        Collections.sort(list, new Comparator<Movie>(){
            public int compare(Movie movie, Movie otherMovie){
                return movie.getRate().compareTo(otherMovie.getRate());
            }
        });
        System.out.println("Rate Sorted Successfully!");
    }
}
