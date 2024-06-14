package MovieRatingProject;

import java.io.*;
import java.util.*;

public class CRUD implements ICRUD {
    private Map<String, Movie> list;
    public CRUD(){
        this.list = new HashMap<>();
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
        if (list.containsKey(movie[0])) {
            return;
        }
        switch (movie[2].trim().toLowerCase()) {
            case "action":
                if(movie.length == 4) list.put(movie[0], new Action(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new Action(movie[0], movie[1], movie[2]));
                break;
            case "documentary":
                if(movie.length == 4) list.put(movie[0], new Documentary(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new Documentary(movie[0], movie[1], movie[2]));
                break;
            case "fantasy":
                if(movie.length == 4) list.put(movie[0], new Fantasy(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new Fantasy(movie[0], movie[1], movie[2]));
                break;
            case "history":
                if(movie.length == 4) list.put(movie[0], new History(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new History(movie[0], movie[1], movie[2]));
                break;
            case "romance":
                if(movie.length == 4) list.put(movie[0], new Romance(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new Romance(movie[0], movie[1], movie[2]));
                break;
            case "sf":
                if(movie.length == 4) list.put(movie[0], new ScienceFiction(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new ScienceFiction(movie[0], movie[1], movie[2]));
                break;
        }
    }
    public void loadText() {
        String fileName = "movie.txt";
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
            for(Movie movie : list.values()){
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
            for (Movie movie : list.values()) {
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
        Movie movie = list.get(movieName);
        if(movie != null){
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
        }else{
            System.out.println("Not Found");
        }
    }
    public void deleteMovie(){
        Scanner keyboard = new Scanner(System.in);
        String movieNameDelete, userConfirmation;
        System.out.print("Movie title for deleting: ");
        movieNameDelete = keyboard.nextLine().trim();
        Movie movie = list.get(movieNameDelete);
        if(movie != null) {
            System.out.println(movie.toString());
            while (true) {
                System.out.print("Are you sure deleting <" + movie.getName() + ">? (Y/N) : ");
                userConfirmation = keyboard.nextLine();
                if (userConfirmation.equalsIgnoreCase("y")) {
                    list.remove(movieNameDelete);
                    //System.out.println("::Successfully Deleted::");
                    break;
                } else if (userConfirmation.equalsIgnoreCase("n")) {
                    System.out.println("~ Deletion cancelled ~");
                    break;
                } else {
                    System.out.println("::Invaild input. Try again.::");
                }
            }
        }else{
            System.out.println("Not Found");
        }
    }
    public void sortName() {
        List<Map.Entry<String, Movie>> entryList = new ArrayList<>(list.entrySet());
        entryList.sort(Map.Entry.comparingByKey());
        System.out.println("Name Sorted Successfully!");

        // print sorted movies
        for (Map.Entry<String, Movie> entry : entryList) {
            System.out.println(entry.getValue().toString());
        }
    }

    public void sortRate() {
        List<Map.Entry<String, Movie>> entryList = new ArrayList<>(list.entrySet());
        entryList.sort((entry1, entry2) -> {
            String rate1 = entry1.getValue().getRate();
            String rate2 = entry2.getValue().getRate();

            if (rate1.equals("Not Rated Yet") && rate2.equals("Not Rated Yet")) {
                return entry1.getKey().compareTo(entry2.getKey());
            } else if (rate1.equals("Not Rated Yet")) {
                return 1;
            } else if (rate2.equals("Not Rated Yet")) {
                return -1;
            } else {
                int rateComparison = Integer.compare(Integer.parseInt(rate2), Integer.parseInt(rate1));
                if (rateComparison == 0) {
                    return entry1.getKey().compareTo(entry2.getKey());
                }
                return rateComparison;
            }
        });

        // 정렬된 영화 출력
        System.out.println("Movies sorted by rate (highest to lowest):");
        for (Map.Entry<String, Movie> entry : entryList) {
            Movie movie = entry.getValue();
            StringBuilder stars = new StringBuilder();
            if (!movie.getRate().equals("Not Rated Yet")) {
                int rate = Integer.parseInt(movie.getRate());
                for (int i = 0; i < rate; i++) {
                    stars.append('*');
                }
            } else {
                stars.append("Not Rated Yet");
            }
            System.out.printf("%-12s <%s> Year: %s Rate: %s\n",
                    movie.getGenre(),
                    movie.getName(),
                    movie.getYear(),
                    stars.toString()
            );
        }
    }
    public void save(){
        String fileName = "movie.txt";
        PrintWriter outputStream = null;
        try{
            outputStream = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " + fileName);
            throw new RuntimeException(e);
        }
        String movieInfo;
        for(Movie movie : list.values()){
            if(movie.getRate().equalsIgnoreCase("Not rated yet")){
                movieInfo = movie.getName() + ", " + movie.getYear() + ", " + movie.getGenre() + "\n";
                outputStream.println(movieInfo);
            }else{
                movieInfo = movie.getName() + ", " + movie.getYear() + ", " + movie.getGenre() + ", " + movie.getRate() + "\n";
                outputStream.println(movieInfo);
            }
        }
        outputStream.close();
        System.out.println("Movies are saved in text file " + fileName);
    }


}
