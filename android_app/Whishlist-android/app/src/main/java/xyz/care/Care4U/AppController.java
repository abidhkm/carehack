package xyz.care.Care4U;



import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    static double[][][] location= new double[15][15][2];
    static String[][] locationName= new String[15][15];

    private static Context context;
    public static String[] sCategories = new String[]{"1", "2", "3"};




    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppController.context = getApplicationContext();
        initLocation();


    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }



    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void initLocation(){
        location[0][0][0]=8.5472; //latitude of 1st shop in 1st category(vehicle)
        location[0][0][1]=76.8793; //longitude of 1st shop in 1st category(vehicle)
        location[0][1][0]=8.5472;
        location[0][1][1]=76.8793;
        location[0][2][0]=8.5472;
        location[0][2][1]=76.8793;
        location[1][0][0]=8.5472;
        location[1][0][1]=76.8793;
        location[1][1][0]=8.5237;
        location[1][1][1]=76.9277;
        location[1][2][0]=8.5237;
        location[1][2][1]=76.9277;
        location[2][0][0]=8.5472;
        location[2][0][1]=76.8793;
        location[2][1][0]=8.5472;
        location[2][1][1]=76.8793;
        location[2][2][0]=8.5472;
        location[2][2][1]=76.8793;
        location[3][0][0]=8.5472;
        location[3][0][1]=76.8793;
        location[3][1][0]=8.5472;
        location[3][1][1]=76.8793;
        location[3][2][0]=8.5472;
        location[3][2][1]=76.8793;



        locationName[0][0]="Rajasree medicals";
        locationName[0][1]="City Medicals";
        locationName[0][2]="Nagarjuna Medicals";
        locationName[1][0]="Neethy Shop";
        locationName[1][1]="SH cmi";
        locationName[1][2]="Chinmaya Pharmacy";
        locationName[2][0]="ASJ Medicals";
        locationName[2][1]="Geekay Drug House";
        locationName[2][2]="Shankar Pharmacy";
        locationName[3][0]="Gayathri Pharmacy";
        locationName[3][1]="Mampilly Drug Store";
        locationName[3][2]="Arya Pharmacy";


    }




    public static Context getAppContext() {
        return AppController.context;
    }
}
