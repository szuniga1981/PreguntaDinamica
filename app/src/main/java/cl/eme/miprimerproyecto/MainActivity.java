package cl.eme.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cl.eme.miprimerproyecto.api.Api;
import cl.eme.miprimerproyecto.api.RetrofitClient;
import cl.eme.miprimerproyecto.pojos.Pregunta;
import cl.eme.miprimerproyecto.pojos.PreguntasLista;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "método onCreate");

        loadApiInfo();
    }

    private void loadApiInfo() {
        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);
        Call<PreguntasLista> call = service.getAllQuestions();

        //Async
        call.enqueue(new Callback<PreguntasLista>() {
            @Override
            public void onResponse(Call<PreguntasLista> call,
                                   Response<PreguntasLista> response) {
                if (response != null) {
                    /*
                    Log.d(TAG, response.toString());
                    Log.d(TAG, "------------------------------------------------");
                    for (int x = 0; x < response.body().getResults().size(); x++) {
                        Log.d(TAG, "Pregunta: " + response.body().getResults().get(x).getQuestion());
                    }

                     */
                    Pregunta pregunta = response.body().getResults().get(0);
                    crearFragmentPregunta(pregunta);
                }
            }

            @Override
            public void onFailure(Call<PreguntasLista> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: no pudimos recuperar los datos de opentdb", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearFragmentPregunta(Pregunta pregunta) {

        Log.d(TAG, pregunta.toString());
        PreguntaFragment preguntaFragment = PreguntaFragment.newInstance(
                        pregunta.getQuestion(),
                        pregunta.getCategory(),
                        pregunta.getCorrectAnswer(),
                        pregunta.getIncorrectAnswer()
                );
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, preguntaFragment,
                        "FRAGMENTO").commit();
    }


    // ---------------------------------------------------

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "método onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "método onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "método onDestroy");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "método onRestart");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "método onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "método onResume");

        // La actividad está disponible para que el usuario interactue con ella
        //Toast.makeText(this, R.string.hello_world, Toast.LENGTH_LONG).show();
    }
}