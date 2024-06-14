//MovieFrame
package MovieRatingProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class MovieFrame extends JFrame implements ICRUD{
    private DefaultTableModel tableModel;
    private JTable table;
    private Map<String, Movie> list = new HashMap<>();

    public MovieFrame() {
        setTitle("Movie Rating System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Movie Rating System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Name");
        tableModel.addColumn("Release Date");
        tableModel.addColumn("Genre");
        tableModel.addColumn("Rate");

        table = new JTable(tableModel);

        table.getTableHeader().setReorderingAllowed(false);

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 0, 40));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, 10, 10));

        //print button
        ImageIcon printIcon = new ImageIcon("print.png");
        Image printImage = printIcon.getImage();
        Image printImg = printImage.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon printIconBttn = new ImageIcon(printImg);

        JButton printButton = new JButton(printIconBttn);
        printButton.setToolTipText("Print");
        printButton.setActionCommand("print");
        printButton.addActionListener(new ButtonClickListener());

        panel.add(printButton);

        //add button
        ImageIcon addIcon = new ImageIcon("add.png");
        Image addImage = addIcon.getImage();
        Image addImg = addImage.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon addIconBttn = new ImageIcon(addImg);

        JButton addButton = new JButton(addIconBttn);
        addButton.setToolTipText("Add");
        addButton.setActionCommand("add");
        addButton.addActionListener(new ButtonClickListener());

        panel.add(addButton);
        //rate button
        ImageIcon rateIcon = new ImageIcon("rate.png");
        Image rateImage = rateIcon.getImage();
        Image rateImg = rateImage.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon rateIconBttn = new ImageIcon(rateImg);

        JButton rateButton = new JButton(rateIconBttn);
        rateButton.setToolTipText("Rate");
        rateButton.setActionCommand("rate");
        rateButton.addActionListener(new ButtonClickListener());

        panel.add(rateButton);

        //sort button
        ImageIcon sortIcon = new ImageIcon("sorting.png");
        Image sortImage = sortIcon.getImage();
        Image sortImg = sortImage.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon sortIconBttn = new ImageIcon(sortImg);

        JButton sortButton = new JButton(sortIconBttn);
        sortButton.setToolTipText("Sort");
        sortButton.setActionCommand("sort");
        sortButton.addActionListener(new ButtonClickListener());

        panel.add(sortButton);

        //find button
        ImageIcon searchIcon = new ImageIcon("search.png");
        Image searchImage = searchIcon.getImage();
        Image searchImg = searchImage.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon searchIconBttn = new ImageIcon(searchImg);

        JButton findButton = new JButton(searchIconBttn);
        findButton.setToolTipText("Find Genre");
        findButton.setActionCommand("findgenre");
        findButton.addActionListener(new ButtonClickListener());

        panel.add(findButton);

        //trash button
        ImageIcon trashIcon = new ImageIcon("trash.png");
        Image trashImage = trashIcon.getImage();
        Image trashImg = trashImage.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon trashIconBttn = new ImageIcon(trashImg);

        JButton trashButton = new JButton(trashIconBttn);
        trashButton.setToolTipText("Delete");
        trashButton.setActionCommand("delete");
        trashButton.addActionListener(new ButtonClickListener());

        panel.add(trashButton);

        ImageIcon saveIcon = new ImageIcon("save.png");
        Image saveImage = saveIcon.getImage();
        Image saveImg = saveImage.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon saveIconBttn = new ImageIcon(saveImg);

        JButton saveButton = new JButton(saveIconBttn);
        saveButton.setToolTipText("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new ButtonClickListener());

        panel.add(saveButton);

        add(panel, BorderLayout.SOUTH);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        loadText();
        printMovie();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand().replace(" ", "").toLowerCase();
            switch (command) {
                case "print":
                    printMovie();
                    break;
                case "rate":
                    addRate();
                    break;
                case "findgenre":
                    findGenre();
                    break;
                case "add":
                    addMovie();
                    break;
                case "delete":
                    deleteMovie();
                    break;
                case "sort":
                    showSortOptions();
                    break;
                case "save":
                    save();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Not a valid function. try again", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void printMovie() {
        updateTable(new ArrayList<>(list.values()));
    }

    public void updateTable(List<Movie> movieList) {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        for (Movie movie : movieList) {
            String starRate = "";
            if (movie.getRate().equals("Not Rated Yet") || movie.getRate() == null) {
                starRate = "Not Rated Yet";
            } else {
                for (int i = 0; i < Integer.parseInt(movie.getRate()); i++) {
                    starRate += "*";
                }
            }
            Object[] rowData = {movie.getName(), movie.getYear(), movie.getGenre(), starRate};
            tableModel.addRow(rowData);
        }
    }

    public void addRate() {
        String movieName = JOptionPane.showInputDialog(this, "Movie title for rating:");
        if (movieName != null) {
            Movie movie = list.get(movieName.trim());
            if (movie != null) {
                displayMessage(movie.toString());
                while (true) {
                    String movieRate = JOptionPane.showInputDialog(this, "Enter the rate(1 ~ 5):");
                    try {
                        int rate = Integer.parseInt(movieRate.trim());
                        if (rate >= 1 && rate <= 5) {
                            movie.setRate(movieRate);
                            displayMessage("Rate is updated!");
                            printMovie();
                            break;
                        } else {
                            JOptionPane.showMessageDialog(this, "Rate has to be between 1 and 5", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Rate has to be an Integer number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Not Found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteMovie() {
        String movieNameDelete = JOptionPane.showInputDialog(this, "Movie title for deleting:");
        if (movieNameDelete != null) {
            Movie movie = list.get(movieNameDelete.trim());
            if (movie != null) {
                int response = JOptionPane.showConfirmDialog(this, "Are you sure deleting <" + movie.getName() + ">?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    list.remove(movieNameDelete.trim());
                    displayMessage("Succesfully Deleted");
                    printMovie();
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion cancelled", "Information", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Not Found" , "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void addMovie() {
        String name;
        while(true){
            name = JOptionPane.showInputDialog(this, "Enter movie name:");
            if(name == null){
                return;
            }
            if(name.trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Movie name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if(list.containsKey(name.trim())){
                JOptionPane.showMessageDialog(this, "The movie with the name \"" + name + "\" already exists. Please enter a different name." , "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            break;
        }
        String year = JOptionPane.showInputDialog(this, "Enter released year of the movie:");
        if(year == null){
            return;
        }
        String[] genres = {"Action", "Documentary", "Fantasy", "History", "Romance", "SF"};
        String genre = (String) JOptionPane.showInputDialog(this, "Choose genre:", "Find Genre", JOptionPane.QUESTION_MESSAGE, null, genres, genres[0]);
        if(genre == null){
            return;
        }
        String rate="Not Rated Yet";
        int rateResponse = JOptionPane.showConfirmDialog(this, "Would you rate the movie?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (rateResponse == JOptionPane.YES_OPTION) {
            while (true) {
                rate = JOptionPane.showInputDialog(this, "Enter the rate(1 ~ 5):");
                try {
                    int rateInt = Integer.parseInt(rate.trim());
                    if (rateInt >= 1 && rateInt <= 5) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "Rate has to be between 1 and 5", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Rate has to be an Integer number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        String[] movieDetails = {name, year, genre, rate};
        addToList(movieDetails);
        displayMessage("Movie added successfully");
        printMovie();
    }


    public void findGenre() {
        String[] genres = {"Action", "Documentary", "Fantasy", "History", "Romance", "SF"};
        String genre = (String) JOptionPane.showInputDialog(this, "Choose genre to find:", "Find Genre", JOptionPane.QUESTION_MESSAGE, null, genres, genres[0]);
        if (genre != null) {
            List<Movie> filteredList = new ArrayList<>();
            for (Movie movie : list.values()) {
                if (movie.getGenre().equalsIgnoreCase(genre)) {
                    filteredList.add(movie);
                }
            }
            updateTable(filteredList);
        }
    }
    public void addToList(String[] movie) {
        if (list.containsKey(movie[0])) {
            return;
        }
        switch (movie[2].trim().toLowerCase()) {
            case "action":
                if (movie.length == 4) list.put(movie[0], new Action(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0] , new Action(movie[0], movie[1], movie[2]));
                break;
            case "documentary":
                if (movie.length == 4) list.put(movie[0], new Documentary(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new Documentary(movie[0], movie[1], movie[2]));
                break;
            case "fantasy":
                if (movie.length == 4) list.put(movie[0], new Fantasy(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new Fantasy(movie[0], movie[1], movie[2]));
                break;
            case "history":
                if (movie.length == 4) list.put(movie[0], new History(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new History(movie[0], movie[1], movie[2]));
                break;
            case "romance":
                if (movie.length == 4) list.put(movie[0], new Romance(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new Romance(movie[0], movie[1], movie[2]));
                break;
            case "sf":
                if (movie.length == 4) list.put(movie[0], new ScienceFiction(movie[0], movie[1], movie[2], movie[3]));
                else list.put(movie[0], new ScienceFiction(movie[0], movie[1], movie[2]));
                break;
        }
    }
    public void save(){
        String fileName = "movie.txt";
        PrintWriter outputStream = null;
        try{
            outputStream = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error opening the file " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        String movieInfo;
        for(Movie movie : list.values()){
            if(movie.getRate().equalsIgnoreCase("Not rated yet")){
                movieInfo = movie.getName() + ", " + movie.getYear() + ", " + movie.getGenre();
                outputStream.println(movieInfo);
            }else{
                movieInfo = movie.getName() + ", " + movie.getYear() + ", " + movie.getGenre() + ", " + movie.getRate();
                outputStream.println(movieInfo);
            }
        }
        outputStream.close();
        JOptionPane.showMessageDialog(this, "Movies are saved in text file "+fileName, "Information", JOptionPane.PLAIN_MESSAGE);
    }
    public void loadText() {
        String fileName = "movie.txt";
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error opening the file", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            String[] inputLine = line.split(",");
            addToList(inputLine);
        }
        JOptionPane.showMessageDialog(this, "List of Movies loaded", "Information", JOptionPane.PLAIN_MESSAGE);
        inputStream.close();
    }
    private void showSortOptions() {
        String[] options = {"Name", "Rate"};
        String selectedOption = (String) JOptionPane.showInputDialog(this, "Sort by:", "Sort Options", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption != null) {
            if (selectedOption.equalsIgnoreCase("Name")) {
                sortName();
            } else if (selectedOption.equalsIgnoreCase("Rate")) {
                sortRate();
            }
        }
    }
    public void sortName() {
        List<Map.Entry<String, Movie>> entryList = new ArrayList<>(list.entrySet());
        entryList.sort(Map.Entry.comparingByKey());

        List<Movie> sortedMovies = new ArrayList<>();
        for (Map.Entry<String, Movie> entry : entryList) {
            sortedMovies.add(entry.getValue());
        }
        updateTable(sortedMovies);

        JOptionPane.showMessageDialog(this, "Movie list is sorted by name", "Information", JOptionPane.PLAIN_MESSAGE);

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

        List<Movie> sortedMovies = new ArrayList<>();
        for (Map.Entry<String, Movie> entry : entryList) {
            sortedMovies.add(entry.getValue());
        }
        updateTable(sortedMovies);
        JOptionPane.showMessageDialog(this, "Movie list is sorted by rate", "Information", JOptionPane.PLAIN_MESSAGE);
    }

}