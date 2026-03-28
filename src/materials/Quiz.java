package materials;

import java.util.ArrayList;
import java.util.List;

import domain.MaterialKind;

public class Quiz extends Material {

    private int timeLimitMinutes;
    private final List<Question> questions;

    public Quiz(int limit, List<Question> questions) {
        super("Quiz");
        this.timeLimitMinutes = limit;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
    }

    public int getTimeLimit() {
        return timeLimitMinutes;
    }

    public void setTimeLimit(int limit) {
        this.timeLimitMinutes = limit;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getNumberOfQuestions() {
        return questions.size();
    }

    @Override
    public String getDisplayName() {
        return "Quiz (" + getNumberOfQuestions() + " questions, " + timeLimitMinutes + " min)";
    }

    @Override
    public MaterialKind getMaterialKind() {
        return MaterialKind.QUIZ;
    }
}
