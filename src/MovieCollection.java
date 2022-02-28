import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    private void exitToMenu() {
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        String str = scanner.nextLine();
    }
    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();
            String keywords = results.get(i).getKeywords();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);
        exitToMenu();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }
    private ArrayList<String> sortStringArray(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String temp = listToSort.get(j);
            String tempTitle = temp;

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
        return listToSort;
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Genre: " + movie.getGenres());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void removeDups(ArrayList<String> arr) {
        for (int x = 0; x < arr.size(); x ++) {
            for (int y = x + 1; y < arr.size(); y ++) {
                if (arr.get(x).equals(arr.get(y))) {
                    arr.remove(y);
                    y--;
                }
            }
        }
    }
    private void searchCast()
    {
        System.out.println("Enter a cast member word: ");
        String castMem = scanner.nextLine();

        castMem = castMem.toLowerCase();

        ArrayList<String> castMembers = new ArrayList<String>();

        for (int x = 0; x < movies.size(); x ++) {
            String[] cast = movies.get(x).getCast().split("\\|");
            for (int y = 0; y < cast.length; y ++) {
                if (cast[y].contains(castMem))
                {
                    castMembers.add(cast[y]);
                }
            }
        }
        removeDups(castMembers);

        for (int castInd = 0; castInd < castMembers.size(); castInd ++) {
            System.out.println((castInd + 1) + ". " + castMembers.get(castInd));
        }
        System.out.print("Select a cast member to learn more about (by number): ");
        int num = scanner.nextInt();

        // find all movies
        ArrayList<Movie> castMovies = new ArrayList<Movie>();
        for (int ind = 0; ind < movies.size(); ind ++) {
            if (movies.get(ind).getCast().indexOf(castMembers.get(num - 1)) != -1) {
                // contains the cast member
                castMovies.add(movies.get(ind));
            }
        }
        sortResults(castMovies);
        // display movies
        int cnt = 1;
        for (Movie resultMovie : castMovies) {
            System.out.println(cnt + ". " + resultMovie.getTitle());
            cnt++;
        }

        System.out.print("Select a movie to learn more about (enter by number): ");
        int movieNum = scanner.nextInt();

        displayMovieInfo(castMovies.get(movieNum - 1));
        exitToMenu();
    }

    private void searchKeywords()
    {

        System.out.print("Enter a keyword term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> matchedKey = new ArrayList<Movie>();

        // look for keywords
        for (int x = 0; x < movies.size(); x++)
        {
            String movieTitle = movies.get(x).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                matchedKey.add(movies.get(x));
            }
        }

        sortResults(matchedKey);

        // print it out
        for (int y = 0; y < matchedKey.size(); y ++) {
            System.out.println((y+1) + ". \"" + matchedKey.get(y).getTitle() + "\" matched with keyword '" + searchTerm + "'");
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int movieChoice = scanner.nextInt();

        displayMovieInfo(matchedKey.get(movieChoice - 1));
        exitToMenu();
    }

    private void listGenres()
    {

        ArrayList<String> genreList = new ArrayList<String>();
        for (int genreInd = 0; genreInd < movies.size(); genreInd ++) {
            String[] genreParsed = movies.get(genreInd).getGenres().split("\\|");
            for (String singleGenre : genreParsed) {
                genreList.add(singleGenre);
            }
        }
        removeDups(genreList);
        sortStringArray(genreList);
        for (int g = 0; g < genreList.size(); g ++) {
            System.out.println((g + 1) + ". " + genreList.get(g));
        }
        System.out.print("Select a genre by number: ");
        int gNum = scanner.nextInt();

        ArrayList<Movie> castMovies = new ArrayList<Movie>();
        for (int ind = 0; ind < movies.size(); ind ++) {
            if (movies.get(ind).getGenres().contains(genreList.get(gNum-1))) {
                // contains the cast member
                castMovies.add(movies.get(ind));
            }
        }
        sortResults(castMovies);
        // display movies
        int cnt = 1;
        for (Movie resultMovie : castMovies) {
            System.out.println(cnt + ". " + resultMovie.getTitle());
            cnt++;
        }

        System.out.print("Select a movie to learn more about (enter by number): ");
        int movieNum = scanner.nextInt();

        displayMovieInfo(castMovies.get(movieNum - 1));
        exitToMenu();
    }

    private void listHighestRated()
    {
        /*
        // insertion sort
        ArrayList<Integer> sample = new ArrayList<Integer>();
        sample.add(5);sample.add(7);sample.add(3);sample.add(9);sample.add(1);sample.add(12);sample.add(4);sample.add(6);
        for (int movie = 1; movie < sample.size(); movie ++) {
            int indToCheck = movie - 1;
            while (indToCheck > 0 && sample.get(movie) < sample.get(indToCheck)) {
                indToCheck--;
            }
//            if (sample.get(movie) < sample.get(indToCheck)) indToCheck = movie;
            System.out.println("Temp Ind: " + indToCheck + " : movie: " + movie + "; Array: " + sample);

            if (indToCheck + 1 != movie) {
//                int tempMovie = sample.get(indToCheck);
//                sample.set(indToCheck,sample.get(movie));
//                sample.set(movie,tempMovie);
                sample.add(indToCheck,sample.remove(movie));
            }
        }
        int ct = 0;
        System.out.println(sample);

         */
        // selection sort
        for (int x = 0; x < movies.size(); x ++) {
            int maxInd = x;
            for (int y = x + 1; y < movies.size(); y ++) {
                if (movies.get(y).getUserRating() > movies.get(maxInd).getUserRating()) {
                    maxInd = y;
                }
            }
            Movie temp = movies.get(x);
            movies.set(x,movies.get(maxInd));
            movies.set(maxInd,temp);
        }
        int ct = 1;
        for (Movie m: movies) {
            if (ct == 51) {break;}
            System.out.println(ct + ". " + m.getTitle() + ": " + m.getUserRating());
            ct ++;
        }
        System.out.println("Select a movie to learn more about");
        System.out.print("Enter number: ");

        int movieInp = scanner.nextInt();
        displayMovieInfo(movies.get(movieInp - 1));
        exitToMenu();
    }

    private void listHighestRevenue() {
        for (int x = 0; x < movies.size(); x++) {
            int maxInd = x;
            for (int y = x + 1; y < movies.size(); y++) {
                if (movies.get(y).getRevenue() > movies.get(maxInd).getRevenue()) {
                    maxInd = y;
                }
            }
            Movie temp = movies.get(x);
            movies.set(x, movies.get(maxInd));
            movies.set(maxInd, temp);
        }
        int ct = 1;
        for (Movie m : movies) {
            if (ct == 51) {
                break;
            }
            System.out.println(ct + ". " + m.getTitle() + ": " + m.getRevenue());
            ct++;
        }
        System.out.println("Select a movie to learn more about");
        System.out.print("Enter number: ");

        int movieInp = scanner.nextInt();
        displayMovieInfo(movies.get(movieInp - 1));
        exitToMenu();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");
                System.out.println(Arrays.toString(movieFromCSV));
                if (movieFromCSV.length >= 19) {
                    String title = movieFromCSV[5];
                    String cast = movieFromCSV[6];
                    String director = movieFromCSV[8];
                    String tagline = movieFromCSV[9];
                    String keywords = movieFromCSV[10];
                    String overview = movieFromCSV[11];
                    int runtime = Integer.parseInt(movieFromCSV[12]);
                    String genres = movieFromCSV[13];
                    double userRating = Double.parseDouble(movieFromCSV[17]);
                    int year = Integer.parseInt(movieFromCSV[18]);
                    int revenue = Integer.parseInt(movieFromCSV[4]);

                    // original parsing
                    /*
                     String title = movieFromCSV[0];
                    String cast = movieFromCSV[1];
                    String director = movieFromCSV[2];
                    String tagline = movieFromCSV[3];
                    String keywords = movieFromCSV[4];
                    String overview = movieFromCSV[5];
                    int runtime = Integer.parseInt(movieFromCSV[6]);
                    String genres = movieFromCSV[7];
                    double userRating = Double.parseDouble(movieFromCSV[8]);
                    int year = Integer.parseInt(movieFromCSV[9]);
                    int revenue = Integer.parseInt(movieFromCSV[10]);
                     */
                    Movie nextMovie = new Movie(title, cast, director, tagline, keywords, "overview", runtime, genres, userRating, year, revenue);
                    movies.add(nextMovie);
                }

            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}