package unicauca.movil.eclimayahoo.net;

import android.content.Context;
import android.widget.Toast;

import unicauca.movil.eclimayahoo.R;

/**
 * Created by Dario Chamorro on 22/05/2016.
 */
public class HttpApi {

    protected Context context;

    public HttpApi(Context context) {
        this.context = context;
    }

    protected boolean validateError(Response response){
        int error = response.getError();
        if(error == HttpError.NO_ERROR){
            int code = response.getCode();
            if(code == 200){
                return true;
            }else if(code == 404){
                Toast.makeText(context, R.string.http_error_404, Toast.LENGTH_SHORT).show();
                return  false;
            }else
                Toast.makeText(context, R.string.http_error_server, Toast.LENGTH_SHORT).show();
            return false;
        }else if (error == HttpError.NO_INTERNET){
            Toast.makeText(context, R.string.http_error_internet, Toast.LENGTH_SHORT).show();
            return false;
        }else if(error == HttpError.TIMEOUT){
            Toast.makeText(context, R.string.http_error_timeout, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, R.string.http_error_server, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
