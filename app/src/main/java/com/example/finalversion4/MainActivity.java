package com.example.finalversion4;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    public int connection = -1;
    private BoardView boardView;
    private Board gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boardView = (BoardView) findViewById(R.id.board);
        gameEngine = new Board();
        boardView.setGameEngine(gameEngine);
        boardView.setMainActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameEnded(char c) {
        String msg = (c == 'T') ? "Game Ended. Tie" : "GameEnded. " + c + " win";

        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").
                setMessage(msg).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //newGame();
                    }
                }).show();
    }

    private void newGame() {
        gameEngine.newGame();
        boardView.invalidate();
    }

    public void onClick(View view) {
        final FloatingActionButton fab = findViewById(R.id.fab);
        final FABProgressCircle fabProgressCircle = findViewById(R.id.fabProgressCircle);
        fabProgressCircle.show();
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#b7b0b1")));
        fab.setImageDrawable(getResources().getDrawable(R.drawable.connecting));
        Snackbar.make(fabProgressCircle, "Connecting to Server...", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
        new Connection().execute();
        fab.setEnabled(false);
    }





    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String[] args = {"1"};
            ClientSocketModule client = new ClientSocketModule(args);
            connection = client.firstSocket();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            client.secondSocket();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            String output = "123";

            client.ThirdSocket(output);
            return null;
        }
    }
    public void doSth(View view) {
        new Connection().execute();
    }


}
