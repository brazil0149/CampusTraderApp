package com.example.olabiyi.tradeu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olabiyi.tradeu.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Olabiyi Oyewumi, Diego Ortega, Nabila Zaidi
 * @version 1.0
 */
/**
 * Main Activity for the Trade Application
 */

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    private GoogleApiClient mGoogleApiClient;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter.OnItemClickListener onItemClickListener;
    Button btnAddItem;
    ArrayList<RecyclerData> myList = new ArrayList<>();
    public static final int BUY = 0;
    public static final int SELL = 1;
    public static final int TRADE = 2;
    private ArrayList<Integer> mDatasetTypes = new ArrayList<Integer>();
 //   private int mDatasetTypes[] = {BUY, SELL, TRADE};
    EditText etTitle, etDescription;
    String title = "",description = "";
    String Location = "", option = "";
    ImageView crossImage;
    private ActionBarDrawerToggle mDrawerToggle;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private TextView mStatusTextView;
    ListView testView;
    ImageButton New;
    TextView Map;
    TextView results;
    TextView Loc;
    Context context = this;
    AlertDialog.Builder alert0;
    String tI1;
    String tI2;
    private Spinner spinner1, spinner2;
    LayoutInflater inflater;
    View MapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *Setup the DrawerLayout and NavigationView
         */

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]
        mDatasetTypes.add(BUY);
        mDatasetTypes.add(SELL);
        mDatasetTypes.add(TRADE);

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /**
         * Dialog for creating a new entry
         */

        ImageView New = (ImageView) findViewById(R.id.New);
        New.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OpenCategroyDialogBox();
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                load(menuItem.getItemId());
                return true;
            }
        });

        /**
         * creates and gets the recycerview from the recycerAdapter class
         */

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerAdapter = new RecyclerAdapter(MainActivity.this.myList,mDatasetTypes);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        mStatusTextView = (TextView) findViewById(R.id.status);

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    /**
     * Link to open up the Map from the Map Class
     */

    public void OpenMap() {
        /**
        final AlertDialog.Builder alert0 = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        MapView = inflater.inflate(R.layout.activity_map, null);
        alert0.setTitle("Map");
        alert0.setView(MapView);

        alert0.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        Toast.makeText(getApplicationContext(),
                                "Ok Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
        // create an alert dialog
        AlertDialog alert2 = alert0.create();
        alert2.show();
         */
        Intent map = new Intent(context, Map.class);
        startActivity(map);
    }

    /**
     * To open the Dialog for new entries
     */

    private void OpenCategroyDialogBox() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.addnewcategory, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Entry");
        alert.setView(promptView);

        etTitle = (EditText) promptView.findViewById(R.id.etTitle);
        etTitle.requestFocus();
        etTitle.setHint("Name");
        etTitle.setTextColor(Color.BLACK);
        title = etTitle.getText().toString();

        etDescription = (EditText) promptView.findViewById(R.id.etDescription);
        etDescription.requestFocus();
        etDescription.setHint("Description");
        etDescription.setTextColor(Color.BLACK);
        description = etDescription.getText().toString();

        if (title.length() == 0) {
            title = "Example";
        }
        if (description.length() == 0) {
            description = "Test";
        }

        /**
         * creates a spinner of choices of building to select in the new entry dialog
         */

        spinner1 = (Spinner) promptView.findViewById(R.id.spinner1);
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

        spinner1 = (Spinner) promptView.findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        /**
         * creates a spinner of choices of 2  to select in the new entry dialog, whether you want to sell or trade an item
         */

        spinner2 = (Spinner) promptView.findViewById(R.id.spinner2);
        List<String> list1 = new ArrayList<String>();
        list1.add("SELECT ENTRY OPTIONS");
        list1.add("SELL");
        list1.add("TRADE");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter1);

        spinner2 = (Spinner) promptView.findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());



   //     AdapterView.OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(adapter, this.mAdapter);

   //     spinner.setOnItemSelectedListener(spinnerListener);



        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                title = etTitle.getText().toString();
                description = etDescription.getText().toString();
       //         spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                Location = spinner1.getSelectedItem().toString();
                option = spinner2.getSelectedItem().toString();

                // Do something with value!

                if (title.equals("") && description.equals("")) {
                    etTitle.setError("Name Required");
                    OpenCategroyDialogBox();
                } else {
                    Intent intent = getIntent();
        //            String message = intent.getExtras().getString(profileActivity.EXTRA_MESSAGE);
        //            String message1 = intent.getExtras().getString(profileActivity.EXTRA_MESSAGE);
                    String message = intent.getStringExtra("email");
                    String message1 = intent.getStringExtra("name");
                    RecyclerData mLog = new RecyclerData();
                    mLog.setTitle(title);
                    mLog.setDescription(description);
                    mLog.setLocation(Location);
                    mLog.setoption(option);
                    mLog.setemail(message);
                    mLog.setname(message1);
                    myList.add(mLog);
                    mRecyclerAdapter.notifyData(myList);
                //    Toast.makeText(getApplicationContext(), "Worked. Kappa", Toast.LENGTH_SHORT).show();


                }
            }

        });


    alert.setNegativeButton("CANCEL",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                    Toast.makeText(getApplicationContext(),
                            "New Entry Canceled", Toast.LENGTH_SHORT).show();

                }
            });

    // create an alert dialog
    AlertDialog alert1 = alert.create();

    alert1.show();

}


    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

     //   spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   //     spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.drawer, menu);
            return true;
        }

    /**
     * void function for the items in the navigator view
     * @param item select an item in the navigator view
     */

        public void load(int item) {
            switch (item) {
                case R.id.Log_out:
                    signOut();
                    break;
                case R.id.user:
                    Intent profile = new Intent(context, profileActivity.class);
                    startActivity(profile);
                    //   return true;
                    break;
                case R.id.Map:
                    Intent map = new Intent(context, Map.class);
                    startActivity(map);
                    //   return true;
                    break;
                case R.id.Email:
                    Intent email = new Intent(context, Email.class);
                    startActivity(email);
                    //   return true;
                    break;
            }

        }


        /**
         * // [START signIn]
         * private void signIn() {
         * signOut();
         * Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
         * startActivityForResult(signInIntent, RC_SIGN_IN);
         * }
         * // [END signIn]
         */

    /**
     * ablity to sign out with google in the app
     */
        public void signOut() {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            //     updateUI(false);
                            Toast.makeText(getApplicationContext(),
                                    "You Have Logged Out", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(context, LoginActivity.class);
                            startActivity(i1);
                            // [END_EXCLUDE]
                        }
                    });
        }
        // [END signOut]

        private void updateUI(boolean signedIn) {
            if (signedIn) {
                findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);

            } else {
//            mStatusTextView.setText(R.string.signed_out);

                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            }
        }

        // [START revokeAccess]
        private void revokeAccess() {
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            updateUI(false);
                            // [END_EXCLUDE]
                        }
                    });
        }
        // [END revokeAccess]


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    //   signIn();
                    break;
                case R.id.sign_out_button:
                    signOut();
                    break;
                case R.id.disconnect_button:
                    revokeAccess();
                    break;
            }
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }


    }