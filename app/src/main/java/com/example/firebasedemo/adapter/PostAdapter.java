package com.example.firebasedemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.databinding.RecyRowBinding;
import com.example.firebasedemo.model.Posts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    ArrayList<Posts> postsArrayList;

    public PostAdapter(ArrayList<Posts> postsArrayList) {
        this.postsArrayList = postsArrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyRowBinding binding=RecyRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(binding);
    }

    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.binding.recEmail.setText(postsArrayList.get(position).email);
        holder.binding.recCom.setText(postsArrayList.get(position).comment);
        Picasso.get().load(postsArrayList.get(position).url).into(holder.binding.recImage);
    }

    class PostHolder extends RecyclerView.ViewHolder{

        RecyRowBinding binding;
        public PostHolder(@NonNull RecyRowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
