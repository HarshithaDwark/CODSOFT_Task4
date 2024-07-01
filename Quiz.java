import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

// Define a Quiz class
public class Quiz {
    private Question[] questions; // Array to hold Question objects
    private int currentQuestionIndex; // Index of the current question being displayed
    private int score; // Player's score
    private Scanner scanner; // Scanner object to read user input
    private Timer timer; // Timer object to handle question time limits

    // Constructor to initialize Quiz object with an array of questions
    public Quiz(Question[] questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.scanner = new Scanner(System.in); // Initialize Scanner to read from standard input
        this.timer = new Timer(); // Initialize Timer for handling question timeouts
    }

    // Method to start the quiz
    public void start() {
        // Iterate through each question in the array
        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            displayQuestion(questions[currentQuestionIndex]); // Display the current question
            boolean answeredInTime = getUserAnswer(); // Get user's answer within a time limit
            if (!answeredInTime) {
                System.out.println("Time's up!"); // Print message if time runs out
            }
        }
        displayResult(); // Display quiz results after all questions have been answered
    }

    // Method to display a question
    private void displayQuestion(Question question) {
        System.out.println(question.getQuestion()); // Display the question prompt
        String[] options = question.getOptions(); // Get the options for the question
        // Display each option with a number prefix
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ": " + options[i]);
        }
    }

    // Method to get user's answer for a question within a time limit
    private boolean getUserAnswer() {
        final boolean[] answered = {false}; // Flag to track if user has answered
        final boolean[] timedOut = {false}; // Flag to track if time has run out
        final TimerTask[] timerTask = {null}; // TimerTask to handle question timeout

        // Schedule a timer task to set timedOut to true after 10 seconds
        timerTask[0] = new TimerTask() {
            @Override
            public void run() {
                if (!answered[0]) {
                    timedOut[0] = true; // Set timedOut to true if user hasn't answered in time
                    System.out.println("Time's up!");
                    synchronized (scanner) {
                        scanner.nextLine(); // Consume any input to avoid blocking
                    }
                }
            }
        };
        timer.schedule(timerTask[0], 10000); // Schedule timer task to run after 10 seconds

        // Wait for user input within the time limit or until timed out
        while (!answered[0] && !timedOut[0]) {
            try {
                synchronized (scanner) {
                    if (scanner.hasNextInt()) { // Check if user input is an integer
                        int userAnswer = scanner.nextInt(); // Read user's answer
                        if (userAnswer < 1 || userAnswer > 4) { // Check if answer is within valid range
                            System.out.println("Please enter a valid option (1-4).");
                            continue;
                        }
                        // Check if user's answer is correct and update score accordingly
                        if (userAnswer - 1 == questions[currentQuestionIndex].getCorrectAnswer()) {
                            score++;
                        }
                        answered[0] = true; // Mark question as answered
                        timerTask[0].cancel(); // Cancel timer for the current question
                        timer = new Timer(); // Create a new timer for the next question
                    } else {
                        System.out.println("Please enter a valid option (1-4).");
                        scanner.next(); // Consume invalid input to avoid blocking
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input to avoid blocking
            }
        }

        return !timedOut[0]; // Return false if time ran out without an answer
    }

    // Method to display quiz results
    private void displayResult() {
        System.out.println("Quiz Over!");
        System.out.println("Your Score: " + score + "/" + questions.length); // Display player's score
        // Display each question and its correct answer
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Q" + (i + 1) + ": " + questions[i].getQuestion());
            System.out.println("Correct Answer: " + questions[i].getOptions()[questions[i].getCorrectAnswer()]);
        }
    }

    // Main method to start the quiz application
    public static void main(String[] args) {
        // Array of Question objects initialized with questions, options, and correct answers
        Question[] questions = {
            new Question("What is the size of an `int` variable in Java?", new String[]{"8 bits", "16 bits", "32 bits", "64 bits"}, 2),
            new Question("Which keyword is used to create a subclass in Java?", new String[]{"extends", "implements", "inherits", "subclass"}, 0),
            new Question("What is the default value of a boolean variable in Java?", new String[]{"true", "false", "0", "null"}, 1),
            new Question("Which method must be implemented by all threads in Java?", new String[]{"start()", "run()", "execute()", "init()"}, 1),
            new Question("What is the output of the expression `5 + 2 * 3` in Java?", new String[]{"21", "11", "16", "10"}, 1)
        };

        Quiz quiz = new Quiz(questions); // Create a Quiz object with the array of questions
        quiz.start(); // Start the quiz
    }
}
