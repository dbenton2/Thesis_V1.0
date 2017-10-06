package com.example.daniel.thesis_v10;

import java.util.Random;

/**
 * Created by Daniel on 12/7/2016.
 */

public class Deck extends Card {

    private Card[] deck;

    //create a deck of cards and shuffle them
    public Deck(){

        //create an array to hold all of the cards
        deck = new Card[24];

        //create each individual card in its array spot
        int d = 0;
            for(int s = 1; s<5; s++){
                for(int n = 9; n<15; n++){
                    deck[d] = new Card(Suit.values()[s], n );
                    d++;
                }
            }

        //create a random number to shuffle the deck and a temp card to hold the shuffled card
        Random rnum = new Random();
        Card temp;

        int x;
        for(int i=0; i<24; i++){
            x = rnum.nextInt(24);
            temp = deck[i];
            deck[i] = deck[x];
            deck[x] = temp;
        }
    }

    //function to deal cards to players
    public Card dealCard(){

        //get the "top" card of the deck
        Card top = deck[0];

        //update each card so there is a new "top" of the deck
        for(int x = 1; x<24; x++){
             deck[x-1] = deck[x];
        }

        //send top card to the player
        return top;
    }

    public Card getCard(int index){
        return deck[index];
    }
}
