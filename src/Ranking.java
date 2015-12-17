/**
 * Created by SteveLeeLX on 12/8/15.
 */
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;

public class Ranking {
    /** Initializing database */
    private Connection c = null;                                        // Initializing database connection
    private Statement stm = null;                                       // Statement to process the database
    private String DATABASE_CLASS = "org.sqlite.JDBC";                  // JDBC constant
    private String DATABASE_NAME = "jdbc:sqlite:Data.db";    // Database name

    /** Initializing limitation constants */
    private final int MAX_TOP_USER = 10;                                // The best player maximum fetch from database
    private final int MAX_ATTEMP = 5;                                   // Repeat SQL statement as many as this value (if any trouble happen)

    /** Constructor without argument, initializing database */
    Ranking() {
        initDatabase();
    }

    /** Initializing database */
    private void initDatabase() {
        try {
            Class.forName(DATABASE_CLASS);                              // Loading the JDBC class
            c = DriverManager.getConnection(DATABASE_NAME);             // Get the database connection
            stm = c.createStatement();                                  // Create statement
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Close database */
    public void closeDatabase() {
        if(c != null) {                             // Check if connection is not empty
            try {
                c.close();                          // Close connection
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        if(stm != null) {                           // Check if the statement is not empty
            try {
                stm.close();                        // Close the statement
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /** Return the top 5  players (index 0 = payer name, 1 = score) */
    public String[][] getTopUser() {
        int topUserCount = 0;                               // The best players counter
        String[][] topUser = new String[MAX_TOP_USER][2];   // The best players store here

        try {
            ResultSet score = stm.executeQuery("SELECT * FROM record ORDER BY score DESC LIMIT 5;");   // Get the best player from database
            while(score.next()) {                                           // Repeat if there're any
                topUser[topUserCount][0] = score.getString("name");         // Set the best player's name
                topUser[topUserCount][1] = score.getString("score");        // Set the best player's score
                topUserCount++;                                             // Increase the best player counter
            }
            score.close();                  // Close database
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return topUser;                     // Return the best player
    }

    /** Insert player name to database and return player's ID */
    public int insertUserName(String userName, int score) {
        int userId = 0;                             // Player ID updated from database
        int insert = 0, insertAttemp = 0;           // Tester to insert into the database

        do {
            try {
                insert = stm.executeUpdate("INSERT INTO record (name, score) VALUES ('"+ userName +"', '"+score +"');"); // Insert player name to database

                if(insert != 0) {                           // Check if effected rows from database process is not 0 (insert success)
                    ResultSet id = stm.executeQuery("SELECT id FROM record ORDER BY id DESC LIMIT 1;");         // Get player ID
                    userId = id.getInt("id");               // Set player ID
                    id.close();                             // Close database
                }
            } catch(Exception ex) {
            }

            insertAttemp++;                                 // Increase the insert tester
        } while(insert == 0 && insertAttemp < MAX_ATTEMP);  // Repeat if failed to insert into database and the insert tester < maximum test

        return userId;                              // Return player ID
    }

    /** Update player score, and return rank of the player */
    public int updateUserScore(int userId, long score) {
        int position = -1;                          // Beginning rank (-1 returned, if the rank was not found)
        boolean positionFound = false;              // The rank was found or not
        int update = 0, updateAttemp = 0;           // Testeer to update the database

        do {
            try {
                update = stm.executeUpdate("UPDATE record SET score = '"+ score +"' WHERE id = '"+ userId +"';");   // Update score in database

                if(update != 0) {                                           // Check if effected rows is not 0 (failed to update)
                    ResultSet positionResult = stm.executeQuery("SELECT id FROM record ORDER BY score DESC;");      // Get game data from database
                    while(positionResult.next()) {                          // Repeat if there're any data
                        int currentId = positionResult.getInt("id");        // Current ID
                        if(currentId == userId) {                           // Check if current ID is player's ID
                            positionFound = true;                           // Set found the rank
                            break;                                          // Quit from loop
                        }
                        else                                                // Other condition, current ID is not player's ID
                            position++;                                     // Increase the rank
                    }

                    positionResult.close();                                 // Tutup basis data
                }
            } catch(Exception ex) {
            }

            updateAttemp++;                                     // Increase tester to update the database
        } while(update == 0 && updateAttemp < MAX_ATTEMP);      // Repeat if failed to update

        if(positionFound)           // Check if rank found
            position += 2;          // Add the rank with 2, because the beginning rank was -1

        return position;            // Return the player's rank
    }
}