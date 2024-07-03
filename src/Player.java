import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player extends JPanel {
    private int id;
    private String name;
    private ArrayList<Card> hand;
    private JTextArea textArea;
    private JTextField nameField;

    public Player(String name, int id) {
        this.id = id;
        this.name = name;
        setLayout(new BorderLayout());
        hand = new ArrayList<>();
        nameField = new JTextField(name);
        nameField.setEditable(false);
        add(nameField, BorderLayout.NORTH);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setSize(240, 160);
        add(textArea, BorderLayout.CENTER);
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

    public void addCard(Card card) {
        hand.add(card);
        textArea.append(card + "\n");
    }

    public void removeCard(Card card) {
        hand.remove(card);
        textArea.setVisible(false);
        textArea = new JTextArea();
        for (Card c : hand) {
            textArea.append(c + "\n");
        }
        textArea.setEditable(false);
        textArea.setSize(240, 160);
        add(textArea, BorderLayout.CENTER);
        textArea.setVisible(true);
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
}
