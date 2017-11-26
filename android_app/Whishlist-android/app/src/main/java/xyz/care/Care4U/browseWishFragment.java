package xyz.care.Care4U;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class browseWishFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    String url = "https://atscreations.000webhostapp.com/browse_wish.php";
    ProgressDialog pDialog;

    String tag_json_obj = "json_obj_req";
    List<pojoclass2> list = new ArrayList<pojoclass2>();
    String id="";
    RecyclerView recyclerview;
    private WishViewAdapter mAdapter;
    View view;

    public browseWishFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_browsewish_list, container, false);

        // Set the adapter
        SharedPreferences prefs = getActivity().getSharedPreferences("whishlist", MODE_PRIVATE);
        id= prefs.getString("id", "100");
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
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success")==2) {

                        list.clear();

                        //help.setText(jobj.getString("helps")+" Helps");
                        JSONArray jsonarray=obj.getJSONArray("wishes");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject c = jsonarray.getJSONObject(i);
                            pojoclass2 pc=new pojoclass2();
                            pc.setTitle(c.getString("title"));
                            pc.setDate(c.getString("time"));
                            pc.setWishId(c.getString("wish_id"));
                            pc.setUserId(c.getString("user_id"));
                            pc.setLikes(c.getInt("likes"));
                            pc.setUserName(c.getString("username"));
                            pc.setHelp(c.getInt("helps"));
                            pc.setWishDesc(c.getString("desc"));
                            pc.setWishTag(c.getInt("tags"));

                            list.add(pc);
                            recyclerview = (RecyclerView) view.findViewById(R.id.list1);

                            mAdapter = new WishViewAdapter(list, new WishViewAdapter.OnListFragmentInteractionListener() {
                                @Override
                                public void onListFragmentInteraction(int item) {
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                    //add a fragment
                                    likefragment myFragment = new likefragment();

                                    Bundle args = new Bundle();
                                    args.putString("item", list.get(item).getWishId());
                                    myFragment.setArguments(args);
                                    fragmentTransaction.replace(R.id.main2, myFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            }, false,getActivity()) ;
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerview.setLayoutManager(mLayoutManager);
                            recyclerview.setItemAnimator(new DefaultItemAnimator());
                            recyclerview.setAdapter(mAdapter);
                        }
                        Log.e("inside","log");
                    }
                    else{

                    }

                }
                catch(Exception e){
                    Log.e("ex",e.toString());
                    Toast.makeText(getActivity(),"Exception"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getActivity(),"Error"+ error.getMessage(),Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pDialog.hide();
            }
        }){@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();

            params.put("id", id);



            return params;
        }};

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);



        return view;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
