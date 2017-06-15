package com.example.akshat.innervoicewritepost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText quote;
    Button next;
    int MAXLINES;


    /*class newLine implements View.OnKeyListener {
        newLine() {
        }

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == 66 && event.getAction() == 1) {
                String text = ((EditText) v).getText().toString();
                int editTextRowCount = text.split("\\n").length;
                if (editTextRowCount >= MainActivity.this.MAXLINES) {
                    String newText = text.substring(0, text.lastIndexOf("\n"));
                    ((EditText) v).setText(BuildConfig.FLAVOR);
                    ((EditText) v).append(newText);
                }
            }
            return false;
        }
    }*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote = (EditText) findViewById(R.id.quote_edittext);
        next = (Button) findViewById(R.id.next_button);

        //quote.setOnKeyListener(new newLine());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,NextActivity.class);
                i.putExtra("quote", quote.getText().toString());
                startActivity(i);

            }
        });
    }
}
