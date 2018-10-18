package com.mahmoudmabrok.mo3sec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.spAlgo)
    Spinner spAlgo;
    @BindView(R.id.edKey)
    EditText edKey;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    private void showMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
