
interface Question {
    void displayQuestion(boolean[] removed);
    int getCorrectAnswerIndex();
    String getOption(int index);
}

public class QuizQuestion implements Question {

    private String question;
    private String[] options;
    private int correctAnswer;

    public QuizQuestion(String question, String[] options,
                        int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void displayQuestion(boolean[] removed) {

        System.out.println("\n" + question);

        for (int i = 0; i < options.length; i++) {

            char letter = (char) ('A' + i);

            if (removed[i]) {
                System.out.println(letter + ". [Removed]");
            } else {
                System.out.println(letter + ". " + options[i]);
            }
        }
    }

    @Override
    public int getCorrectAnswerIndex() {
        return correctAnswer;
    }

    @Override
    public String getOption(int index) {
        return options[index];
    }
}

