package com.example.daniel.thesis_v10;

/**
 * Created by Daniel on 12/7/2016.
 */

public class Player extends Card {

    private Card[] hand;
    boolean leadPlayer = false;
    boolean trump = true;

    //no argument constructor to hold each player's cards
    public Player(){

        //the player's hand
        hand = new Card[5];
    }

    //function to add a card to the player's hand
    public void addCard(Card card, int index){
        hand[index] = card;
    }

    //function to play a card from the player's hand
    public Card playCard(int index){
        return hand[index];
    }

    //returns true to say this player goes first and false to say they do not.
    public boolean getLeadPlayer(){
        return leadPlayer;
    }

    //change whether or not the player is supposed to go first on next round.
    public void setLeadPlayer(boolean x){
        leadPlayer = x;
    }

    //return the player's hand
    public Card[] getHand(){
        return hand;
    }

    //returns what card is at the index supplied
    public Card getCard(int index){
        return hand[index];
    }

    //sets if the player set the trump suit
    public void setTrump(boolean x){trump = x;}

    //returns if the player set the trump suit
    public boolean getTrump(){return trump;}

    //returns the face of the card at the supplied index
    public String getFace(int index){
        return hand[index].getFace();
    }

    //set the played boolean to true so the card can't be played again
    public void setPlayed(int index){
        hand[index].setPlayed(true);
    }

}


