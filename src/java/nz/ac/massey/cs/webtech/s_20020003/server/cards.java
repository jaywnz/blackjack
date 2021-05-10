/*
 * Copyright (C) 2021 Jay
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
        ACE("A", 1),
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
            for (int i = 0; i < 13; i++) {
                Value value = Value.values()[i];
                // iterate enum card suits
                for (int j = 0; j < 4; j++) {
                    Card card = new Card(Suit.values()[j], value);
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
