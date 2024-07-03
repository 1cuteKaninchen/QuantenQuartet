import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player extends JPanel {
    private final int WIDTH = 180;
    private final int HEIGHT = 80;
    private int FINALHIGHT;

    private int playerCount;
    private int id;
    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> hasNot;
    private JTextArea cards;
    private JTextArea cardsNo;
    private JTextField nameField;

    public Player(String name, int id, int playerCount) {
        FINALHIGHT = Math.min(HEIGHT * playerCount, 240);
        this.playerCount = playerCount;
        this.id = id;
        this.name = name;
        setLayout(new BorderLayout());
        hand = new ArrayList<>();
        hasNot = new ArrayList<>();
        nameField = new JTextField(name);
        nameField.setEditable(false);
        add(nameField, BorderLayout.NORTH);
        cards = new JTextArea();
        cards.setEditable(false);
        cards.setSize(WIDTH, HEIGHT / 2);
        add(cards, BorderLayout.CENTER);
        cardsNo = new JTextArea();
        cardsNo.setEditable(false);
        cardsNo.setSize(WIDTH, HEIGHT / 2);
        add(cardsNo, BorderLayout.SOUTH);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCard(Card card, Game game, RunGame runGame) {
        try {
            if (hasNot.contains(card)) {
                removeFromHasNot(card);
            }
        } catch (NullPointerException e) {}
        hand.add(card);
        cards.append("+ " + card + " (" + card.getCategory() + ")" + "\n");
        game.clearHasNot(card);
        checkQuartet(game, runGame);
    }

    public void removeFromHasNot(Card card) {
        hasNot.remove(card);
        cardsNo.setVisible(false);
        cardsNo = new JTextArea();
        for (Card c : hasNot) {
            cardsNo.append("- " + c + " (" + c.getCategory() + ")" + "\n");
        }
        cardsNo.setEditable(false);
        cardsNo.setSize(WIDTH, HEIGHT / 2);
        add(cardsNo, BorderLayout.SOUTH);
        cards.setVisible(true);
    }

    public void removeCard(Card card) {
        hand.remove(card);
        cards.setVisible(false);
        cards = new JTextArea();
        for (Card c : hand) {
            cards.append("+ " + c + " (" + c.getCategory() + ")" + "\n");
        }
        cards.setEditable(false);
        cards.setSize(WIDTH, HEIGHT / 2);
        add(cards, BorderLayout.CENTER);
        cards.setVisible(true);
    }

    public boolean hasCard(Card card) {
        return hand.contains(card);
    }

    @Override
    public boolean equals(Object player) {
        if (!(player instanceof Player)) {
            return false;
        }
        return id == ((Player) player).id;
    }

    @Override
    public String toString() {
        return name;
    }

    public void highlight() {
        nameField.setBackground(Color.green);
    }

    public void unhighlight() {
        nameField.setBackground(Color.white);
    }

    public void addHasNot(Card card) {
        hasNot.add(card);
        cardsNo.append("- " + card + " (" + card.getCategory() + ")" + "\n");
    }

    public ArrayList<Card> getHasNot() {
        return hasNot;
    }

    public int getFINALHIGHT() {
        return FINALHIGHT;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void checkQuartet(Game game, RunGame runGame) {
        for (Category category : game.getCategoryArr()) {
            boolean hasAll = true;
            for (Card card : category.getCards()) {
                if (!hasCard(card) || category.getCardArr().length != 4) {
                    hasAll = false;
                }
            }
            if (hasAll) {
                runGame.gameOver(this, category);
            }
        }
    }

    public boolean mustHaveCard(Game game, Card card) {
        boolean result = true;
        for (Player player : game.getPlayers()) {
            if (player.equals(this)) continue;
            if (!player.getHasNot().contains(card)) result = false;
        }
        return result;
    }
}
