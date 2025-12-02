import java.util.List;

public class Developer {
    private String name;
    private List<String> languages;

    public Developer(String name, List<String> languages) {
        this.name = name;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public List<String> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return name + " " + languages;
    }
}