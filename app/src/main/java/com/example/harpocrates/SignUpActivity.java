package com.example.harpocrates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

    private EditText user,pw,cpw;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        user=findViewById(R.id.usernameField);
        pw=findViewById(R.id.passwordField);
        cpw=findViewById(R.id.confirmPasswordField);
        signUpButton=findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!StashDataBase.getInstance(getApplicationContext()).usernameExists(user.getText().toString())){
                        if (passwordConfirmed()){
                            StashDataBase.getInstance(getApplicationContext()).createAccount(
                                    user.getText().toString(),pw.getText().toString());
                            Snackbar.make(v,"account created", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            finish();
                        }else{
                            Snackbar.make(v,"passwords do not match", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }else{
                        Snackbar.make(v,"username in use", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                } catch ( Exception e ) {
                    Toast.makeText( getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private boolean passwordConfirmed() {
        return pw.getText().toString().equals(cpw.getText().toString());
    }
}
