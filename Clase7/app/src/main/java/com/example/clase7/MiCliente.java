package com.example.clase7;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MiCliente {

    private  String url = "https://function-bun-production-232d.up.railway.app/api/characters";


    OkHttpClient client = new OkHttpClient();

    public ArrayList<Personaje> getElementos(){
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            String respuesta = response.body().string();
            ArrayList<Personaje> elementos = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray array = jsonObject.getJSONArray("characters");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Personaje personaje = new Personaje(
                        obj.getString("name"),
                        obj.getString("desc"),
                        obj.getString("photo"),
                        obj.getInt("ataque"),
                        obj.getInt("defensa")
                );

                elementos.add(personaje); // ERROR CORREGIDO: Antes agregabas 'elemento' (JSON)
            }
            return elementos;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

}