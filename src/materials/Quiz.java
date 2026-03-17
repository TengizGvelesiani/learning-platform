package materials;

public class Quiz extends Material {

    private int timeLimitMinutes;
    private Question[] questions;

    public Quiz(int limit, Question[] questions) {
        super("Quiz");
        this.timeLimitMinutes = limit;
        this.questions = questions;
    }

    public int getTimeLimit() {
        return timeLimitMinutes;
    }

    public void setTimeLimit(int limit) {
        this.timeLimitMinutes = limit;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public int getNumberOfQuestions() {
        return questions == null ? 0 : questions.length;
    }

    @Override
    public String getDisplayName() {
        return "Quiz (" + getNumberOfQuestions() + " questions, " + timeLimitMinutes + " min)";
    }
}
