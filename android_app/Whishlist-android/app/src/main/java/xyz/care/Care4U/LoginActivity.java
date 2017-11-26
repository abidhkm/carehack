package xyz.care.Care4U;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    TextView etLinkSignup;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String url = "https://atscreations.000webhostapp.com/signin.php";
    ProgressDialog pDialog;
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        etLinkSignup = (TextView) findViewById(R.id.link_signup);
        sharedpreferences = getSharedPreferences("whishlist", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if (sharedpreferences.getBoolean("loggedIn",false)){
            Intent intent =new Intent(LoginActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }else{
            mPasswordView = (EditText) findViewById(R.id.password);


            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {




                    //attemptLogin();
                    pDialog= new ProgressDialog(LoginActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d("Omnalista", response.toString());


                            pDialog.hide();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getInt("success")==2) {
                                    // Toast.makeText(LoginActivity.this, "Sucess, user id:"+ obj.getString("user_id"), Toast.LENGTH_SHORT).show();
                                    editor.putString("email",mEmailView.getText().toString() );
                                    editor.putString("id", obj.getString("user_id"));
                                    editor.putBoolean("loggedIn",true);
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(LoginActivity.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            catch(Exception e){
                                Log.d("Omnalista",e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Omnalista", "Error: " + error.getMessage());
                            // hide the progress dialog
                            pDialog.hide();
                        }
                    }){@Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", mEmailView.getText().toString());
                        params.put("pass", mPasswordView.getText().toString());

                        return params;
                    }};

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

                }
            });

            etLinkSignup.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            });
        }

    }




















}

