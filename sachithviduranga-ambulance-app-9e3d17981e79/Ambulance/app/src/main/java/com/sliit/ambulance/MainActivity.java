package com.sliit.ambulance;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sliit.ambulance.viewHolder.userViewHolder;


public class MainActivity extends AppCompatActivity {

   // String[] requestLocations = {"Kadawatha", "Kiribathgoda", "Malabe"};
   // String[] requestHospitals = {"Colombo", "Ragama", "Colombo"};
    private DatabaseReference mDatabase;
    private RecyclerView friends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Ambulance Requests");

        friends = findViewById(R.id.friends);
        friends.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        friends.setLayoutManager(linearLayoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Location");
        DisplayMyallLocations();
        /*CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return requestLocations.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_list, null);
            TextView textViewLocation = convertView.findViewById(R.id.textViewLocation);
            TextView textViewHospital = convertView.findViewById(R.id.textViewHospital);

            textViewLocation.setText(requestLocations[position]);
            textViewHospital.setText(requestHospitals[position]);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RequestActivity.class);
                    intent.putExtra("user", "uid");
                    startActivity(intent);
                }
            });

            return convertView;
        }*/
    }

    private void DisplayMyallLocations() {
        FirebaseRecyclerOptions<Location> options = new FirebaseRecyclerOptions.Builder<Location>()
                .setQuery(mDatabase, Location.class)
                .build();

        FirebaseRecyclerAdapter<Location, userViewHolder> adapter = new FirebaseRecyclerAdapter<Location, userViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final userViewHolder holder, final int position, @NonNull final Location model) {
                final String visit_userId=getRef(position).getKey();

                mDatabase.child(visit_userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String toastmsg = "";
                        //for(DataSnapshot ds: dataSnapshot.getChildren())
                        //{
                         //   String location1=ds.child("Location1").getValue(String.class);
                          //  String location2=ds.child("Location2").getValue(String.class);
                           // toastmsg += location1;

                            //holder.location1.setText(location1);
                            //holder.location2.setText(location2);
                       // }
                        //makeToast(toastmsg);
                        if (dataSnapshot.exists()) {
                          //  final String Username = dataSnapshot.child("Location1").getValue().toString();
                          //  String Status = dataSnapshot.child("Location2").getValue().toString();
                            final String location1=dataSnapshot.child("Location1").getValue().toString();
                             final String location2=dataSnapshot.child("Location2").getValue().toString();
                             toastmsg += location1;

                            holder.location1.setText(location1);
                            holder.location2.setText(location2);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(MainActivity.this, RequestActivity.class);
                                    intent.putExtra("UserId", visit_userId);
                                    intent.putExtra("location1", location1);
                                    intent.putExtra("location2", location2);
                                    startActivity(intent);
                                }
                            });



                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //holder.usrname.setText(model.getUsername());
                //holder.Status.setText(model.getStatus());
                //Picasso.get().load(model.getProfile()).into(holder.ppimg);


            }

            @NonNull
            @Override
            public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);

                userViewHolder holder = new userViewHolder(view);

                return holder;
            }
        };

        friends.setAdapter(adapter);
        adapter.startListening();
    }

    private void makeToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
