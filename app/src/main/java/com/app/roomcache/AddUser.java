package com.app.roomcache;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import Room.UserRepository;

public class AddUser extends AppCompatActivity {

    EditText name,age;
    Button submit;
    UserRepository repo;
    ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        submit = findViewById(R.id.submit);
        prog = findViewById(R.id.prog);

        repo = new UserRepository(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prog.setVisibility(View.VISIBLE);

                String str1 = name.getText().toString();
                String str2 = age.getText().toString();

                if(str1.equals("") || str2.equals("")){

                    prog.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(),"Field is empty",Toast.LENGTH_SHORT).show();
                }else{

                 //   prog.setVisibility(View.GONE);
                    repo.addRemoteData(str1,str2);

                }

            }
        });
    }
}
