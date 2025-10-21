public enum CardCategory {
    PUPIL("Учнівська"),
    STUDENT("Студентська"),
    STANDARD("Звичайна");

    private final String description;

    CardCategory(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}