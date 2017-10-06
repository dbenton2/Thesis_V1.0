package com.example.daniel.thesis_v10;

import android.media.Image;
import android.util.Log;

/**
 * Created by Daniel on 12/7/2016.
 */

public class Card {

    //create the three variables the cards will need
    private Suit suit;
    private int rank;
    public String face;
    public int playerID;
    boolean played;

    //no argument constructor
    public Card(){
        suit = Suit.values()[0];
        rank = 0;
        played = false;
    }

    //two argument constructor
    public Card(Suit suit, int rank){
        this.suit = suit;
        this.rank = rank;
        face = "ic_"+suit.name()+"_"+rank;
        played = false;
    }

    //returns the rank of the card
    public int getRank(Suit leadSuit){
      if(suit == leadSuit && rank < 15) {
          return rank + 10;
      }

        return rank;
    }

    //sets the rank of the card used for trump
    public void setRank(int trumpValue){
        rank = rank+trumpValue;
    }

    //returns the suit of the card
    public Suit getSuit(){
        return suit;
    }

    //returns the face string for the card
    public String getFace(){
        return face;
    }

    //sets the playerID of the card
    public void setPlayerID(int ID){
        playerID = ID;

    }

    //returns the playerID of the card
    public int getPlayerID(){
        return playerID;
    }

    //finds the lowest card in an array of cards
    public int lowCard(Card[] card, Suit humanSuit, Suit leadSuit){
        int lowCardRank = 1000;
        int lowCardIndex = -1;
        for (int i = 0; i < 5; i++) {
            int ordinal = card[i].getSuit().ordinal();
            if(ordinal <= 2) ordinal += 2;
            else ordinal -= 2;
            //Log.v("Cards " + i, "rank: " + card[i].getRank() +" played?: " + card[i].getPlayed());
            if(card[i].getRank(leadSuit) < lowCardRank && card[i].getSuit() == humanSuit && card[i].getPlayed() == false){
                if(humanSuit.ordinal() == ordinal  &&  card[i].getRank(leadSuit) > 30) {
                }
                else {
                    lowCardIndex = i;
                    lowCardRank = card[i].getRank(leadSuit);
                }
            }
            if(card[i].getRank(leadSuit) < lowCardRank && humanSuit.ordinal() == ordinal  &&  card[i].getRank(leadSuit) > 30 && card[i].getPlayed() == false){
                lowCardIndex = i;
                lowCardRank = card[i].getRank(leadSuit);
            }
            Log.v("Cards " + i, "rank: " + card[i].getRank(leadSuit) +" played?: " + card[i].getPlayed());
        }
        if(lowCardIndex == -1) {
            for (int i = 0; i < 5; i++) {
                if (card[i].getRank(leadSuit) < lowCardRank && card[i].getPlayed() == false) {
                    lowCardIndex = i;
                    lowCardRank = card[i].getRank(leadSuit);
                }
            }
        }
        if(lowCardIndex == -1) {
            Log.v("lowCardError:","lowCardIndex is -1");
        }
        return lowCardIndex;
    }

    //finds the highest card in an array of cards
    public int highCard(Card[] card, Suit leadSuit){
        Log.i("High Card", "High card function was called");
        int highCard = 0;
        for (int i = 0; i <5; i++){
            if(card[i].getRank(leadSuit) > card[highCard].getRank(leadSuit) && card[i].getPlayed() == false){
                highCard = i;
            }
        }
        return highCard;
    }

    //return if a card has been played
    public boolean getPlayed(){
        return played;
    }

    //change if a card has been played
    public void setPlayed(boolean x){
        played = x;
    }

    public String getProperName(){
        return suit.name()+" " + rank;
    }
}
