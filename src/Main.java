import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame implements ActionListener {
    private JTextArea playerNamesArea;
    private JTextField nameField;

    public Main () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("set your players!");
        playerNamesArea = new JTextArea();
        playerNamesArea.setSize(480, 320);
        add(playerNamesArea, BorderLayout.NORTH);
        JButton startButton = new JButton("start");
        startButton.addActionListener(this);
        add(startButton, BorderLayout.SOUTH);
        setSize(480, 320);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        runGame();
    }

    private void runGame() {
        ArrayList<Player> players = new ArrayList<>();
        int id = 0;
        for (String s : playerNamesArea.getText().split("\n")) {
            players.add(new Player(s, id));
            id++;
        }
        new Game(players.toArray(new Player[players.size()]));
        dispose();
    }

    public static void main(String[] args) {
        new Main();
    }
}