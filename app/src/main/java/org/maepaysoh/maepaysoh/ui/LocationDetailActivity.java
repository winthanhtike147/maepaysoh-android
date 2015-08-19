package org.maepaysoh.maepaysoh.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonGeometry;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysohsdk.GeoAPIHelper;
import org.maepaysoh.maepaysohsdk.models.Geo;

/**
 * Created by Ye Lin Aung on 15/08/10.
 */
public class LocationDetailActivity extends BaseActivity {
  private GeoAPIHelper mGeoAPIHelper;
  private GoogleMap mMap;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_detail);
    String pCode = getIntent().getStringExtra("GEO_OBJECT_ID");
    System.out.println(pCode);
    mGeoAPIHelper = getMaePaySohWrapper().getGeoApiHelper();
    new GetGeoByID().execute(pCode);
  }

  @Override protected void onResume() {
    super.onResume();
  }
  public void setUpMapIfNeeded(AppCompatActivity activity,int fragmentId,Geo geo) {
    // Do a null check to confirm that we have not already instantiated the map.
    if (mMap == null) {
      // Try to obtain the map from the SupportMapFragment.
      mMap = ((SupportMapFragment) activity.getSupportFragmentManager().findFragmentById(fragmentId)).getMap();
      if (mMap != null) {
        setUpMap(activity,geo);
      }
    }
  }

  private void setUpMap(AppCompatActivity activity,Geo geo) {
    for(List cordinates:geo.getGeometry().getCoordinates()){
      System.out.println(cordinates);
    }
    Gson gson = new GsonBuilder().create();
    String object = gson.toJson(geo);
    try {
      GeoJsonLayer layer = new GeoJsonLayer(mMap,new JSONObject(object));
      GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
      GeoJsonFeature geoJsonFeature = null;
      for (GeoJsonFeature feature : layer.getFeatures()) {
        GeoJsonGeometry geoJsonGeometry = feature.getGeometry();
        geoJsonFeature = new GeoJsonFeature(geoJsonGeometry, null, null, null);
        pointStyle = feature.getPointStyle();
      }
      GeoJsonPolygonStyle geoJsonPolygonStyle = layer.getDefaultPolygonStyle();
      geoJsonPolygonStyle.setFillColor(
          activity.getResources().getColor(R.color.geojson_background_color));
      geoJsonPolygonStyle.setStrokeColor(activity.getResources().getColor(R.color.geojson_stroke_color));
      geoJsonPolygonStyle.setStrokeWidth(2);

      pointStyle.setTitle(geo.getProperties().getDT());
      pointStyle.setSnippet(geo.getProperties().getDT());

      if (geoJsonFeature != null) {
        geoJsonFeature.setPointStyle(pointStyle);
        geoJsonFeature.setPolygonStyle(geoJsonPolygonStyle);
        layer.addFeature(geoJsonFeature);
      }



      layer.addLayerToMap();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    LatLng ygnLatLng = new LatLng(((geo.getGeometry().getCoordinates().get(0)).get(0)).get(1),((geo.getGeometry().getCoordinates().get(0)).get(0)).get(0));
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ygnLatLng, 12));
  }
  class GetGeoByID extends AsyncTask<String,Void,List<Geo>>{

    @Override protected List<Geo> doInBackground(String... strings) {
      return mGeoAPIHelper.getLocationByObjectId(strings[0]);
    }

    @Override protected void onPostExecute(List<Geo> geos) {
      super.onPostExecute(geos);
      setUpMapIfNeeded(LocationDetailActivity.this,R.id.location_detail_map,geos.get(0));
    }
  }
}
