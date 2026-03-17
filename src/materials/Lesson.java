package materials;

public class Lesson extends Material {

    public Lesson(String content) {
        super(content);
    }

    public String getContent() {
        return name;
    }

    public void setContent(String content) {
        this.name = content;
    }

    @Override
    public String getDisplayName() {
        return "Lesson: " + name;
    }
}
