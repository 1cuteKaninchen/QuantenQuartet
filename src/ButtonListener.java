import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    private Game game;
    private RunGame runGame;
    private Category category;
    private Card card;
    private Player player;

    public ButtonListener(Game game, RunGame runGame) {
        super();
        this.runGame = runGame;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().equals("ask")) {
            if (!runGame.isAskable()) {
                JOptionPane.showMessageDialog(null, "specify a category and a card!", "Error", JOptionPane.ERROR_MESSAGE);
                runGame.enableBoxes();
                return;
            }
            runGame.disableBoxes();
            try {
                category = runGame.getChosenCategory();
                card = runGame.getChosenCard();
                runGame.setAskedCategory(category);
                runGame.setAskedCard(card);
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "specify a category and a card!", "Error", JOptionPane.ERROR_MESSAGE);
                runGame.enableBoxes();
                return;
            }
            player = runGame.getChosenPlayer();
            if (player.equals(game.getAtTurn())) {
                JOptionPane.showMessageDialog(null, "cant ask for a card from yourself!", "Error", JOptionPane.ERROR_MESSAGE);
                runGame.enableBoxes();
                return;
            }
            if (game.addCategory(category)) {
                if (category.addCard(card)) {
                    boolean cardIsHold = false;
                    for (Player p : game.getPlayers()) {
                        if (p.equals(runGame.getChosenPlayer())) continue;
                        if (p.getHand().contains(card)) {
                            cardIsHold = true;
                        }
                    }
                    if (cardIsHold) {
                        JOptionPane.showMessageDialog(null, "cant ask for a card you know the player doesn't have!", "Error", JOptionPane.ERROR_MESSAGE);
                        runGame.enableBoxes();
                        return;
                    }
                    if (player.getHasNot() != null) {
                        if (player.getHasNot().contains(runGame.getChosenCard())) {
                            System.out.println(runGame.getChosenCard());
                            System.out.println(runGame.getChosenCard().getCategory());
                            System.out.println(runGame.getChosenCategory());
                            JOptionPane.showMessageDialog(null, "1cant ask for a card you know the player doesn't have!", "Error", JOptionPane.ERROR_MESSAGE);
                            runGame.enableBoxes();
                            return;
                        }
                    }
                    if (player.getHand().contains(card)) {
                        player.removeCard(card);
                        game.getAtTurn().addCard(card, game, runGame);
                        runGame.updateBoxes();
                        runGame.enableBoxes();
                    } else if (player.mustHaveCard(game, card)) {
                        game.getAtTurn().addCard(card, game, runGame);
                        runGame.updateBoxes();
                        runGame.enableBoxes();
                    } else {
                        runGame.setWaitingForAnswer(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "category is already full!", "Error", JOptionPane.ERROR_MESSAGE);
                    runGame.enableBoxes();
                }
            } else {
                JOptionPane.showMessageDialog(null, "to many categories!", "Error", JOptionPane.ERROR_MESSAGE);
                runGame.enableBoxes();
            }
        };
        if (((JButton) e.getSource()).getText().equals("yes")) {
            player = runGame.getChosenPlayer();
            game.getAtTurn().addCard(runGame.getAskedCard(), game, runGame);
            runGame.setWaitingForAnswer(false);
        };
        if (((JButton) e.getSource()).getText().equals("no")) {
            runGame.getChosenPlayer().addHasNot(runGame.getAskedCard());
            runGame.setWaitingForAnswer(false);
            game.changeAtTurn();
        };
        if (((JButton) e.getSource()).getText().equals("reset")) {
            runGame.dispose();
            new Main();
        };
    }
}
