package com.example.unisphere.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.unisphere.R;
import com.example.unisphere.model.Post;
import com.squareup.picasso.Picasso;

public class PostDetailsFragment extends Fragment {

    private static final String ARG_POST = "post";

    private Post post;

    public static PostDetailsFragment newInstance(Post post) {
        PostDetailsFragment fragment = new PostDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POST, post);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable(ARG_POST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        ImageView imageViewPost = view.findViewById(R.id.imageView_post);
        TextView textViewDescription = view.findViewById(R.id.textView_post_description);
        TextView textViewLikeCount = view.findViewById(R.id.like_count);
        TextView textViewCommentCount = view.findViewById(R.id.comment_count);

        if (post != null) {
            Picasso.get().load(post.getImageUrl()).into(imageViewPost);
            textViewDescription.setText(post.getDescription());
            textViewLikeCount.setText(String.valueOf(post.getLikedByUserIds().size()));
            textViewCommentCount.setText(String.valueOf(post.getComments().size()));
        }

        return view;
    }

}