package xyz.care.Care4U;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;



public class likefragment extends Fragment {
    String id="";
    String url = "https://atscreations.000webhostapp.com/help_wish.php";
    String url2 = "https://atscreations.000webhostapp.com/browse_wish_byid.php";
    String url1 = "https://atscreations.000webhostapp.com/like_wish.php";
    ProgressDialog pDialog;
    String tag_json_obj = "json_obj_req";
    List<pojoclass2> list = new ArrayList<pojoclass2>();
    TextView title,wish,tag,date,like,help;
    View view;
    String value;
    FloatingActionButton fbLike,fbHelp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.likefragment, container, false);
        value= getArguments().getString("item");

        /*text=(TextView) view.findViewById(R.id.wish_title);
        desc=(TextView) view.findViewById(R.id.wish_details);
        tags=(TextView) view.findViewById(R.id.tags);
        date=(TextView) view.findViewById(R.id.dates);
        likes=(TextView) view.findViewById(R.id.likes);
        helps=(TextView) view.findViewById(R.id.helps);*/

        Log.e("final",value+"");
        title=(TextView) view.findViewById(R.id.wish_title);
        wish=(TextView) view.findViewById(R.id.wish_details);
        date=(TextView) view.findViewById(R.id.dates);
        tag=(TextView) view.findViewById(R.id.tags);
        like=(TextView) view.findViewById(R.id.likes);
        help=(TextView) view.findViewById(R.id.helps);
        SharedPreferences prefs = getActivity().getSharedPreferences("whishlist", MODE_PRIVATE);
        id= prefs.getString("id", "100");
        pDialog= new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("success", response.toString());


                pDialog.hide();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success")==2) {
                        // Toast.makeText(LoginActivity.this, "Sucess, user id:"+ obj.getString("user_id"), Toast.LENGTH_SHORT).show();
                        JSONObject jobj=obj.getJSONObject("details");

                        title.setText(jobj.getString("title"));
                        wish.setText(jobj.getString("desc"));
                        tag.setText(AppController.sCategories[jobj.getInt("tags")]);
                        date.setText(jobj.getString("time"));
                        like.setText(jobj.getString("likes")+" Likes");
                        help.setText(jobj.getString("helps")+" Helps");

                        if (jobj.has("helpers")){
                            JSONArray jsonarray=jobj.getJSONArray("helpers");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject c = jsonarray.getJSONObject(i);
                                pojoclass2 pc=new pojoclass2();
                                pc.setTitle(c.getString("name"));
                                pc.setDate(c.getString("mobile"));
                                list.add(pc);
                            }
                        }







                        Log.e("inside","log");
                    }
                    else{

                    }

                }
                catch(Exception e){
                    Log.e("ex",e.toString());
                    Toast.makeText(getActivity(),"error"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getActivity(),"No network",Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pDialog.hide();
            }
        }){@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();

            params.put("user_id", id);
            params.put("wish_id", value);


            return params;
        }};

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);


        fbLike=(FloatingActionButton) view.findViewById(R.id.fab2) ;
        fbHelp=(FloatingActionButton) view.findViewById(R.id.fab1);
        fbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog= new ProgressDialog(getActivity());
                pDialog.setMessage("Loading...");
                pDialog.show();
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        url1, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("sucess", response.toString());


                        pDialog.hide();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getActivity(), ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                        catch(Exception e){
                            Log.e("ex",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error", "Error: " + error.getMessage());
                        // hide the progress dialog
                        pDialog.hide();
                    }
                }){@Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", id);
                    params.put("wish_id", value+"");



                    return params;
                }};

// Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

            }
        });
        fbHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                final EditText et = new EditText(getActivity());
                et.setHint("Enter your help message");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("whishlist", MODE_PRIVATE);
                final String userId= sharedPreferences.getString("id", "100");
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(et);

                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (et.getText().toString().length()>2){

                            pDialog= new ProgressDialog(getActivity());
                            pDialog.setMessage("Loading...");
                            pDialog.show();
                            StringRequest strReq = new StringRequest(Request.Method.POST,
                                    url, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    Log.d("sucess", response.toString());

                                    pDialog.hide();
                                    try {

                                        Toast.makeText(getActivity(), "Great your frined will be notified", Toast.LENGTH_SHORT).show();
                                    }
                                    catch(Exception e){
                                        Log.e("ex",e.toString());
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d("Error", "Error: " + error.getMessage());
                                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    // hide the progress dialog
                                    pDialog.hide();
                                }
                            }){@Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("user_id", userId);
                                params.put("wish_id", value+"");
                                params.put("message", et.getText().toString());



                                return params;
                            }};

// Adding request to request queue
                            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

                        }else{
                            Toast.makeText(getActivity(),"Sorry,message cannot be empty",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();


            }
        });


        return view;
    }
}
