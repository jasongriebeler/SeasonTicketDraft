package griebeler.org.seasonticketdraft;

public class DraftPosition {
    private String name;
    private int position;
    private boolean completed;

    public String getName() {
        return name;
    }

    public DraftPosition setName(String name) {
        this.name = name;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public DraftPosition setPosition(int position) {
        this.position = position;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public DraftPosition setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }
}
