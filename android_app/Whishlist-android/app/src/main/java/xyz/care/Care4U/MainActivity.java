package xyz.care.Care4U;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText name,email,pass,confirmpass,mobile;
    Button Signup;
    String url = "https://atscreations.000webhostapp.com/signup.php";
    ProgressDialog pDialog;
    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.password);
        confirmpass=(EditText) findViewById(R.id.cpassword);
        mobile=(EditText) findViewById(R.id.mobile);
        Signup=(Button) findViewById(R.id.email_sign_in_button);
        sharedpreferences = getSharedPreferences("whishlist", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("") || confirmpass.getText().toString().equals("") || mobile.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("values", name.getText().toString() + email.getText().toString() + pass.getText().toString() + confirmpass.getText().toString() + mobile.getText().toString());
                    pDialog = new ProgressDialog(MainActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            url, new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {
                            Log.d("sucess", response.toString());
                            pDialog.hide();
                               try {
                                   JSONObject obj = new JSONObject(response);
                                    if (obj.getString("success").equals("2")) {
                                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        editor.putString("email",email.getText().toString() );
                                        editor.putString("id", obj.getString("user_id"));
                                        editor.putBoolean("loggedIn",true);

                                        editor.commit();
                                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                        Toast.makeText(MainActivity.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                                catch(Exception e){

                                }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Error", "Error: " + error.getMessage());
                            // hide the progress dialog
                            pDialog.hide();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name.getText().toString());
                            params.put("email", email.getText().toString());
                            params.put("pass", pass.getText().toString());
                            params.put("c_pass", confirmpass.getText().toString());
                            params.put("mobile", mobile.getText().toString());
                            //Log.e("values",name.getText().toString()+email.getText().toString()+pass.getText().toString()+confirmpass.getText().toString()+mobile.getText().toString());
                            return params;
                        }
                    };

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

                }
            }
        });
    }
}
