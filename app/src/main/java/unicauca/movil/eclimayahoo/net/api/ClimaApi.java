package unicauca.movil.eclimayahoo.net.api;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import unicauca.movil.eclimayahoo.models.Clima;
import unicauca.movil.eclimayahoo.net.HttpApi;
import unicauca.movil.eclimayahoo.net.HttpAsyncTask;
import unicauca.movil.eclimayahoo.net.Response;

/**
 * Created by Dario Chamorro on 22/05/2016.
 */
public class ClimaApi extends HttpApi implements HttpAsyncTask.OnResponseListener {

    static final int REQUEST_CLIMA = 0;

    public interface OnClima{
        void onClima(Clima clima);
    }

    OnClima onClima;

    public ClimaApi(Context context) {
        super(context);
    }

    public void getClima(OnClima onClima){
        this.onClima = onClima;
        HttpAsyncTask task = new HttpAsyncTask(context, REQUEST_CLIMA,HttpAsyncTask.METHOD_GET,this);
        task.execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22popayan%2C%20co%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
    }

    private void processClima(Response response) {
        if(validateError(response)){
            Clima clima = new Clima();
            String json = response.getMsg();

            try {
                JSONObject obj =  new JSONObject(json);
                JSONObject query = obj.getJSONObject("query");
                JSONObject results = query.getJSONObject("results");
                JSONObject channel = results.getJSONObject("channel");
                JSONObject atmosphere = channel.getJSONObject("atmosphere");

                clima.setHumedad(atmosphere.getString("humidity"));
                clima.setPresion(atmosphere.getString("pressure"));

                JSONObject item = channel.getJSONObject("item");
                JSONObject condition = item.getJSONObject("condition");

                clima.setTemp(condition.getString("temp"));
                clima.setDescripcion(condition.getString("text"));
                onClima.onClima(clima);

            } catch (JSONException e) {
                e.printStackTrace();
                onClima.onClima(null);
            }

        }else{
            onClima.onClima(null);
        }
    }

    @Override
    public void onResponse(int request, Response response) {
        switch (request){
            case REQUEST_CLIMA: processClima(response); break;
        }
    }
}
