import java.io.*;
import java.util.*;

public class NumberGame {

    static final String ACCOUNTS_FILE = "accounts.txt";
    static final String STATS_FILE    = "stats.txt";
    static Scanner sc = new Scanner(System.in);

    // ── File Helpers ──────────────────────────────────────────────────────────

    // Load key=value pairs from a file
    static Map<String, String> loadMap(String filename) {
        Map<String, String> map = new HashMap<>();
        File f = new File(filename);
        if (!f.exists()) return map;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename);
        }
        return map;
    }

    // Save key=value pairs to a file
    static void saveMap(String filename, Map<String, String> map) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> e : map.entrySet())
                pw.println(e.getKey() + "=" + e.getValue());
        } catch (IOException e) {
            System.out.println("Error saving " + filename);
        }
    }

    // ── Account Management ────────────────────────────────────────────────────

    static void createAccount() {
        Map<String, String> accounts = loadMap(ACCOUNTS_FILE);
        System.out.println("\n--- Create Account ---");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();
        if (accounts.containsKey(username)) {
            System.out.println("❌ Username already exists. Try logging in.");
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();
        accounts.put(username, password);
        saveMap(ACCOUNTS_FILE, accounts);

        // Initialise stats
        Map<String, String> stats = loadMap(STATS_FILE);
        if (!stats.containsKey(username + "_games")) {
            stats.put(username + "_games",  "0");
            stats.put(username + "_total",  "0");
            stats.put(username + "_best",   "NA");
            saveMap(STATS_FILE, stats);
        }
        System.out.println("✅ Account created! Welcome, " + username + ".");
    }

    static String login() {
        Map<String, String> accounts = loadMap(ACCOUNTS_FILE);
        System.out.println("\n--- Login ---");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();
        if (password.equals(accounts.get(username))) {
            System.out.println("✅ Login successful! Welcome back, " + username + ".");
            return username;
        }
        System.out.println("❌ Invalid username or password.");
        return null;
    }

    // ── Game Logic ────────────────────────────────────────────────────────────

    static void playGame(String username) {
        System.out.println("\n--- Number Guessing Game ---");
        System.out.println("I'm thinking of a number between 1 and 100.");
        int secret   = new Random().nextInt(100) + 1;
        int attempts = 0;

        while (true) {
            System.out.print("Your guess: ");
            String input = sc.nextLine().trim();
            int guess;
            try {
                guess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
                continue;
            }
            attempts++;
            if (guess < secret)       System.out.println("📈 Too low!  Try higher.");
            else if (guess > secret)  System.out.println("📉 Too high! Try lower.");
            else {
                System.out.println("🎉 Correct! You guessed it in " + attempts + " attempt(s).");
                break;
            }
        }

        // Update stats
        Map<String, String> stats = loadMap(STATS_FILE);
        int games = Integer.parseInt(stats.getOrDefault(username + "_games", "0"));
        int total = Integer.parseInt(stats.getOrDefault(username + "_total", "0"));
        String bestStr = stats.getOrDefault(username + "_best", "NA");

        games++;
        total += attempts;
        int best = bestStr.equals("NA") ? attempts : Math.min(Integer.parseInt(bestStr), attempts);

        stats.put(username + "_games", String.valueOf(games));
        stats.put(username + "_total", String.valueOf(total));
        stats.put(username + "_best",  String.valueOf(best));
        saveMap(STATS_FILE, stats);
    }

    static void showStatistics(String username) {
        Map<String, String> stats = loadMap(STATS_FILE);
        int games    = Integer.parseInt(stats.getOrDefault(username + "_games", "0"));
        int total    = Integer.parseInt(stats.getOrDefault(username + "_total", "0"));
        String best  = stats.getOrDefault(username + "_best", "NA");
        String avg   = (games > 0) ? String.format("%.2f", (double) total / games) : "NA";

        System.out.println("\n==============================");
        System.out.println("  STATISTICS FOR " + username.toUpperCase());
        System.out.println("==============================");
        System.out.println("  Games Played   : " + games);
        System.out.println("  Total Attempts : " + total);
        System.out.println("  Best Score     : " + best + " attempt(s)");
        System.out.println("  Average Tries  : " + avg);
        System.out.println("==============================");
    }

    // ── Menus ─────────────────────────────────────────────────────────────────

    static void playerMenu(String username) {
        while (true) {
            System.out.println("\n======================");
            System.out.println("  PLAYER MENU");
            System.out.println("======================");
            System.out.println("1. Start Game");
            System.out.println("2. Statistics");
            System.out.println("3. Logout");
            System.out.print("Select option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": playGame(username);        break;
                case "2": showStatistics(username);  break;
                case "3":
                    System.out.println("👋 Logged out. Goodbye, " + username + "!");
                    return;
                default:
                    System.out.println("❌ Invalid option. Choose 1-3.");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n======================");
            System.out.println("  NUMBER GAME");
            System.out.println("======================");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": createAccount();  break;
                case "2":
                    String user = login();
                    if (user != null) playerMenu(user);
                    break;
                case "3":
                    System.out.println("👋 Thanks for playing! Goodbye.");
                    return;
                default:
                    System.out.println("❌ Invalid option. Choose 1-3.");
            }
        }
    }
}