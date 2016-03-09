package com.example.olabiyi.tradeu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Ability to sign into google and display user profile
 */

public class profileActivity extends AppCompatActivity implements OnConnectionFailedListener, View.OnClickListener, ConnectionCallbacks {
    public static final String EXTRA_MESSAGE ="" ;
    public static final String EXTRA_MESSAGE1 = "";
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    SignInButton signIn_btn;
    private static final int RC_SIGN_IN = 0;
    ProgressDialog progress_dialog;
    private Spinner spinner1, spinner2;
    String Location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buidNewGoogleApiClient();
        setContentView(R.layout.activity_profile);
        customizeSignBtn();
        setBtnClickListeners();
   //     Location();
        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Signing in....");
    }

    /*
    Configure sign-in to request the user's ID, email address, and basic profile.
    User's ID and basic profile are included in DEFAULT_SIGN_IN.
    create and  initialize GoogleApiClient object to use Google  Sign-In API and the options specified by gso..
    */

    private void buidNewGoogleApiClient(){

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /*
      Customize sign-in button. The sign-in button can be displayed in
      multiple sizes and color schemes. It can also be contextually
      rendered based on the requested scopes. For example. a red button may
      be displayed when Google+ scopes are requested, but a white button
      may be displayed when only basic profile is requested. Try adding the
      Plus.SCOPE_PLUS_LOGIN scope to see the  difference.
    */

    private void customizeSignBtn(){

        signIn_btn = (SignInButton) findViewById(R.id.sign_in_button);
        signIn_btn.setSize(SignInButton.SIZE_STANDARD);
        signIn_btn.setScopes(gso.getScopeArray());

    }

    /*
      Set on click Listeners on the sign-in sign-out and disconnect buttons
     */

    private void setBtnClickListeners(){
        // Button listeners
        signIn_btn.setOnClickListener(this);
//        findViewById(R.id.sign_out_button).setOnClickListener(this);
//        findViewById(R.id.disconnect_button).setOnClickListener(this);

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {

                progress_dialog.dismiss();

            }
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getSignInResult(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Toast.makeText(this, "start sign process", Toast.LENGTH_SHORT).show();
                gSignIn();
                break;
            case R.id.sign_out_button:
                Toast.makeText(this, "Google Sign Out", Toast.LENGTH_SHORT).show();
                gSignOut();
                break;
            case R.id.disconnect_button:
                Toast.makeText(this, "Google Access Revoked", Toast.LENGTH_SHORT).show();
                gRevokeAccess();
                break;
        }
    }

    private void gSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progress_dialog.show();
    }

    private void gSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        updateUI(false);

                    }
                });
    }

    private void gRevokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        updateUI(false);

                    }
                });
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }
    public String getLocation() {
        return Location;
    }

    public void Location() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("SELECT LOCATION");
        list.add("SC-N Building");
        list.add("SC-S Building");
        list.add("SF Building");
        list.add("LI Building");
        list.add("UU Building");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        profileActivity log = new profileActivity();
        Location = spinner1.getSelectedItem().toString();
        log.setLocation(Location);
        TextView Locate = (TextView)findViewById(R.id.Location);
        Locate.setText("Location: "+ log.getLocation());
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String item = parent.getItemAtPosition(pos).toString();
            //    Toast.makeText(parent.getContext(),"OnItemSelectedListener : " + item,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    /**
     * gets the displayname, email and id of the signed in user
     * @param result gets the account info
     */
    private void getSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            TextView user_name= (TextView)findViewById(R.id.userName);
            TextView email_id= (TextView)findViewById(R.id.emailId);
            TextView id= (TextView)findViewById(R.id.Id);
            user_name.setText(acct.getDisplayName());
            email_id.setText(acct.getEmail());
            id.setText("Id: " + acct.getId());
            String token = acct.getIdToken();
            /**
            Intent intent = new Intent(profileActivity.this, MainActivity.class);
        //    String message = email_id.getText().toString();
        //    String message1 = user_name.getText().toString();
            String name = acct.getDisplayName();
            intent.putExtra("email", email_id.getText().toString());
            intent.putExtra("name", name);
            startActivity(intent);
             */
            updateUI(true);
            progress_dialog.dismiss();
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}