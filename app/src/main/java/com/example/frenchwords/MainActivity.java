package com.example.frenchwords;

import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button buttonB, buttonG, buttonW;
    Spinner spinner;
    TextView title;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title = findViewById(R.id.title);
        buttonB = findViewById(R.id.buttonBlack);
        buttonG = findViewById(R.id.buttonGreen);
        buttonW = findViewById(R.id.buttonWhite);

        spinner =findViewById(R.id.spinner);

        String[] choice = {"Color", "Food"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,  // Custom layout for spinner items
                R.id.spinner_item_text,  // ID of TextView in the custom layout
                choice
        );

        spinner.setAdapter(adapter);
        spinner.setSelection(1);


        spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = spinner.getSelectedItem().toString();
                if (selectedItem.equals("Color")) {
                    setupColorButtons();
                } else if (selectedItem.equals("Food")) {
                    setupFoodButtons();
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupColorButtons() {
        buttonB.setText("Black");
        buttonB.setOnClickListener(v -> playSound(R.raw.black));
        buttonG.setText("Green");
        buttonG.setOnClickListener(v -> playSound(R.raw.green));
        buttonW.setText("White");
        buttonW.setOnClickListener(v -> playSound(R.raw.blanc));
    }

    private void setupFoodButtons() {
        buttonB.setText("Bread");
        buttonB.setOnClickListener(v -> playSound(R.raw.bread));
        buttonG.setText("Chicken");
        buttonG.setOnClickListener(v -> playSound(R.raw.chicken));
        buttonW.setText("Cheese");
        buttonW.setOnClickListener(v -> playSound(R.raw.cheese));

        // Use ContextCompat.getColor() to get the color
        buttonB.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.breadTint)));
        buttonG.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.chickenTint)));
        buttonW.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.cheeseTint)));
    }


    private void playSound(int soundResId) {
        // Stop and release any currently playing sound
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Initialize new MediaPlayer and play the sound
        mediaPlayer = MediaPlayer.create(MainActivity.this, soundResId);
        mediaPlayer.start();

        // Release resources after the sound has finished playing
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    }
}