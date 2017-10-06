package com.example.daniel.thesis_v10;



import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class easyGameActivity extends AppCompatActivity implements View.OnClickListener {
    Player human = new Player();
    Player opp1 = new Player();
    Player teamMate = new Player();
    Player opp2 = new Player();
    ArrayList<Player> players = new ArrayList<> ();// {human, opp1, teamMate, opp2};
    Deck deck = new Deck();
    Card[] playedCards = new Card[4];
    int tricks = 0;
    int round = 0;
    int team1 = 0;
    int team2 = 0;
    int cardPlayed;
    int[] trickWins = new int[5];
    Card pickUpCard = new Player();
    boolean humanPass = false;
    boolean order = false;
    boolean cardChanged = false;
    Suit leadSuit = Suit.hearts;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_game);

        players.add(human);
        players.add(opp1);
        players.add(teamMate);
        players.add(opp2);

        TextView textView = (TextView) findViewById(R.id.ScoreBoard);
        textView.setText(team1 + " - " + team2);
        textView.invalidate();

        //set the human player to be first to start the game
        human.setLeadPlayer(true);

        //deal cards to each player for first round
        dealCards();

    }

    public void logRanks(Card[] roundCards) {
        for(int i = 0; i <4 ; i++) {
            Log.v("Ranks", "Player: " + i + " : " + roundCards[i].getRank(leadSuit));
        }
    }

    //determine who the winning player is
    public void checkWinner(Card[] roundCards, int round){
        int winnerID = 5;
        Log.i("CheckWinner", "Tricks="+tricks+ " round="+round);
        //determine which card won
        logRanks(roundCards);
        if(roundCards[0].getRank(leadSuit)>roundCards[1].getRank(leadSuit) && roundCards[0].getRank(leadSuit)>roundCards[2].getRank(leadSuit) && roundCards[0].getRank(leadSuit)>roundCards[3].getRank(leadSuit)){
            winnerID = roundCards[0].getPlayerID();
            Log.i("CheckWinner", "Human won round");
            human.setLeadPlayer(true);
            opp1.setLeadPlayer(false);
            opp2.setLeadPlayer(false);
            teamMate.setLeadPlayer(false);
        }
        else if(roundCards[1].getRank(leadSuit) > roundCards[0].getRank(leadSuit) && roundCards[1].getRank(leadSuit) > roundCards[2].getRank(leadSuit) && roundCards[1].getRank(leadSuit) > roundCards[3].getRank(leadSuit)){
            winnerID = roundCards[1].getPlayerID();
            Log.i("CheckWinner", "opp1 won round");
            human.setLeadPlayer(false);
            opp1.setLeadPlayer(true);
            opp2.setLeadPlayer(false);
            teamMate.setLeadPlayer(false);
        }
        else if(roundCards[2].getRank(leadSuit) > roundCards[0].getRank(leadSuit) && roundCards[2].getRank(leadSuit) > roundCards[1].getRank(leadSuit) && roundCards[2].getRank(leadSuit) > roundCards[3].getRank(leadSuit)){
            winnerID = roundCards[2].getPlayerID();
            Log.i("CheckWinner", "opp2 won round");
            human.setLeadPlayer(false);
            opp1.setLeadPlayer(false);
            opp2.setLeadPlayer(true);
            teamMate.setLeadPlayer(false);
        }
        else if(roundCards[3].getRank(leadSuit) > roundCards[0].getRank(leadSuit) && roundCards[3].getRank(leadSuit) > roundCards[1].getRank(leadSuit) && roundCards[3].getRank(leadSuit) > roundCards[2].getRank(leadSuit)){
            winnerID = roundCards[3].getPlayerID();
            Log.i("CheckWinner", "tm won round");
            human.setLeadPlayer(false);
            opp1.setLeadPlayer(false);
            opp2.setLeadPlayer(false);
            teamMate.setLeadPlayer(true);
        }

        //keep track of which team won the trick
        if (winnerID == 0 || winnerID == 2){
            trickWins[tricks-1] = 1;
        }
        else trickWins[tricks-1] = 2;
    }

    //The AI to play a card
    public int easyAI(Player player, Card [] playedCards){
        int playCard;
        //Card [] hand = player.getHand();
        if(playedCards[0] == null){
            playCard = player.highCard(player.getHand(), leadSuit);
            //showPlayedCard(player.getFace(playCard),player);
        }
        else if (playedCards[0].getPlayerID()== 1 || playedCards[0].getPlayerID()== 3){
            playCard = player.highCard(player.getHand(), leadSuit);
            //showPlayedCard(player.getFace(playCard),player);
        }
        else{
            playCard = player.lowCard(player.getHand(), playedCards[0].getSuit(), leadSuit);
            //showPlayedCard(player.getFace(playCard),player);
        }
        player.setPlayed(playCard);
        return playCard;
    }

    //listener that listens for which card is selected to play
    public void onClick(View view){
        Button cb = (Button) findViewById(R.id.continue_button);
        if(cb.isEnabled() == true)
            return;
        if(cardChanged == true)
            return;
        switch (view.getId()){
            case R.id.p1_c1:
                cardPlayed = 0;
                cardChanged = true;
                findViewById(R.id.p1_c1).setEnabled(false);
                findViewById(R.id.player_played_card).invalidate();
                Log.i("onClick","Called invalidate on card 1");
                break;
            case R.id.p1_c2:
                cardPlayed = 1;
                cardChanged = true;
                findViewById(R.id.p1_c2).setEnabled(false);
                findViewById(R.id.player_played_card).invalidate();
                Log.i("onClick","Called invalidate on card 2");
                break;
            case R.id.p1_c3:
                cardPlayed = 2;
                cardChanged = true;
                findViewById(R.id.p1_c3).setEnabled(false);
                findViewById(R.id.player_played_card).invalidate();
                Log.i("onClick","Called invalidate on card 3");
                break;
            case R.id.p1_c4:
                cardPlayed = 3;
                cardChanged = true;
                findViewById(R.id.p1_c4).setEnabled(false);
                Log.i("onClick","Called invalidate on card 4");
                break;
            case R.id.p1_c5:
                cardPlayed = 4;
                cardChanged = true;
                findViewById(R.id.p1_c5).setEnabled(false);
                Log.i("onClick","Called invalidate on card 5");
                break;
        }


        View image = findViewById(R.id.player_played_card);
        String face = human.getCard(cardPlayed).getFace();
        int id = getResources().getIdentifier(face,"drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(id);
        image.setBackgroundDrawable(drawable);
        view.setBackgroundResource(0);
        view.invalidate();
        image.invalidate();

        playTrick(1);
    }

    //changes the score at the end of the round and sets up the next round
    public void determineScore(int[] roundWins){
        int sum = 0;
        for(int i = 0; i < 5; i++) {
            sum = sum + roundWins[i];
        }

        if(sum == 5){
            team1 = team1 + 2;
        }
        else if(sum < 8){
            team1 = team1 + 1;
        }
        else if(sum == 10){
            team2 = team2 + 2;
        }
        else{
            team2 = team2 + 1;
        }

        TextView textView = (TextView) findViewById(R.id.ScoreBoard);
        textView.setText(team1 + " - " + team2);
        textView.invalidate();
        Log.i("Set Score", "Set the score after the trick was played");

        if(team1 >= 10 || team2 >=10){
            Log.i("game over? win/lose","did we hit one of these conditions");
            if(team1>= 10) {
                TextView winLoseTextView = (TextView) findViewById(R.id.winLoseText);
                winLoseTextView.setText("You Win!!");
                findViewById(R.id.winLoseText).setEnabled(true);
                winLoseTextView.invalidate();
            }
            else if(team2 >= 10){
                TextView winLoseTextView = (TextView) findViewById(R.id.winLoseText);
                winLoseTextView.setText("You Lose :(");
                findViewById(R.id.winLoseText).setEnabled(true);
                winLoseTextView.invalidate();
            }
        }
        //reset everything
        else{
            deck = new Deck();
            human = new Player();
            opp1 = new Player();
            teamMate = new Player();
            opp2 = new Player();
            players.set(0, human);
            players.set(1, opp1);
            players.set(2, teamMate);
            players.set(3, opp2);

            //determineOrderUp();
            tricks = 0;
            round = round + 1;
            playedCards = new Card[4];
            //set the next player to be first based on who went first the previous round
            if(round == 4 || round == 8 || round == 12 || round == 16){
                human.setLeadPlayer(true);
                teamMate.setLeadPlayer(false);
                opp2.setLeadPlayer(false);
                opp1.setLeadPlayer(false);
                Log.i("Set lead", "hum set to lead player");
            }
            else if(round == 1 || round == 5 || round == 9 || round == 13 || round == 17){
                human.setLeadPlayer(false);
                teamMate.setLeadPlayer(false);
                opp2.setLeadPlayer(false);
                opp1.setLeadPlayer(true);
                Log.i("Set lead", "opp1 set to lead player");
            }
            else if(round == 2 || round == 6 || round == 10 || round == 14 || round == 18){
                human.setLeadPlayer(false);
                teamMate.setLeadPlayer(true);
                opp2.setLeadPlayer(false);
                opp1.setLeadPlayer(false);
                Log.i("Set lead", "tm set to lead player");
            }
            else if(round == 3 || round == 7 || round == 11 || round == 15 || round == 19){
                human.setLeadPlayer(false);
                teamMate.setLeadPlayer(false);
                opp2.setLeadPlayer(true);
                opp1.setLeadPlayer(false);
                Log.i("Set lead", "opp2 set to lead player");
            }

            dealCards();
            findViewById(R.id.p1_c1).setEnabled(true);
            findViewById(R.id.p1_c2).setEnabled(true);
            findViewById(R.id.p1_c3).setEnabled(true);
            findViewById(R.id.p1_c4).setEnabled(true);
            findViewById(R.id.p1_c5).setEnabled(true);
            showCardFaces();
        }
    }

    //determines trump for the computer players and asks the human if they want to make trump
    public void determineTrump(Player human, Player teamMate, Player opp1, Player opp2){
        Log.i("determine trump", "called");
        boolean trumpSet = false;
        while(!trumpSet) {
            if (human.getLeadPlayer() || (!opp1.getTrump() || !teamMate.getTrump() || !opp2.getTrump())) {
                Log.i("human", "determined trump and is calling calculate trump");

                AlertDialog.Builder builder = new AlertDialog.Builder(easyGameActivity.this);
                builder.setMessage("Select Trump")
                        .setPositiveButton("Clubs", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String trump = "clubs";
                                calculateTrump(trump);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Pass", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builder.show();
                trumpSet = true;

                human.setTrump(false);
            }
            else if (opp1.getLeadPlayer() || (!human.getTrump() && !teamMate.getTrump() && !opp2.getTrump())) {
                Log.i("Opp1", "determined trump and is calling calculate trump");
                if ((opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs) || (opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs) || (opp1.getCard(2).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs) || (opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs)) {

                    String trump = "clubs";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades) || (opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades) || (opp1.getCard(2).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades) || (opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades)) {

                    String trump = "spades";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts) || (opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts) || (opp1.getCard(2).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts) || (opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts)) {

                    String trump = "hearts";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds) || (opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds) || (opp1.getCard(2).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds) || (opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds)) {

                    String trump = "diamonds";
                    calculateTrump(trump);
                    trumpSet = true;
                }
                else {
                    opp1.setTrump(false);
                }

            }
            else if (teamMate.getLeadPlayer() || (!human.getTrump() && !opp1.getTrump() && !opp2.getTrump())) {
                Log.i("team mate", "determined trump and is calling calculate trump");
                if ((teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs) || (teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs) || (teamMate.getCard(2).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs) || (teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs)) {

                    String trump = "clubs";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades) || (teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades) || (teamMate.getCard(2).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades) || (teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades)) {

                    String trump = "spades";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts) || (teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts) || (teamMate.getCard(2).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts) || (teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts)) {

                    String trump = "hearts";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds) || (teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds) || (teamMate.getCard(2).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds) || (teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds)) {

                    String trump = "diamonds";
                    calculateTrump(trump);
                    trumpSet = true;
                }
                else {
                    teamMate.setTrump(false);
                }

            }
            else if (opp2.getLeadPlayer() || (!human.getTrump() && !teamMate.getTrump() && !opp1.getTrump())) {
                Log.i("Opp2", "determined trump and is calling calculate trump");
                if ((opp2.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs) || (opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs) || (opp2.getCard(2).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs) || (opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs)) {

                    String trump = "clubs";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades) || (opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades) || (opp2.getCard(2).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades) || (opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades)) {

                    String trump = "spades";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts) || (opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts) || (opp2.getCard(2).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts) || (opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts)) {

                    String trump = "hearts";
                    calculateTrump(trump);
                    trumpSet = true;
                } else if ((opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds) || (opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds) || (opp2.getCard(2).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds) || (opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds)) {

                    String trump = "diamonds";
                    calculateTrump(trump);
                    trumpSet = true;
                }
                else {
                    opp2.setTrump(false);
                }


            }else if(opp1.getTrump() && human.getTrump() && opp2.getTrump() && teamMate.getTrump()){
                //need to deal a new deck and start at the beginning of the process without losing the current score
                deck = new Deck();
                dealCards();
            }
        }


    }

    //adds to the rank of each card to give them a higher value if they are trump.
    public void calculateTrump(String suit){
        int ordinal = Suit.valueOf(suit).ordinal();
        if(ordinal == 1 || ordinal == 2){
            ordinal = ordinal + 2;
        }
        else{ordinal = ordinal - 2;}

        Log.i("calculate trump of",suit +" for human");
        for(int i = 0; i<5; i++){
            if(human.getCard(i).getSuit() == Suit.valueOf(suit) && human.getCard(i).getRank(Suit.Null) == 11){
                human.getCard(i).setRank(35);
            }
            else if (human.getCard(i).getSuit() == Suit.values()[ordinal] && human.getCard(i).getRank(Suit.Null) == 11){
                human.getCard(i).setRank(34);
            }
            else if(human.getCard(i).getSuit() == Suit.valueOf(suit)){
                human.getCard(i).setRank(30);
            }
        }

        Log.i("calculate trump of",suit +" for opp1");
        for(int i = 0; i<5; i++){
            if(opp1.getCard(i).getSuit() == Suit.valueOf(suit) && opp1.getCard(i).getRank(Suit.Null) == 11){
                opp1.getCard(i).setRank(35);
            }
            else if (opp1.getCard(i).getSuit() == Suit.values()[ordinal] && opp1.getCard(i).getRank(Suit.Null) == 11){
                opp1.getCard(i).setRank(34);
            }
            else if(opp1.getCard(i).getSuit() == Suit.valueOf(suit)){
                opp1.getCard(i).setRank(30);
            }
        }

        Log.i("calculate trump of",suit +" for team mate");
        for(int i = 0; i<5; i++){
            if(teamMate.getCard(i).getSuit() == Suit.valueOf(suit) && teamMate.getCard(i).getRank(Suit.Null) == 11){
                teamMate.getCard(i).setRank(35);
            }
            else if (teamMate.getCard(i).getSuit() == Suit.values()[ordinal] && teamMate.getCard(i).getRank(Suit.Null) == 11){
                teamMate.getCard(i).setRank(34);
            }
            else if(teamMate.getCard(i).getSuit() == Suit.valueOf(suit)){
                teamMate.getCard(i).setRank(30);
            }
        }

        Log.i("calculate trump of",suit +" for opp2");
        for(int i = 0; i<5; i++){
            if(opp2.getCard(i).getSuit() == Suit.valueOf(suit) && opp2.getCard(i).getRank(Suit.Null) == 11){
                opp2.getCard(i).setRank(35);
            }
            else if (opp2.getCard(i).getSuit() == Suit.values()[ordinal] && opp2.getCard(i).getRank(Suit.Null) == 11){
                opp2.getCard(i).setRank(34);
            }
            else if(opp2.getCard(i).getSuit() == Suit.valueOf(suit)){
                opp2.getCard(i).setRank(30);
            }
        }
        TextView textView = (TextView) findViewById(R.id.Trump);
        textView.setText("Trump is: " + suit);
        textView.invalidate();
        //if(!human.leadPlayer) {
            //playTrick(0);
        //}
    }

    //Asks the player to pass or order up and then the AI decides to order up or not
    public void determineOrderUp() {
        boolean opp1Pass = false;
        boolean opp2Pass = false;
        boolean teamMatePass = false;
        String orderUp;
        Log.i("determine Order Up", "called");
        if (opp1.leadPlayer) {
            orderUp = "Pick up the card?";
        } else if (human.leadPlayer || teamMate.leadPlayer) {
            orderUp = "Order up your opponent?";
        } else {
            orderUp = "Order up your team mate?";
        }


        calculateTrump(pickUpCard.getSuit().toString());

        if (false) {
            int i;
            for (i = 0; i < 4; i++) {
                Log.i("for loop", "i is currently: " + i);
                if ((human.leadPlayer || opp2Pass) && !humanPass && i == 1) {
                    Log.i("determine Order Up", "showing  dialog");
                    AlertDialog.Builder builder = new AlertDialog.Builder(easyGameActivity.this);
                    builder.setMessage(orderUp)
                            .setPositiveButton("Pass", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    humanPass = true;
                                    findViewById(R.id.pick_up_card).setEnabled(false);
                                    findViewById(R.id.pick_up_card).invalidate();
                                    Log.i("OrderUpDialog", "humanPass was set to true");
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Order Up", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    humanOrderedUP();
                                    findViewById(R.id.pick_up_card).setEnabled(false);
                                    findViewById(R.id.pick_up_card).invalidate();
                                    dialog.dismiss();
                                }
                            });

                    builder.show();

                } else if (opp1.leadPlayer || humanPass) {
                    Log.v("opp1", "opp1 is determining to order up");
                    if ((opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs) || (opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs) || (opp1.getCard(2).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(2).getSuit() == Suit.clubs) || (opp1.getCard(0).getSuit() == Suit.clubs && opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs) || (opp1.getCard(3).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp1.getCard(4).getSuit() == Suit.clubs)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }

                    } else if ((opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades) || (opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades) || (opp1.getCard(2).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(2).getSuit() == Suit.spades) || (opp1.getCard(0).getSuit() == Suit.spades && opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades) || (opp1.getCard(3).getSuit() == Suit.spades && opp1.getCard(1).getSuit() == Suit.spades && opp1.getCard(4).getSuit() == Suit.spades)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }

                    } else if ((opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts) || (opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts) || (opp1.getCard(2).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(2).getSuit() == Suit.hearts) || (opp1.getCard(0).getSuit() == Suit.hearts && opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts) || (opp1.getCard(3).getSuit() == Suit.hearts && opp1.getCard(1).getSuit() == Suit.hearts && opp1.getCard(4).getSuit() == Suit.hearts)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }

                    } else if ((opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds) || (opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds) || (opp1.getCard(2).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(2).getSuit() == Suit.diamonds) || (opp1.getCard(0).getSuit() == Suit.diamonds && opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds) || (opp1.getCard(3).getSuit() == Suit.diamonds && opp1.getCard(1).getSuit() == Suit.diamonds && opp1.getCard(4).getSuit() == Suit.diamonds)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }

                    } else {
                        opp1Pass = true;
                    }
                    calculateTrump(pickUpCard.getSuit().toString());

                } else if (teamMate.leadPlayer || opp1Pass) {

                    if ((teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs) || (teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs) || (teamMate.getCard(2).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(2).getSuit() == Suit.clubs) || (teamMate.getCard(0).getSuit() == Suit.clubs && teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs) || (teamMate.getCard(3).getSuit() == Suit.clubs && teamMate.getCard(1).getSuit() == Suit.clubs && teamMate.getCard(4).getSuit() == Suit.clubs)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else if ((teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades) || (teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades) || (teamMate.getCard(2).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(2).getSuit() == Suit.spades) || (teamMate.getCard(0).getSuit() == Suit.spades && teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades) || (teamMate.getCard(3).getSuit() == Suit.spades && teamMate.getCard(1).getSuit() == Suit.spades && teamMate.getCard(4).getSuit() == Suit.spades)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else if ((teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts) || (teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts) || (teamMate.getCard(2).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(2).getSuit() == Suit.hearts) || (teamMate.getCard(0).getSuit() == Suit.hearts && teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts) || (teamMate.getCard(3).getSuit() == Suit.hearts && teamMate.getCard(1).getSuit() == Suit.hearts && teamMate.getCard(4).getSuit() == Suit.hearts)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else if ((teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds) || (teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds) || (teamMate.getCard(2).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(2).getSuit() == Suit.diamonds) || (teamMate.getCard(0).getSuit() == Suit.diamonds && teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds) || (teamMate.getCard(3).getSuit() == Suit.diamonds && teamMate.getCard(1).getSuit() == Suit.diamonds && teamMate.getCard(4).getSuit() == Suit.diamonds)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else {
                        teamMatePass = true;
                    }
                    calculateTrump(pickUpCard.getSuit().toString());


                } else if (opp2.leadPlayer || teamMatePass) {

                    if ((opp2.getCard(0).getSuit() == Suit.clubs && opp1.getCard(1).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs) || (opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs) || (opp2.getCard(2).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(2).getSuit() == Suit.clubs) || (opp2.getCard(0).getSuit() == Suit.clubs && opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs) || (opp2.getCard(3).getSuit() == Suit.clubs && opp2.getCard(1).getSuit() == Suit.clubs && opp2.getCard(4).getSuit() == Suit.clubs)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else if ((opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades) || (opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades) || (opp2.getCard(2).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(2).getSuit() == Suit.spades) || (opp2.getCard(0).getSuit() == Suit.spades && opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades) || (opp2.getCard(3).getSuit() == Suit.spades && opp2.getCard(1).getSuit() == Suit.spades && opp2.getCard(4).getSuit() == Suit.spades)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else if ((opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts) || (opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts) || (opp2.getCard(2).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(2).getSuit() == Suit.hearts) || (opp2.getCard(0).getSuit() == Suit.hearts && opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts) || (opp2.getCard(3).getSuit() == Suit.hearts && opp2.getCard(1).getSuit() == Suit.hearts && opp2.getCard(4).getSuit() == Suit.hearts)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else if ((opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds) || (opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds) || (opp2.getCard(2).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(2).getSuit() == Suit.diamonds) || (opp2.getCard(0).getSuit() == Suit.diamonds && opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds) || (opp2.getCard(3).getSuit() == Suit.diamonds && opp2.getCard(1).getSuit() == Suit.diamonds && opp2.getCard(4).getSuit() == Suit.diamonds)) {
                        if (opp1.leadPlayer) {
                            orderUp(human, pickUpCard);
                        } else if (teamMate.leadPlayer) {
                            orderUp(opp1, pickUpCard);
                        } else if (opp2.leadPlayer) {
                            orderUp(teamMate, pickUpCard);
                        } else if (human.leadPlayer) {
                            orderUp(opp2, pickUpCard);
                        }
                    } else {
                        opp2Pass = true;
                    }
                    calculateTrump(pickUpCard.getSuit().toString());


                } else if (humanPass && opp1Pass && opp2Pass && teamMatePass) {
                    determineTrump(human, teamMate, opp1, opp2);

                }
            }
        }
    }

    //add the card to the AI player's hand
    public void orderUp(Player player, Card pickUpCard){
        if(player == human){
            AlertDialog.Builder builder = new AlertDialog.Builder(easyGameActivity.this);
            builder.setMessage("Choose a card to get rid of")
                    .setPositiveButton("1", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("2", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });

            builder.show();
        }
        else {
            Log.i("player was ordered up","low card called");
            player.addCard(pickUpCard, player.lowCard(player.getHand(), pickUpCard.getSuit(), leadSuit));
        }
        //This calculateTrump calls adjust the trump to be the picked up card's suit
        calculateTrump(pickUpCard.getSuit().toString());


    }

    public int whichSpot(Player p) {
        if(p == human)
            return 0;
        if(p == opp1)
            return 1;
        if(p == opp2)
            return 2;
        else
            return 3;
    }

    //run the game engine till all tricks have been completed
    public void takeTurn(Player p) {
        int pos = whichSpot(p);
        if(p == human){
            if(cardChanged) {
                playedCards[0] = human.playCard(cardPlayed);
            }
        }
        else {
            playedCards[pos] = p.playCard(easyAI(p,playedCards));
            showPlayedCard(playedCards[pos].getFace(), p);
        }
    }


    public void takeFinalTurns(Player p) {
        takeTurn(human);
        if(p == human) {
            takeTurn(opp1);
            takeTurn(teamMate);
            takeTurn(opp2);
        }
        else if(p == opp1) {

        }
        else if(p == teamMate) {
            takeTurn(opp1);
        }
        else if(p == opp2) {
            takeTurn(opp1);
            takeTurn(teamMate);
        }


        tricks++;
        Button cb = (Button) findViewById(R.id.continue_button);
        cb.setVisibility(VISIBLE);
        cb.setEnabled(true);
    }

    public void takeTurns(Player p) {
        if(p == human) {
        }
        else if(p == opp1) {
            takeTurn(opp1);
            leadSuit = playedCards[1].getSuit();
            takeTurn(teamMate);
            takeTurn(opp2);
            //remove the played humans card
            View image = findViewById(R.id.player_played_card);
            image.setBackgroundResource(0);
            image.invalidate();
        }
        else if(p == teamMate) {
            takeTurn(teamMate);
            leadSuit = playedCards[3].getSuit();
            takeTurn(opp2);
            //remove played humans and opp1
            View image = findViewById(R.id.player_played_card);
            image.setBackgroundResource(0);
            image.invalidate();
            View image2 = findViewById(R.id.opp1_played_card);
            image2.setBackgroundResource(0);
            image2.invalidate();
        }
        else {
            takeTurn(opp2);
            leadSuit = playedCards[2].getSuit();
        }
    }

    public void clickContinueButton(View v) {

        View image = findViewById(R.id.player_played_card);
        image.setBackgroundResource(0);
        image.invalidate();
        View image2 = findViewById(R.id.opp1_played_card);
        image2.setBackgroundResource(0);
        image2.invalidate();
        View image3 = findViewById(R.id.teamMate_played_card);
        image3.setBackgroundResource(0);
        image3.invalidate();
        View image4 = findViewById(R.id.opp2_played_card);
        image4.setBackgroundResource(0);
        image4.invalidate();

        Button cb = (Button) findViewById(R.id.continue_button);
        cb.setVisibility(INVISIBLE);
        cb.setEnabled(false);
        if(tricks == 5) {
            determineScore(trickWins);
            tricks = 0;
        }
        takeFirstTurns();
        cardChanged = false;
    }

public void takeFirstTurns() {
    if (tricks != 5) {
        if (opp1.leadPlayer) {
            takeTurns(opp1);
        } else if (teamMate.leadPlayer) {
            takeTurns(teamMate);
        } else if (opp2.leadPlayer) {
            takeTurns(opp2);
        }

    }
}

    public void playTrick(int gone){
        if(human.leadPlayer) {
            takeTurn(human);
            leadSuit = playedCards[0].getSuit();
        }
        Log.i("trick", "card Changed: "+cardChanged+" gone: " + gone);
        for(int i = 0; i < 4; i++) {
            if(players.get(i).leadPlayer) {
                takeFinalTurns(players.get(i));
                checkWinner(playedCards, round);
                //takeFirstTurns();
                break;
            }
        }

     //   cardChanged = false;

   }

    //does the order up if the player selects to order up in the dialog
    public void humanOrderedUP(){
        if(opp1.leadPlayer){
            orderUp(human, pickUpCard);
            order = true;
        }
        else if(teamMate.leadPlayer){
            orderUp(opp1, pickUpCard);
            order = true;
        }
        else if(opp2.leadPlayer){
            orderUp(teamMate, pickUpCard);
            order = true;
        }
        else if(human.leadPlayer){
            orderUp(opp2, pickUpCard);
            order = true;
        }
    }

    //deals the cards to each player
    public void dealCards(){
        Log.i("Deal cards:","the players are being dealt new cards");
        int count = 0;
        while(count < 5){
            human.addCard(deck.dealCard(), count);
            opp1.addCard(deck.dealCard(), count);
            teamMate.addCard(deck.dealCard(), count);
            opp2.addCard(deck.dealCard(), count);
            count = count+1;
        }

        //take the next card from the deck to be the order up card
        pickUpCard = deck.getCard(0);
        showCardFaces();
        determineOrderUp();
    }

    //shows the card faces for the player to see their cards
    public void showCardFaces(){

        View image1 = findViewById(R.id.p1_c1);
        String face = human.getFace(0);
        int id = getResources().getIdentifier(face,"drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(id);
        image1.setBackgroundDrawable(drawable);
        View image2 = findViewById(R.id.p1_c2);
        String face2 = human.getFace(1);
        int id2 = getResources().getIdentifier(face2,"drawable", getPackageName());
        Drawable drawable2 = getResources().getDrawable(id2);
        image2.setBackgroundDrawable(drawable2);
        View image3 = findViewById(R.id.p1_c3);
        String face3 = human.getFace(2);
        int id3 = getResources().getIdentifier(face3,"drawable", getPackageName());
        Drawable drawable3 = getResources().getDrawable(id3);
        image3.setBackgroundDrawable(drawable3);
        View image4 = findViewById(R.id.p1_c4);
        String face4 = human.getFace(3);
        int id4 = getResources().getIdentifier(face4,"drawable", getPackageName());
        Drawable drawable4 = getResources().getDrawable(id4);
        image4.setBackgroundDrawable(drawable4);
        View image5 = findViewById(R.id.p1_c5);
        String face5 = human.getFace(4);
        int id5 = getResources().getIdentifier(face5,"drawable", getPackageName());
        Drawable drawable5 = getResources().getDrawable(id5);
        image5.setBackgroundDrawable(drawable5);
        View image6 = findViewById(R.id.pick_up_card);
        String face6 = pickUpCard.getFace();
        int id6 = getResources().getIdentifier(face6,"drawable", getPackageName());
        Drawable drawable6 = getResources().getDrawable(id6);
        image6.setBackgroundDrawable(drawable6);
    }

    //shows the card faces for the AI players after the card has been played
    public void showPlayedCard(String face, Player player){
        Log.i("showPlayedCard", "called the function with face="+face);
            if (player == opp1) {
                face = face + "_horizontal";
                View image = findViewById(R.id.opp1_played_card);
                int id = getResources().getIdentifier(face, "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(id);
                image.setBackgroundDrawable(drawable);
                findViewById(R.id.opp1_played_card).invalidate();
            }
            else if (player == opp2){
                face = face + "_horizontal";
                View image = findViewById(R.id.opp2_played_card);
                int id = getResources().getIdentifier(face, "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(id);
                image.setBackgroundDrawable(drawable);
                findViewById(R.id.opp2_played_card).invalidate();
            }
            else if (player == teamMate){
                View image = findViewById(R.id.teamMate_played_card);
                int id = getResources().getIdentifier(face, "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(id);
                image.setBackgroundDrawable(drawable);
                findViewById(R.id.teamMate_played_card).invalidate();
            }
    }

}



/*


        if (human.getLeadPlayer()) {
            if(cardChanged) {
                Log.i("Trick", "Human was lead players");
                playedCards[0] = human.playCard(cardPlayed);
                playedCards[0].setPlayerID(0);
                findViewById(R.id.player_played_card).invalidate();
                playedCards[1] = opp1.playCard(easyAI(opp1,playedCards));
                playedCards[1].setPlayerID(1);
                showPlayedCard(playedCards[1].getFace(), opp1);
                View image = findViewById(R.id.p2_c1);
                image.setBackgroundResource(0);
                image.invalidate();
                playedCards[2] = teamMate.playCard(easyAI(teamMate,playedCards));
                playedCards[2].setPlayerID(2);
                showPlayedCard(playedCards[2].getFace(), teamMate);
                View image2 = findViewById(R.id.p3_c2);
                image2.setBackgroundResource(0);
                image2.invalidate();
                playedCards[3] = opp2.playCard(easyAI(opp2,playedCards));
                playedCards[3].setPlayerID(3);
                showPlayedCard(playedCards[3].getFace(), opp2);
                View image3 = findViewById(R.id.p4_c3);
                image3.setBackgroundResource(0);
                image3.invalidate();
                checkWinner(playedCards, tricks);
                //Log.i("trick", "can't stop looping");
                tricks = tricks + 1;
                cardChanged = false;

            }
        } else if (opp1.getLeadPlayer()) {
            Log.i("Trick", "opp1 was lead player");
            if(!cardChanged) {
                playedCards[0] = opp1.playCard(easyAI(opp1,playedCards));
                playedCards[0].setPlayerID(1);
                showPlayedCard(playedCards[0].getFace(), opp1);
                View image = findViewById(R.id.p2_c2);
                image.setBackgroundResource(0);
                image.invalidate();
                playedCards[1] = teamMate.playCard(easyAI(teamMate,playedCards));
                playedCards[1].setPlayerID(2);
                showPlayedCard(playedCards[1].getFace(), teamMate);
                View image2 = findViewById(R.id.p3_c3);
                image2.setBackgroundResource(0);
                image2.invalidate();
                playedCards[2] = opp2.playCard(easyAI(opp2,playedCards));
                playedCards[2].setPlayerID(3);
                showPlayedCard(playedCards[2].getFace(), opp2);
                View image3 = findViewById(R.id.p4_c4);
                image3.setBackgroundResource(0);
                image3.invalidate();
            }
            if(cardChanged) {
                playedCards[3] = human.playCard(cardPlayed);
                playedCards[3].setPlayerID(0);
                findViewById(R.id.player_played_card).invalidate();
                checkWinner(playedCards, tricks);
                tricks = tricks + 1;
                cardChanged = false;

            }
        } else if (teamMate.getLeadPlayer()) {
            Log.i("Trick", "teamMate was lead player");
            if(!cardChanged) {
                playedCards[0] = teamMate.playCard(easyAI(teamMate,playedCards));
                playedCards[0].setPlayerID(2);
                showPlayedCard(playedCards[0].getFace(), teamMate);
                playedCards[1] = opp2.playCard(easyAI(opp2,playedCards));
                playedCards[1].setPlayerID(3);
                showPlayedCard(playedCards[1].getFace(), opp2);
            }
            else {
                playedCards[2] = human.playCard(cardPlayed);
                playedCards[2].setPlayerID(0);
                playedCards[3] = opp1.playCard(easyAI(opp1,playedCards));
                playedCards[3].setPlayerID(1);
                showPlayedCard(playedCards[3].getFace(), opp1);
                findViewById(R.id.player_played_card).invalidate();
                checkWinner(playedCards, tricks);
                tricks = tricks + 1;
                cardChanged = false;
            }
        } else if (opp2.getLeadPlayer()) {
            Log.i("Trick", "opp2 was lead player");
            if(!cardChanged) {
                playedCards[0] = opp2.playCard(easyAI(opp2, playedCards));
                playedCards[0].setPlayerID(3);
                showPlayedCard(playedCards[0].getFace(), opp2);
            }
            else {
                playedCards[1] = human.playCard(cardPlayed);
                playedCards[1].setPlayerID(0);
                playedCards[2] = opp1.playCard(easyAI(opp1, playedCards));
                playedCards[2].setPlayerID(1);
                showPlayedCard(playedCards[2].getFace(), opp1);
                playedCards[3] = teamMate.playCard(easyAI(teamMate, playedCards));
                playedCards[3].setPlayerID(2);
                showPlayedCard(playedCards[3].getFace(), teamMate);
                findViewById(R.id.player_played_card).invalidate();
                checkWinner(playedCards, tricks);
                tricks = tricks + 1;
                cardChanged = false;
            }
        }


*/
