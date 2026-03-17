package materials;

public class Module extends Material {

    private Lesson[] lessons;
    private Quiz quiz;

    public Module(String name, Lesson[] lessons, Quiz quiz) {
        super(name);
        this.lessons = lessons;
        this.quiz = quiz;
    }

    public String getModuleName() {
        return name;
    }

    public void setModuleName(String name) {
        this.name = name;
    }

    public Lesson[] getLessons() {
        return lessons;
    }

    public void setLessons(Lesson[] lessons) {
        this.lessons = lessons;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String getDisplayName() {
        return "Module: " + name;
    }
}
