public class Question {
    // The Question class defines a structure for storing quiz questions, their possible answers, and the correct answer.

    private String question;
    // This private field stores the text of the quiz question.

    private String[] options;
    // This private field stores the multiple-choice options for the quiz question as an array of strings.

    private int correctAnswer;
    // This private field stores the index of the correct answer in the options array.

    public Question(String question, String[] options, int correctAnswer) {
        // This is the constructor for the Question class. It initializes the question text, options, and the index of the correct answer.

        this.question = question;
        // The 'this.question' refers to the current instance's question field, and it is set to the value of the parameter 'question'.

        this.options = options;
        // The 'this.options' refers to the current instance's options field, and it is set to the value of the parameter 'options'.

        this.correctAnswer = correctAnswer;
        // The 'this.correctAnswer' refers to the current instance's correctAnswer field, and it is set to the value of the parameter 'correctAnswer'.
    }

    public String getQuestion() {
        // This public method returns the question text.

        return question;
        // It returns the value of the question field.
    }

    public String[] getOptions() {
        // This public method returns the array of multiple-choice options.

        return options;
        // It returns the value of the options field.
    }

    public int getCorrectAnswer() {
        // This public method returns the index of the correct answer in the options array.

        return correctAnswer;
        // It returns the value of the correctAnswer field.
    }
}
