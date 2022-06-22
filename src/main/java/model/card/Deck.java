package model.card;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {

    private List<Card> cards = new LinkedList<>();

    private static Deck instance = null;

    private Deck() {
        // Add cards
        for(int i = 1; i <= 4; i++) {
            for(int j = 1; j <= 10; j++) {
                cards.add(new SimpleCard(i));
            }
        }
        for(int i = 1; i <= 12; i++)
            cards.add(new SpecialCard());

        // Cards' order must be random
        Collections.shuffle(cards);
    }

    public static Deck getInstance() {
        if(instance == null)
            instance = new Deck();

        return instance;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffleCards() {
        Collections.shuffle(cards);
    }


    public Card takeCard() {
        Card card = cards.get(0);

        cards.remove(0);
        cards.add(51, card);

        return card;
    }
}
