package com.example.clase7;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MiCliente {

    private String url = "https://function-bun-production-46fa.up.railway.app/";
    OkHttpClient client = new OkHttpClient();


    public ArrayList<Personaje> getElements(){
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String respuesta =  response.body().string();

            Log.e("PRUEBA_SERVER", "LO QUE LLEGA: " + respuesta);

            ArrayList<Personaje> elementos = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray array = jsonObject.getJSONArray("characteres");
            if (array == null) {
                array = jsonObject.optJSONArray("characteres");
            }
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject elemento = array.optJSONObject(i);

                        if (elemento != null) {
                            String name = elemento.optString("name", elemento.optString("character", "Sin nombre"));
                            String desc = elemento.optString("desc", "Sin descripción");
                            String photo = elemento.optString("photo", "");
                            int attack = elemento.optInt("attack", 0);
                            int def = elemento.optInt("def", 0);

                            elementos.add(new Personaje(name, desc, photo, attack, def));
                        }
                    } catch (Exception e) {
                        Log.e("PRUEBA_SERVER", "Saltando dato basura en la posición " + i);
                    }
                }
            }else {
                Log.e("PRUEBA_SERVER", "No se encontró la llave characters o characteres");
            }

            //Log.i("Rey", respuesta);
            return elementos;
        } catch (IOException | JSONException e) {
            Log.e("PRUEBA_SERVER", "Error de red o JSON: " + e.getMessage());
        return new ArrayList<>();
    }

    }

    public void addElement(Personaje personaje) {
        final okhttp3.MediaType JSON = okhttp3.MediaType.get("application/json; charset=utf-8");

        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", personaje.getName());
            jsonObject.put("desc", personaje.getDesc());
            jsonObject.put("photo", personaje.getPhoto());
            jsonObject.put("attack", personaje.getAttack());
            jsonObject.put("def", personaje.getDef());

            String jsonString = jsonObject.toString();
            RequestBody body = RequestBody.create(jsonString, JSON);
            Request request = new Request.Builder()
                    .url(url + "add")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    Log.d("PRUEBA_SERVER", "¡LOGRADO! Personaje guardado.");
                } else {
                    Log.e("PRUEBA_SERVER", "Error " + response.code() + ": " + response.body().string());
                }
            }
        } catch (JSONException | IOException e) {
            Log.e("PRUEBA_SERVER", "Error fatal: " + e.getMessage());
        }
    }

    }

