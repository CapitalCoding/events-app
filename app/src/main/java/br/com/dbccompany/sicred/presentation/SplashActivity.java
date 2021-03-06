package br.com.dbccompany.sicred.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dbccompany.sicred.presentation.board.BoardActivity;
import dagger.hilt.android.AndroidEntryPoint;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, BoardActivity.class));
        finish();
    }
}
