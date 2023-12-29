import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    String question;
    List<String> options;
    int correctOption;

    public QuizQuestion(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}

public class QuizGame {
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex;
    private int userScore;
    private Timer timer;

    public QuizGame() {
        initializeQuestions();
        currentQuestionIndex = 0;
        userScore = 0;
    }

    private void initializeQuestions() {
        quizQuestions = new ArrayList<>();

        // Add new quiz questions, options, and correct answers
        quizQuestions.add(new QuizQuestion("What is the largest mammal on Earth?\n select 1-4:",
                List.of("Elephant", "Blue Whale", "Giraffe", "Lion"), 2));

        quizQuestions.add(new QuizQuestion("Who is known as the 'Father of Computer Science'? \nselect 1-4:",
                List.of("Alan Turing", "Steve Jobs", "Bill Gates", "Tim Berners-Lee"), 1));

        quizQuestions.add(new QuizQuestion("Which country is known as the 'Land of the Rising Sun'?\n select 1-4:?",
                List.of("China", "South Korea", "Japan", "Vietnam"), 3));

        quizQuestions.add(new QuizQuestion("What is the capital of Australia? \n select 1-4:",
                List.of("Sydney", "Melbourne", "Canberra", "Brisbane"), 3));

        quizQuestions.add(new QuizQuestion("Who painted the Mona Lisa? \n select 1-4:",
                List.of("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"), 3));

        // Add more questions as needed
    }

    private void displayQuestion() {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        System.out.println("Question: " + currentQuestion.question);
        for (int i = 0; i < currentQuestion.options.size(); i++) {
            System.out.println((i + 1) + ". " + currentQuestion.options.get(i));
        }
    }

    private void displayPrompt() {
        System.out.print("Select your answer (1-" + quizQuestions.get(currentQuestionIndex).options.size() + "): ");
    }

    private void startTimer(int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = seconds;

            @Override
            public void run() {
                System.out.print("\rTime remaining: " + count + " seconds:");
                count--;

                if (count < 0) {
                    System.out.println("\nTime's up! Moving to the next question.");
                    nextQuestion();
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void nextQuestion() {
        stopTimer();

        if (currentQuestionIndex < quizQuestions.size() - 1) {
            currentQuestionIndex++;
            displayQuestion();
            displayPrompt();
            startTimer(10); // Set the timer for the next question (10 seconds in this example)
        } else {
            endQuiz();
        }
    }

    private void endQuiz() {
        System.out.println("\nQuiz completed! Your final score: " + userScore);
    }

    public void playQuiz() {
        displayQuestion();
        displayPrompt();
        startTimer(10); // Set the initial timer for the first question (10 seconds in this example)

        Scanner scanner = new Scanner(System.in);
        while (currentQuestionIndex < quizQuestions.size()) {
            try {
                int userAnswer = scanner.nextInt();

                if (userAnswer == quizQuestions.get(currentQuestionIndex).correctOption + 1) {
                    System.out.println("Correct!\n");
                    userScore++;
                } else {
                    System.out.println("Incorrect. The correct answer was: " +
                            quizQuestions.get(currentQuestionIndex).options.get(
                                    quizQuestions.get(currentQuestionIndex).correctOption) + "\n");
                }

                nextQuestion();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume the invalid input
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        System.out.println("***************THE QUIZ GAME***************"); // Added semicolon at the end of this line
        QuizGame quizGame = new QuizGame();
        quizGame.playQuiz();
    }
}
