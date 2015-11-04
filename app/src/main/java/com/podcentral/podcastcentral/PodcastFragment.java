package com.podcentral.podcastcentral;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.podcentral.podcastcentral.utils.ApiUtility;
import com.podcentral.podcastcentral.utils.ImageUtility;
import com.podcentral.podcastcentral.utils.interfaces.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dyego on 11/2/15.
 */
public class PodcastFragment extends Fragment {
    ImageView podcastImage;
    TextView podcastName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_podcast, container, false);
        podcastImage = (ImageView) rootView.findViewById(R.id.podcast_image);
        podcastName = (TextView) rootView.findViewById(R.id.podcast_name);
        new PodcastApiUtility().execute();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class PodcastApiUtility extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.podcast_loading_dialog));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            ApiUtility apiUtility = new ApiUtility();

            JSONObject json = apiUtility.getContentJSON(AppConstants.PODCAST_JSON_URL, AppConstants.PODCAST_ID, 3) ;
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
            try {
                ImageUtility imageUtility = new ImageUtility();

                String name = jsonObject.getString("name");
                String image = jsonObject.getString("image64");

                podcastImage.setImageBitmap(imageUtility.getDecodedBase64Image(image));
                podcastName.setText("Username: " + name);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
