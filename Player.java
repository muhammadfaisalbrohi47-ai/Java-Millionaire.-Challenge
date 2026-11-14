
public class Player {

    private String name;
    private int prizeMoney;
    private int correctAnswers;

    public Player(String name) {
        this.name = name;
        this.prizeMoney = 0;
        this.correctAnswers = 0;
    }

    public String getName() {
        return name;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void updatePrize(int amount) {
        prizeMoney = amount;
        correctAnswers++;
    }

    public void securePrize(int amount) {
        prizeMoney = amount;
    }

    public void displayResult() {

        System.out.println("\n================================");
        System.out.println("          FINAL RESULT");
        System.out.println("================================");

        System.out.println("Player Name: " + name);
        System.out.println("Correct Answers: " + correctAnswers);
        System.out.println("Winning Amount: Rs. " + prizeMoney);

        System.out.println("================================");
    }
}

