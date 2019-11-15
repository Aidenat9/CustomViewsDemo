package me.tmgg.viewsdemoapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;

import me.tmgg.viewsdemoapp.R;

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.enter_pushin);
        Transition transition2 = TransitionInflater.from(this).inflateTransition(R.transition.return_pullout);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);
        getWindow().setEnterTransition(transition);
        getWindow().setReturnTransition(transition2);
    }
}
