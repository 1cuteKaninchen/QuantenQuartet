import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RunGame extends JFrame {

    private Card askedCard;
    private Category askedCategory;
    private Game game;
    private boolean gameOver = false;
    private boolean waitingForAnswer;
    private JComboBox<Player> playerBox;
    private JComboBox<String> categoryBox;
    private JComboBox<String> cardBox;
    private JButton ask = new JButton("ask");
    private JButton yes = new JButton("yes");
    private JButton no = new JButton("no");
    private JButton reset = new JButton("reset");

    public RunGame(Game game, Player[] players) {
        setTitle("Quanten Quartet");
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout((int) Math.ceil(Math.sqrt(players.length)), (int) Math.ceil(Math.sqrt(players.length))));
        for (Player player : players) {
            playerPanel.add(player);
        }
        add(playerPanel, BorderLayout.CENTER);

        JPanel comboboxPanel = new JPanel();
        playerBox = new JComboBox(players);
        playerBox.setEditable(false);
        categoryBox = new JComboBox();
        categoryBox.setEditable(true);
        cardBox = new JComboBox();
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

        setSize(240 * (int) Math.ceil(Math.sqrt(players.length)), 100 + 160 * (int) Math.ceil(Math.sqrt(players.length)));
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
            game.changeAtTurn();
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
        return new Card(cardBox.getSelectedItem().toString(), new Category(categoryBox.getSelectedItem().toString()));
    }

    public Category getChosenCategory() throws NullPointerException {
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
}