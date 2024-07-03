import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardBoxUpdater implements ActionListener {
    private RunGame runGame;

    public CardBoxUpdater(RunGame runGame) {
        super();
        this.runGame = runGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        runGame.updateCardBox();
    }
}
