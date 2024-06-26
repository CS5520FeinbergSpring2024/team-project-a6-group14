package com.example.unisphere.signup_fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.unisphere.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//TODO: Fix the backstack.

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignupUserFragment extends Fragment {

    private NavController navController;

    private Spinner universitySelector;
    private EditText userName;
    private EditText userEmail;
    private String emailDomain;
    private EditText userPassword;
    private EditText userConfirmPassword;
    private Spinner userRoleSelector;
    private TextView domainNameTextView;
    private SharedPreferences preferences;
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//private File

    public SignupUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emailDomain = "";
// If you reach this page, your app should have no prior user data.
        getContext().deleteSharedPreferences("USER_DATA");

        preferences = getActivity().getSharedPreferences("USER_DATA", MODE_PRIVATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_signup_user, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);

        universitySelector = (Spinner) view.findViewById(R.id.universitySpinner);
        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.universities, android.R.layout.simple_spinner_dropdown_item);
        universitySelector.setAdapter(universityAdapter);
        universitySelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                domainNameTextView.setText(getResources().getStringArray(R.array.domains)[position]);
                emailDomain = getResources().getStringArray(R.array.domains)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userRoleSelector = (Spinner) view.findViewById(R.id.userRoleSelector);
        ArrayAdapter<CharSequence> userRoleAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.user_roles, android.R.layout.simple_spinner_dropdown_item);
        userRoleSelector.setAdapter(userRoleAdapter);
        userName = (EditText) view.findViewById(R.id.name);
        userEmail = (EditText) view.findViewById(R.id.newUserEmail);
        userPassword = (EditText) view.findViewById(R.id.newUserPassword);
        userConfirmPassword = (EditText) view.findViewById(R.id.newUserConfirmPassword);
        domainNameTextView = view.findViewById(R.id.domainNameTextView);

        FloatingActionButton nextButton = view.findViewById(R.id.signup_user_next_btn);
        nextButton.setOnClickListener(this::signupUser);


    }

    /**
     * Validate inputs entered in the text fields. This includes valid email, name, passwords and confirm password.
     *
     * @return true if all inputs are valid
     * @throws Exception if inputs are invalid
     */
    public boolean validateInputs() throws Exception {

        String email = userEmail.getText().toString() + emailDomain;
        if (!(email.matches(emailPattern) && email.length() > 0))
            throw new Exception("Invalid Email!");
        if (userPassword.getText().toString().length() < 6)
            throw new Exception("Passwords must be at least 6 characters!");
        if (!userConfirmPassword.getText().toString().equals(userPassword.getText().toString()))
            throw new Exception("Passwords do not match!");
        if (userName.getText().toString().length() < 3)
            throw new Exception("Please enter a valid name");
        return true;
    }

    /**
     *
     *
     * @param view
     */
    public void signupUser(View view) {

        //Check if inputs are valid
        try {
            if (!validateInputs()) {
                Toast.makeText(this.getContext(), "Invalid Inputs!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }


        addUserInformationToSharedPreferences();

        navController.navigate(R.id.action_fragment_signup_user_to_fragment_signup_student);
        //navController.popBackStack();

    }

    /**
     * Add all the entered fields into shared preferences.
     */
    private void addUserInformationToSharedPreferences() {
        preferences.edit().putString("username", userName.getText().toString()).putString("university", universitySelector.getSelectedItem().toString()).
                putString("email", userEmail.getText().toString() + emailDomain)
                .putString("userRole", userRoleSelector.getSelectedItem().toString()).putString("password", userPassword.getText().toString()).apply();

    }

}