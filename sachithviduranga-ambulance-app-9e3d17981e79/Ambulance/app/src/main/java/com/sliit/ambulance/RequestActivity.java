package com.sliit.ambulance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestActivity extends AppCompatActivity {

    private DatabaseReference userRef;
    private TextView textViewUname;
    private Button pickUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        getSupportActionBar().setTitle("Requst Details");

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        String location1 = getIntent().getExtras().get("location1").toString();
        String location2 = getIntent().getExtras().get("location2").toString();
        webView.loadUrl("http://zacseed.com/map/directions.php?origin="+ location1 +"&destination=" + location2);

        TextView textViewUId = findViewById(R.id.userId);
        textViewUId.setText(getIntent().getExtras().get("UserId").toString());

        userRef=FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getExtras().get("UserId").toString());
        textViewUname = findViewById(R.id.name);
        pickUpBtn=findViewById(R.id.btnPic);
        retreivedata();

        pickUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestActivity.this, UploadPic.class);
                intent.putExtra("UId", getIntent().getExtras().get("UserId").toString());
                startActivity(intent);
            }
        });


    }

    private void retreivedata()
    {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    String Name=dataSnapshot.child("Fullname").getValue().toString();

                    textViewUname.setText(Name);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
