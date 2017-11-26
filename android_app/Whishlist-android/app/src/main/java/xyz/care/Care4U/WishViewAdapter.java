package xyz.care.Care4U;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;



public class WishViewAdapter extends RecyclerView.Adapter<WishViewAdapter.ViewHolder> {

    private List<pojoclass2> wishList;
    private OnListFragmentInteractionListener listener;
    private boolean isMyWish;
    String url = "https://atscreations.000webhostapp.com/help_wish.php";
    String url1 = "https://atscreations.000webhostapp.com/like_wish.php";
    ProgressDialog pDialog;
    String tag_json_obj = "json_obj_req";
    Context context;
    String id="";
    String value="";

    public interface OnListFragmentInteractionListener{
        void onListFragmentInteraction(int pos);
    }

    public WishViewAdapter(List<pojoclass2> items, OnListFragmentInteractionListener fragmentInteractionListener, boolean isMyWish,Context context){
        this.wishList = items;
        this.listener = fragmentInteractionListener;
        this.isMyWish = isMyWish;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_card_layout, parent, false);
        return new WishViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.wishData = wishList.get(position);
        holder.wishTitle.setText(holder.wishData.getTitle());
        holder.wishDate.setText(holder.wishData.getDate());
        holder.wishTags.setText("Dosage: " + AppController.sCategories[holder.wishData.getWishTag()]);
        holder.likeBtn.setText("Like("+ holder.wishData.getLikes() +")");
        holder.helpBtn.setText("Help("+ holder.wishData.getHelp() +")");

        SharedPreferences prefs = context.getSharedPreferences("whishlist", MODE_PRIVATE);
        id= prefs.getString("id", "100");

        holder.wishContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null)
                    listener.onListFragmentInteraction(position);
            }
        });

        if(!isMyWish){
            holder.wishUser.setText(holder.wishData.getUserName());
            holder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // trigger like & update the view text
                    pDialog= new ProgressDialog(context);
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            url1, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d("success", response.toString());


                            pDialog.hide();
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
                        params.put("wish_id", wishList.get(position).getWishId());



                        return params;
                    }};

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

                }
            });

            holder.helpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // trigger help & update the view text
                    pDialog= new ProgressDialog(context);
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d("sucess", response.toString());


                            pDialog.hide();
                            try {

                                Toast.makeText(context, "We will reach you soon..", Toast.LENGTH_SHORT).show();
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
                        params.put("wish_id", wishList.get(position).getWishId());



                        return params;
                    }};

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
                }
            });
        }else {
            holder.wishUser.setVisibility(GONE);
            holder.likeBtn.setClickable(false);
            holder.helpBtn.setClickable(false);

        }
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView wishImg;
        TextView wishTitle, wishUser, wishDate, wishTags, likeBtn, helpBtn;
        pojoclass2 wishData;
        RelativeLayout wishContent;


        public ViewHolder(View itemView) {
            super(itemView);
            wishDate = (TextView) itemView.findViewById(R.id.wish_date);
            wishTags = (TextView) itemView.findViewById(R.id.wish_tag);
            wishTitle = (TextView) itemView.findViewById(R.id.wish_title);
            wishUser = (TextView) itemView.findViewById(R.id.wish_user);
            likeBtn = (TextView) itemView.findViewById(R.id.like_btn);
            helpBtn = (TextView) itemView.findViewById(R.id.help_btn);
            wishImg = (ImageView) itemView.findViewById(R.id.wish_image);
            wishContent = (RelativeLayout) itemView.findViewById(R.id.wish_content);
            if (!isMyWish){
                helpBtn.setVisibility(GONE);
            }
        }
    }
}
