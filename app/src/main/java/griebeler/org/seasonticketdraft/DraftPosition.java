package griebeler.org.seasonticketdraft;

import java.util.Date;

public class DraftPosition {
    private String name;
    private int position;
    private boolean completed;
    private Date dateTimeDate;
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getDateTimeDate() {
        return dateTimeDate;
    }

    public void setDateTimeDate(Date dateTimeDate) {
        this.dateTimeDate = dateTimeDate;
    }

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
