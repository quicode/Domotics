package com.example.domotics;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {
    private static final String ruta="http://huayacocotla.com/ws/registra.php";
    //private static final String ruta="http://201.156.18.205:3001/iot/registra.php";
    private Map<String, String> parametros;
    public RegistroRequest(String nombre, String usuario, String clave, int edad, Response.Listener<String> listener){
        super(Request.Method.POST, ruta , listener, null);
        parametros = new HashMap<>();
        parametros.put("nombre",nombre+"");
        parametros.put("usuario",usuario+"");
        parametros.put("clave",clave+"");
        parametros.put("edad",edad+"");
    }

    @Override
    public Map<String, String> getParams() {
        return parametros;
    }
}
