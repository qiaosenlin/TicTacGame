package com.example.tictac;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView player1Score, player2Score, playStatus;
    private Button[] buttons = new Button[9];
    private Button reset;

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    private boolean turn;

    /* p1 -> 0 "X"
       p2 -> 1 "O"
       empty -> 2
     */

    //set game state all 2 for empty game
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8}, //rows
            {0,3,6},{1,4,7},{2,5,8}, //cols
            {0,4,8},{2,4,6} //cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1Score = (TextView) findViewById(R.id.score1);
        player2Score = (TextView) findViewById(R.id.score2);
        playStatus = (TextView) findViewById(R.id.status);
        reset = (Button) findViewById(R.id.reset);

        for(int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);

            roundCount = 0;
            playerOneScoreCount = 0;
            playerTwoScoreCount = 0;
            turn = true; //player_1 turn

        }

    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); //get ID from the btn
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));
        if(turn){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFF34C"));
            gameState[gameStatePointer] = 0; //set to player 1
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1; //set to player 1
        }
        roundCount++;
        if(isWin()){
            if (turn){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"PlayerOne Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"PlayerTow Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if (roundCount == 9){
            playAgain();
            Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();

        } else {
            turn  = !turn;
        }
        if(playerOneScoreCount > playerTwoScoreCount){
            playStatus.setText("Player One is Winning!");
        } else if(playerOneScoreCount < playerTwoScoreCount){
            playStatus.setText("Player Two is Winning!");
        } else {
            playStatus.setText("");
        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playStatus.setText("");
                updatePlayerScore();
            }
        });

    }

    public boolean isWin(){
        boolean win = false;
        for(int []winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
               gameState[winningPosition[2]] == gameState[winningPosition[1]] &&
               gameState[winningPosition[0]] != 2){
                win = true;
            }
        }
        return win;
    }

    public void updatePlayerScore(){
        player1Score.setText(Integer.toString(playerOneScoreCount));
        player2Score.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        roundCount = 0;
        turn = true;
        for(int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}