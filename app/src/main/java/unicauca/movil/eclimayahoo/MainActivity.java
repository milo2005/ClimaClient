package unicauca.movil.eclimayahoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import unicauca.movil.eclimayahoo.databinding.ActivityMainBinding;
import unicauca.movil.eclimayahoo.models.Clima;
import unicauca.movil.eclimayahoo.net.api.ClimaApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClimaApi.OnClima {

    ClimaApi api;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setOnClick(this);

        api = new ClimaApi(this);
        api.getClima(this);
    }

    @Override
    public void onClick(View v) {
        api.getClima(this);
    }

    @Override
    public void onClima(Clima clima) {
        if(clima != null) {
            binding.setClima(clima);
        }
    }
}
