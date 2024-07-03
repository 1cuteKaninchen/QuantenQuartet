public class Card {
    private String name;
    private Category category;

    public Card(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + category + ")";
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object card) {
        if (!(card instanceof Card)) {
            return false;
        }
        return name.equals(((Card) card).getName()) && category.equals(((Card) card).getCategory());
    }


}
