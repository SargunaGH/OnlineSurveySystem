package com.OSS;
import java.util.*;

class Question {
    private String questionText;
    private List<String> options;
    private Map<String, Integer> responses;

    public Question(String questionText, List<String> options) {
        this.questionText = questionText;
        this.options = options;
        this.responses = new HashMap<>();
        for (String option : options) {
            responses.put(option, 0);
        }
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void recordResponse(String option) {
        if (responses.containsKey(option)) {
            responses.put(option, responses.get(option) + 1);
        }
    }

    public void displayResults() {
        System.out.println("\nResults for: " + questionText);
        for (Map.Entry<String, Integer> entry : responses.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }
}

class Survey {
    private String title;
    private List<Question> questions;

    public Survey(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

public class OnlineSurveySystem {
    static List<Survey> surveys = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Online Survey System Menu ===");
            System.out.println("1. Create a Survey");
            System.out.println("2. Take a Survey");
            System.out.println("3. View Survey Results");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createSurvey();
                case 2 -> takeSurvey();
                case 3 -> viewSurveyResults();
                case 4 -> {
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void createSurvey() {
        System.out.print("Enter the title of the survey: ");
        String title = scanner.nextLine();
        Survey survey = new Survey(title);

        System.out.print("How many questions does this survey have? ");
        int questionCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < questionCount; i++) {
            System.out.print("Enter the question text for question " + (i + 1) + ": ");
            String questionText = scanner.nextLine();

            System.out.print("How many options does this question have? ");
            int optionCount = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            List<String> options = new ArrayList<>();
            for (int j = 0; j < optionCount; j++) {
                System.out.print("Enter option " + (j + 1) + ": ");
                options.add(scanner.nextLine());
            }

            survey.addQuestion(new Question(questionText, options));
        }

        surveys.add(survey);
        System.out.println("Survey created successfully!");
    }

    static void takeSurvey() {
        if (surveys.isEmpty()) {
            System.out.println("No surveys available to take.");
            return;
        }

        System.out.println("\nAvailable Surveys:");
        for (int i = 0; i < surveys.size(); i++) {
            System.out.println((i + 1) + ". " + surveys.get(i).getTitle());
        }

        System.out.print("Choose a survey to take (Enter number): ");
        int surveyChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (surveyChoice < 1 || surveyChoice > surveys.size()) {
            System.out.println("Invalid survey choice.");
            return;
        }

        Survey selectedSurvey = surveys.get(surveyChoice - 1);
        System.out.println("\nTaking Survey: " + selectedSurvey.getTitle());

        for (Question question : selectedSurvey.getQuestions()) {
            System.out.println("\n" + question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            System.out.print("Choose an option (Enter number): ");
            int optionChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (optionChoice < 1 || optionChoice > options.size()) {
                System.out.println("Invalid option. Skipping this question.");
            } else {
                question.recordResponse(options.get(optionChoice - 1));
            }
        }

        System.out.println("\nThank you for completing the survey!");
    }

    static void viewSurveyResults() {
        if (surveys.isEmpty()) {
            System.out.println("No surveys available to view results.");
            return;
        }

        System.out.println("\nAvailable Surveys:");
        for (int i = 0; i < surveys.size(); i++) {
            System.out.println((i + 1) + ". " + surveys.get(i).getTitle());
        }

        System.out.print("Choose a survey to view results (Enter number): ");
        int surveyChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (surveyChoice < 1 || surveyChoice > surveys.size()) {
            System.out.println("Invalid survey choice.");
            return;
        }

        Survey selectedSurvey = surveys.get(surveyChoice - 1);
        System.out.println("\nResults for Survey: " + selectedSurvey.getTitle());
        for (Question question : selectedSurvey.getQuestions()) {
            question.displayResults();
        }
    }
}
