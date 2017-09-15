package cloudist.cc.passwordinputview_java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cloudist.cc.library.TextChangeListener;
import cloudist.cc.library.view.PasswordInputView;
import cloudist.cc.library.widget.InputPasswordDialog;

/**
 * Created by cloudist on 2017/9/13.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        final PasswordInputView passwordInputView = findViewById(R.id.password_inputview);
        final PasswordInputView passwordInputView2 = findViewById(R.id.password_inputview2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputPasswordDialog.newInstance()
                        .setTextChangeListener(new TextChangeListener() {
                            @Override
                            public void textChange(String text) {
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                            }
                        }).show(getSupportFragmentManager(), "");
            }
        });

        passwordInputView.bindKeyBoard(getSupportFragmentManager(), "");
        passwordInputView2.bindKeyBoard(getSupportFragmentManager(), "");

    }

}
