import java.util.*;

class x {
    static int marks[] = new int[100];
    static int ctr = 0;

    static void addMarks(Scanner sc) {
        int n;
        System.out.print("How many marks do you want to enter: ");
        n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter mark " + (i + 1) + ": ");
            marks[ctr] = sc.nextInt();
            ctr++;
        }
    }

    static void display() {
        if (ctr == 0) {
            System.out.println("No marks are available.");
            return;
        }

        System.out.print("Marks are: ");
        for (int i = 0; i < ctr; i++) {
            System.out.print(marks[i] + " ");
        }
        System.out.println();
    }

    static void findAvg() {
        if (ctr == 0) {
            System.out.println("No marks available.");
            return;
        }

        int total = 0;
        for (int i = 0; i < ctr; i++) {
            total += marks[i];
        }

        System.out.println("Average mark is: " + ((double) total / ctr));
    }

    static void findHighest() {
        if (ctr == 0) {
            System.out.println("No marks available.");
            return;
        }

        int highest = marks[0];

        for (int i = 1; i < ctr; i++) {
            if (marks[i] > highest) {
                highest = marks[i];
            }
        }

        System.out.println("Highest mark is: " + highest);
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n====== Menu Driven Project ======");
            System.out.println("1. Add Marks");
            System.out.println("2. Display Marks");
            System.out.println("3. Find Average");
            System.out.println("4. Find Highest");
            System.out.println("5. Exit");
            System.out.print("Enter your option: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addMarks(sc);
                    break;

                case 2:
                    display();
                    break;

                case 3:
                    findAvg();
                    break;

                case 4:
                    findHighest();
                    break;

                case 5:
                    System.out.println("Program Exited.");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
