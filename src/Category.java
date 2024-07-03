import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Card> cards;

    public Category(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean addCard(Card card) {
        if (cards.contains(card)) return true;
        if (cards.size() < 4) {
            cards.add(card);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object category) {
        if (!(category instanceof Category)) return false;
        return name.equals(((Category) category).getName());
    }

    @Override
    public String toString() {
        return name;
    }

    public Card[] getCardArr() {
        return cards.toArray(new Card[cards.size()]);
    }
}
