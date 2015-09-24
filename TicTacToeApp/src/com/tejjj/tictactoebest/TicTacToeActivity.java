package com.tejjj.tictactoebest;


import com.tejjj.tictactoebest.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TicTacToeActivity extends Activity{

	private Tictactoe mGame;

	private Button mBoardButtons[];
	private int counter;
	private TextView mInfoTextView;
	private TextView mHumanCount;
	private TextView mTieCount;
	private TextView mAndroidCount;
	private Button newGame,exitGame;
	
	private int mHumanCounter = 0;
	private int mTieCounter = 0;
	private int mAndroidCounter = 0;

	private boolean mHumanFirst = true;
	private boolean mGameOver = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBoardButtons = new Button[mGame.getBOARD_SIZE()];
		mBoardButtons[0] = (Button) findViewById(R.id.one);
		mBoardButtons[1] = (Button) findViewById(R.id.two);
		mBoardButtons[2] = (Button) findViewById(R.id.three);
		mBoardButtons[3] = (Button) findViewById(R.id.four);
		mBoardButtons[4] = (Button) findViewById(R.id.five);
		mBoardButtons[5] = (Button) findViewById(R.id.six);
		mBoardButtons[6] = (Button) findViewById(R.id.seven);
		mBoardButtons[7] = (Button) findViewById(R.id.eight);
		mBoardButtons[8] = (Button) findViewById(R.id.nine);
		newGame			 = (Button) findViewById(R.id.newgame);
		exitGame 		 = (Button) findViewById(R.id.exitgame);
		mInfoTextView = (TextView) findViewById(R.id.information);
		mHumanCount = (TextView) findViewById(R.id.humanCount);
		mTieCount = (TextView) findViewById(R.id.tiesCount);
		mAndroidCount = (TextView) findViewById(R.id.androidCount);
		mHumanCount.setText(Integer.toString(mHumanCounter));
		mTieCount.setText(Integer.toString(mTieCounter));
		mAndroidCount.setText(Integer.toString(mAndroidCounter));
		mInfoTextView.setTextColor(Color.BLACK);
		mGame = new Tictactoe();
		newGame.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startNewGame();
			}

		});
		exitGame.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TicTacToeActivity.this.finish();
			}

		});
		startNewGame();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.newGame:
			startNewGame();
			break;
		case R.id.exitGame:
			TicTacToeActivity.this.finish();
			break;

		case R.id.aboutUs:
			Intent i = new Intent(TicTacToeActivity.this,AboutUs.class);
			startActivity(i);
			break;


		}

		return true;
	}

	private void startNewGame()
	{
		mGame.clearBoard();
		mInfoTextView.setTextColor(Color.BLACK);
		
		for (int i = 0; i < mBoardButtons.length; i++)
		{
			mBoardButtons[i].setText("");
			mBoardButtons[i].setEnabled(true);
			mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
		}

		if (mHumanFirst)
		{
			mInfoTextView.setText(R.string.first_human);
			mHumanFirst = false;
			counter++;
		}
		else
		{
			mInfoTextView.setText(R.string.turn_computer);
			int move = mGame.getComputerMove();
			setMove(mGame.ANDROID_PLAYER, move);
			if(counter>0){
				mInfoTextView.setText(R.string.turn_human);
				counter = 0;
			}
			mHumanFirst = true;
		}
		mGameOver = false;
	}

	private class ButtonClickListener implements View.OnClickListener
	{
		int location;

		public ButtonClickListener(int location)
		{
			this.location = location;
		}

		public void onClick(View view)
		{
			if (!mGameOver)
			{
				if (mBoardButtons[location].isEnabled())
				{
					setMove(mGame.HUMAN_PLAYER, location);

					int winner = mGame.checkForWinner();

					if (winner == 0)
					{
						mInfoTextView.setTextColor(Color.BLACK);
						mInfoTextView.setText(R.string.turn_computer);
						int move = mGame.getComputerMove();
						setMove(mGame.ANDROID_PLAYER, move);
						winner = mGame.checkForWinner();    					
					}

					if (winner == 0){
						mInfoTextView.setTextColor(Color.BLACK);
						mInfoTextView.setText(R.string.turn_human);
						mInfoTextView.setTextColor(Color.BLACK);
					}
					else if (winner == 1)
					{
						mInfoTextView.setText(R.string.result_tie);
						mInfoTextView.setTextColor(Color.MAGENTA);
						mTieCounter++;
						mTieCount.setText(Integer.toString(mTieCounter));
						mGameOver = true;
					}
					else if (winner == 2)
					{
						mInfoTextView.setText(R.string.result_human_wins);
						mInfoTextView.setTextColor(Color.GREEN);
						mHumanCounter++;
						mHumanCount.setText(Integer.toString(mHumanCounter));
						mGameOver = true;
					}
					else
					{
						mInfoTextView.setText(R.string.result_android_wins);
						mInfoTextView.setTextColor(Color.RED);
						mAndroidCounter++;
						mAndroidCount.setText(Integer.toString(mAndroidCounter));
						mGameOver = true;
					}
				}
			}
		}
	}

	private void setMove(char player, int location)
	{
		mGame.setMove(player, location);
		mBoardButtons[location].setEnabled(false);
		mBoardButtons[location].setText(String.valueOf(player));
		if (player == mGame.HUMAN_PLAYER)
			mBoardButtons[location].setTextColor(Color.GREEN);
		else
			mBoardButtons[location].setTextColor(Color.RED);
	}

}
