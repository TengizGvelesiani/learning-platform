package materials;

public class Module {

    private String moduleName;
    private Lesson[] lessons;
    private Quiz quiz;

    public Module(String name, Lesson[] lessons, Quiz quiz) {
        this.moduleName = name;
        this.lessons = lessons;
        this.quiz = quiz;
    }

    public String getModuleName() { return moduleName; }
    public void setModuleName(String name) { this.moduleName = name; }

    public Lesson[] getLessons() { return lessons; }
    public void setLessons(Lesson[] lessons) { this.lessons = lessons; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }
}
