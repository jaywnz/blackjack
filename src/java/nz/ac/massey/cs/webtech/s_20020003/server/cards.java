/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_20020003.server;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jay
 */
public class cards {
        // Deck construction adapted from https://www.coderscampus.com/enums/ and
// http://users.csc.calpoly.edu/~jdalbey/103/Lectures/Enums.html
    
    public enum Suit {
        HEARTS,
        DIAMONDS,
        CLUBS,
        SPADES;
    }
    
    public enum Value {
        ACE("A", 11),
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("J", 10),
        QUEEN("Q", 10),
        KING("K", 10);
        
        private final String abbr;
        private final int num;
        Value(final String abbr, final int num) {
            this.abbr = abbr;
            this.num = num;
        }
        
        public String getAbbr() {
            return abbr;
        }
        
        public int getNum() {
            return num;
        }
    }
 
    public static class Card {
        public final Suit suit;
        public final Value value;
        
        public Card(Suit suit, Value value) {
            this.suit = suit;
            this.value = value;
        }
        
        public Suit getSuit() {
            return suit;
        }
        
        public Value getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Card{" + "suit=" + suit + ", value=" + value + '}';
        }
    }
    
    public static class Deck {
        private final ArrayList deck;

        public Deck() {
            this.deck = new ArrayList();
            // iterate enum card values
            for (int i=0; i<13; i++) {
                Value value = Value.values()[i];
                // iterate enum card suits
                for (int j=0; j<4; j++) {
                    Card card = new Card(Suit.values()[j], value);
//                    System.out.println(card.getValue());
//                    System.out.println(card.getSuit());
                    this.deck.add(card);                    
                }
            }
        }
        
        public Object dealTopCard() {
            Object top = deck.get(0);
            deck.remove(0);
            return top;
        }
                
        public void shuffle() {
            Collections.shuffle(deck);
        }

        @Override
        public String toString() {
            return "Deck{" + "deck=" + deck + '}';
        }  
    }
}
