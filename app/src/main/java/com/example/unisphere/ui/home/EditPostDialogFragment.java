package com.example.unisphere.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;

import com.example.unisphere.R;
import com.example.unisphere.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPostDialogFragment extends DialogFragment {

    private EditText editText;
    private Button buttonDelete;
    private Button buttonSaveChanges;

    private final Post post;

    private final NavController navController;

    private final String university;

    private DatabaseReference postRef;
    private EditPostListener editPostListener;

    public EditPostDialogFragment(Post post, NavController navController, String university) {
        this.post = post;
        this.navController = navController;
        this.university = university;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        postRef = FirebaseDatabase.getInstance().getReference().child(university)
                .child("posts").child(post.getKeyFirebase());
        editText = view.findViewById(R.id.editText_post);
        buttonDelete = view.findViewById(R.id.button_delete);
        buttonSaveChanges = view.findViewById(R.id.button_save_changes);

        editText.setText(post.getDescription());

        buttonDelete.setOnClickListener(v -> {
            deletePostFromFirebase(post);
            navController.navigate(R.id.navigation_home);
            dismiss();
        });

        buttonSaveChanges.setOnClickListener(v -> {
            editPostOnFirebase(post);
            if (editPostListener != null) {
                post.setDescription(editText.getText().toString());
                editPostListener.onPostEdited(post);
            }
            dismiss();
        });

        return view;
    }

    // Update the post's fields
    public void editPostOnFirebase(Post post) {

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    snapshot.getRef().child("description").setValue(editText.getText().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EditPost", "Failed to edit post", error.toException());
            }
        });
    }

    public void deletePostFromFirebase(Post post) {

        postRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    // Post deleted successfully
                    Log.d("EditPost", "Post deleted successfully");
                    dismiss();
                })
                .addOnFailureListener(e -> {
                    // Failed to delete post
                    Log.e("EditPost", "Failed to delete post", e);
                });
    }

    public void setEditPostListener(EditPostListener editPostListener) {
        this.editPostListener = editPostListener;
    }


    public interface EditPostListener {
        void onPostEdited(Post post);
    }


}
