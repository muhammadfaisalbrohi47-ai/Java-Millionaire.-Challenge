
import java.util.Scanner;
import java.util.Random;

public class Game {

    private Scanner input;
    private Random random = new Random();

    private Player player;
    private Question[] questions;

    private int[] prizes = {
        1000, 2000, 3000, 5000, 10000,
        20000, 40000, 80000, 160000, 320000
    };

    private boolean fiftyUsed = false;
    private boolean audienceUsed = false;
    private boolean phoneUsed = false;

    public Game(Player player, Scanner input) {
        this.player = player;
        this.input = input;
        createQuestions();
    }

    private void createQuestions() {

        questions = new Question[] {

            new QuizQuestion(
                "Which language is used for Android development?",
                new String[]{"Java", "HTML", "CSS", "SQL"}, 0),

            new QuizQuestion(
                "What is the capital of Pakistan?",
                new String[]{"Karachi", "Lahore", "Islamabad", "Quetta"}, 2),

            new QuizQuestion(
                "Which company originally developed Java?",
                new String[]{"Microsoft", "Sun Microsystems",
                              "Google", "Apple"}, 1),

            new QuizQuestion(
                "How many bits are in one byte?",
                new String[]{"4", "8", "16", "32"}, 1),

            new QuizQuestion(
                "Which planet is known as the Red Planet?",
                new String[]{"Earth", "Venus", "Mars", "Jupiter"}, 2),

            new QuizQuestion(
                "Which data structure uses FIFO?",
                new String[]{"Stack", "Queue", "Tree", "Graph"}, 1),

            new QuizQuestion(
                "Who invented the telephone?",
                new String[]{"Newton", "Alexander Graham Bell",
                              "Einstein", "Edison"}, 1),

            new QuizQuestion(
                "Which keyword creates an object in Java?",
                new String[]{"class", "static", "new", "void"}, 2),

            new QuizQuestion(
                "What does CPU stand for?",
                new String[]{"Central Processing Unit",
                              "Computer Personal Unit",
                              "Central Program Utility",
                              "Control Processing User"}, 0),

            new QuizQuestion(
                "Which one is NOT an OOP concept?",
                new String[]{"Inheritance", "Polymorphism",
                              "Encapsulation", "Compilation"}, 3)
        };
    }

    public void startGame() {

        System.out.println("\n================================");
        System.out.println("     JAVA MILLIONAIRE CHALLENGE");
        System.out.println("================================");

        System.out.println("Welcome, " + player.getName() + "!");

        for (int i = 0; i < questions.length; i++) {

            boolean[] removed = new boolean[4];

            System.out.println("\n--------------------------------");
            System.out.println("Question " + (i + 1) + " of 10");
            System.out.println("Prize: Rs. " + prizes[i]);
            System.out.println("--------------------------------");

            boolean answered = false;

            while (!answered) {

                questions[i].displayQuestion(removed);

                System.out.println("\nA/B/C/D = Answer");
                System.out.println("L = Lifelines");
                System.out.println("Q = Quit and Take Money");

                System.out.print("Your Choice: ");

                String choice = input.nextLine().trim().toUpperCase();

                if (choice.equals("L")) {

                    useLifeline(i, removed);

                } else if (choice.equals("Q")) {

                    System.out.println("\nYou decided to quit!");
                    player.displayResult();
                    return;

                } else if (choice.matches("[A-D]")) {

                    int answer = choice.charAt(0) - 'A';

                    if (removed[answer]) {
                        System.out.println("This option was removed!");
                        continue;
                    }

                    if (answer == questions[i].getCorrectAnswerIndex()) {

                        System.out.println("\nCORRECT ANSWER!");

                        player.updatePrize(prizes[i]);

                        System.out.println("You won Rs. " + prizes[i]);

                        answered = true;

                    } else {

                        System.out.println("\nWRONG ANSWER!");

                        int guaranteedPrize = 0;

                        if (player.getCorrectAnswers() >= 5) {
                            guaranteedPrize = prizes[4];
                        }

                        player.securePrize(guaranteedPrize);

                        System.out.println("Correct answer was: "
                            + (char) ('A' +
                            questions[i].getCorrectAnswerIndex()));

                        System.out.println("GAME OVER!");

                        player.displayResult();
                        return;
                    }

                } else {

                    System.out.println("Invalid choice! Try again.");
                }
            }
        }

        System.out.println("\nCONGRATULATIONS!");
        System.out.println("You answered all 10 questions!");
        System.out.println("You are the MILLIONAIRE CHAMPION!");

        player.displayResult();
    }

    private void useLifeline(int q, boolean[] removed) {

        System.out.println("\n========== LIFELINES ==========");
        System.out.println("1. 50:50");
        System.out.println("2. Audience Poll");
        System.out.println("3. Phone a Friend");
        System.out.println("0. Back to Question");

        System.out.print("Select Lifeline: ");

        String choice = input.nextLine().trim();

        switch (choice) {

            case "1":
                fiftyFifty(q, removed);
                break;

            case "2":
                audiencePoll(q);
                break;

            case "3":
                phoneFriend(q);
                break;

            case "0":
                break;

            default:
                System.out.println("Invalid Lifeline!");
        }
    }

    // LIFELINE 1: 50:50

    private void fiftyFifty(int q, boolean[] removed) {

        if (fiftyUsed) {
            System.out.println("50:50 already used!");
            return;
        }

        fiftyUsed = true;

        int correct = questions[q].getCorrectAnswerIndex();

        int count = 0;

        while (count < 2) {

            int option = random.nextInt(4);

            if (option != correct && !removed[option]) {
                removed[option] = true;
                count++;
            }
        }

        System.out.println("Two incorrect options removed!");
    }

    // LIFELINE 2: AUDIENCE POLL

    private void audiencePoll(int q) {

        if (audienceUsed) {
            System.out.println("Audience Poll already used!");
            return;
        }

        audienceUsed = true;

        int correct = questions[q].getCorrectAnswerIndex();

        int[] votes = new int[4];

        votes[correct] = 60 + random.nextInt(21);

        int remaining = 100 - votes[correct];

        int lastWrong = -1;

        for (int i = 0; i < 4; i++) {
            if (i != correct) {
                lastWrong = i;
            }
        }

        for (int i = 0; i < 4; i++) {

            if (i != correct) {

                if (i == lastWrong) {
                    votes[i] = remaining;
                } else {
                    votes[i] = random.nextInt(remaining + 1);
                    remaining -= votes[i];
                }
            }
        }

        System.out.println("\n===== AUDIENCE POLL =====");

        for (int i = 0; i < 4; i++) {

            System.out.println((char) ('A' + i)
                    + ": " + votes[i] + "%");
        }
    }

    // LIFELINE 3: PHONE A FRIEND

    private void phoneFriend(int q) {

        if (phoneUsed) {
            System.out.println("Phone a Friend already used!");
            return;
        }

        phoneUsed = true;

        int correct = questions[q].getCorrectAnswerIndex();

        int suggestion;

        if (random.nextInt(100) < 70) {

            suggestion = correct;

        } else {

            do {
                suggestion = random.nextInt(4);
            } while (suggestion == correct);
        }

        System.out.println("\nCalling your friend...");

        System.out.println("Your friend says: I think option "
                + (char) ('A' + suggestion) + " is correct!");

        System.out.println("This is only a suggestion!");
    }
}

