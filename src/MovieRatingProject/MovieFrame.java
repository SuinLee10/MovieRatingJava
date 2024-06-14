package MovieRatingProject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieFrame extends JFrame {
    private CRUDFrame manager;
    private JTextArea outputArea;

    public MovieFrame(CRUDFrame manager) {
        this.manager = manager;
        setTitle("Movie Rating Project");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));

        addButton(buttonPanel, "Print", e -> printMovies());
        addButton(buttonPanel, "Rate", e -> manager.addRate());
        addButton(buttonPanel, "Sort Name", e -> sortByName());
        addButton(buttonPanel, "Sort Rate", e -> sortByRate());
        addButton(buttonPanel, "Find Genre", e -> manager.findGenre());
        addButton(buttonPanel, "Add", e -> manager.addMovie());
        addButton(buttonPanel, "Delete", e -> manager.delete());
        addButton(buttonPanel, "Load", e -> manager.loadText());
        addButton(buttonPanel, "Exit", e -> System.exit(0));

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private void addButton(JPanel panel, String label, java.awt.event.ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }

    private void printMovies() {
        outputArea.setText("");
        List<Movie> movies = manager.printMovie();
        if (movies != null && !movies.isEmpty()) {
            movies.forEach(movie -> outputArea.append(movie.toString() + "\n"));
        } else {
            outputArea.append("No movies to display.\n");
        }
    }

    private void sortByName() {
        manager.sortName();
        printMovies();
    }

    private void sortByRate() {
        manager.sortRate();
        printMovies();
    }
}
