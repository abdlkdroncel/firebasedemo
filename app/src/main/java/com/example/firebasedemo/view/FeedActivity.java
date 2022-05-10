package com.example.firebasedemo.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firebasedemo.R;
import com.example.firebasedemo.adapter.PostAdapter;
import com.example.firebasedemo.databinding.ActivityFeedBinding;
import com.example.firebasedemo.model.Posts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Posts> postsArrayList;
    private ActivityFeedBinding binding;
    PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        getData();
        postsArrayList=new ArrayList<>();
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter=new PostAdapter(postsArrayList);
        binding.recView.setAdapter(postAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            Intent intent=new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);

        }else if(item.getItemId()==R.id.signout){

            auth.signOut();

            Intent intent=new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getData(){
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(FeedActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(queryDocumentSnapshots!=null){
                    for(DocumentSnapshot snapshot:queryDocumentSnapshots.getDocuments()){
                        Map<String,Object> data=snapshot.getData();
                        String email= (String) data.get("email");
                        String comment= (String) data.get("comment");
                        String url= (String) data.get("url");

                        Posts post=new Posts(email,comment,url);
                        postsArrayList.add(post);
                    }
                    postAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}