import javax.swing.*;
import java.awt.*;

public class RunGame extends JFrame {

    private boolean askable = false;
    private Card askedCard;
    private Category askedCategory;
    private Game game;
    private boolean gameOver = false;
    private boolean waitingForAnswer;
    private JComboBox<Player> playerBox;
    private JComboBox<Category> categoryBox;
    private JComboBox<Card> cardBox;
    private JButton ask = new JButton("ask");
    private JButton yes = new JButton("yes");
    private JButton no = new JButton("no");
    private JButton reset = new JButton("reset");

    public RunGame(Game game, Player[] players) {
        setTitle("Quanten Quartet");
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel playerPanel = new JPanel();
        if (players.length < 8) {
            playerPanel.setLayout(new GridLayout((int) Math.ceil(Math.sqrt(players.length)) - 1, (int) Math.ceil(Math.sqrt(players.length)) + 1));
        } else {
            playerPanel.setLayout(new GridLayout(2, (int) Math.ceil(players.length / 2)));
        }
        for (Player player : players) {
            playerPanel.add(player);
        }
        add(playerPanel, BorderLayout.CENTER);

        JPanel comboboxPanel = new JPanel();
        playerBox = new JComboBox(players);
        playerBox.setEditable(false);
        String[] initialCategoryBox = {"input your category"};
        categoryBox = new JComboBox(initialCategoryBox);
        categoryBox.addActionListener(new CardBoxUpdater(this));
        categoryBox.setEditable(true);
        String[] initialCardBox = {"input your card"};
        cardBox = new JComboBox(initialCardBox);
        cardBox.setEditable(true);
        comboboxPanel.add(playerBox);
        comboboxPanel.add(categoryBox);
        comboboxPanel.add(cardBox);
        add(comboboxPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        ask.addActionListener(new ButtonListener(game, this));
        yes.addActionListener(new ButtonListener(game, this));
        yes.setEnabled(false);
        no.addActionListener(new ButtonListener(game, this));
        no.setEnabled(false);
        reset.addActionListener(new ButtonListener(game, this));
        buttonPanel.add(ask);
        buttonPanel.add(yes);
        buttonPanel.add(no);
        buttonPanel.add(reset);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(Math.max(players[0].getWIDTH() * (players[0].getPlayerCount() / 2), 480), Math.max(40 + players[0].getFINALHIGHT() * ((int) Math.ceil(Math.sqrt(players.length)) - 1), 360));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void setWaitingForAnswer(boolean waitingForAnswer) {
        if (waitingForAnswer) {
            this.waitingForAnswer = true;
            ask.setEnabled(false);
            yes.setEnabled(true);
            no.setEnabled(true);
        } else {
            this.waitingForAnswer = false;
            ask.setEnabled(true);
            yes.setEnabled(false);
            no.setEnabled(false);
            updateBoxes();
            enableBoxes();
        }
    }

    public void disableBoxes() {
        playerBox.setEnabled(false);
        categoryBox.setEnabled(false);
        cardBox.setEnabled(false);
    }

    public void enableBoxes() {
        playerBox.setEnabled(true);
        categoryBox.setEnabled(true);
        cardBox.setEnabled(true);
    }

    public Player getChosenPlayer() {
        return (Player) playerBox.getSelectedItem();
    }

    public Card getChosenCard() throws NullPointerException {
        return new Card(cardBox.getSelectedItem().toString(), getChosenCategory());
    }

    public Category getChosenCategory() throws NullPointerException {
        if (categoryBox.getSelectedItem().getClass().equals(Category.class)) {
            return (Category) categoryBox.getSelectedItem();
        }
        return new Category(categoryBox.getSelectedItem().toString());
    }

    public void setAskedCard(Card askedCard) {
        this.askedCard = askedCard;
    }

    public void setAskedCategory(Category askedCategory) {
        this.askedCategory = askedCategory;
    }

    public Card getAskedCard() {
        return askedCard;
    }

    public Category getAskedCategory() {
        return askedCategory;
    }

    public void updateBoxes() {
        categoryBox.removeAllItems();
        for (Category category : game.getCategoryArr()) {
            categoryBox.addItem(category);
        }
        updateCardBox();
    }

    public void updateCardBox() {
        askable = true;
        cardBox.removeAllItems();
        try {
            if (categoryBox.getSelectedItem().getClass().equals(String.class)) return;
            for (Card card : ((Category) categoryBox.getSelectedItem()).getCardArr()) {
                cardBox.addItem(card);
            }
        } catch (NullPointerException e) {}
    }

    public void gameOver(Player Winner, Category fullQuartet) {
        JOptionPane.showMessageDialog(null, Winner + " has won by collecting all cards of " + fullQuartet + "!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isAskable() {
        return askable;
    }
}