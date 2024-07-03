import java.util.ArrayList;

public class Game {
    private Player[] players;
    private int maxCategories;
    private ArrayList<Category> categories;
    private Player atTurn;

    public Game(Player[] players) {
        this.players = players;
        maxCategories = players.length;
        categories = new ArrayList<>();
        atTurn = players[0];
        atTurn.highlight();
        new RunGame(this, players);
    }

    public boolean addCategory(Category category) {
        if (categories.contains(category)) return true;
        if (categories.size() < maxCategories) {
            categories.add(category);
            return true;
        }
        return false;
    }

    public Player getAtTurn() {
        return atTurn;
    }

    public void changeAtTurn() {
        atTurn.unhighlight();
        if (players[players.length - 1].equals(atTurn)) {
            atTurn = players[0];
        } else {
            atTurn = players[atTurn.getId() + 1];
        }
        atTurn.highlight();
    }

    public Player[] getPlayers() {
        return players;
    }

    public Category[] getCategoryArr() {
        return categories.toArray(new Category[categories.size()]);
    }

    public void clearHasNot(Card card) {
        for (Player player : players) {
            try {
                if (player.getHasNot().contains(card)) player.removeFromHasNot(card);
            } catch (NullPointerException e) {}
        }
    }
}
