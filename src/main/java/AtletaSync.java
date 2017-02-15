import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AtletaSync {

    private static Retrofit retrofit;


    public static void main(String[] args) throws IOException {
        retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080").addConverterFactory(GsonConverterFactory.create()).build();
        AtletaService atletaService = retrofit.create(AtletaService.class);

        getAll(atletaService);


    // POST
        Atleta atleta = new Atleta();
        atleta.setNombre("Atleta1");
        atleta.setApellidos("Apellido1");
        atleta.setNacionalidad("Nacionalidad1");
        LocalDate date = LocalDate.of(1990, 10, 12);
        Call<Atleta> callAtleta = atletaService.createAtleta(atleta);
        Response<Atleta> responseAtleta= callAtleta.execute();

        if(responseAtleta.isSuccessful()) {
            System.out.println("- Nuevo Atleta");
            Atleta atletaResp = responseAtleta.body();
            System.out.println("  Code: " + responseAtleta.code() + System.lineSeparator() +
                    "  Atleta añadido: " + atletaResp);


    // PUT
            atletaResp.setApellidos("Apellido2");
            callAtleta = atletaService.updateAtleta(atletaResp);
            responseAtleta= callAtleta.execute();
            System.out.println("- Actualizar Atleta");
            System.out.println("  Code: " + responseAtleta.code() + System.lineSeparator() +
                    "  Atleta modificado: " + responseAtleta.body());

            getAll(atletaService);


    // DELETE
            Call<Void> callDelete= atletaService.deleteAtleta(atletaResp.getId());
            Response<Void> responseDelete= callDelete.execute();
            System.out.println("- Delete");
            System.out.println("  Code: " + responseDelete.code());

            getAll(atletaService);


        } else {
            System.out.println("Error: El atleta no se ha insertado");
            System.out.println("  Codigo: " + responseAtleta.code() +
                    "  Error: " + responseAtleta.errorBody());
        }

    // GET un Atleta
        callAtleta = atletaService.getAtleta(1L);
        responseAtleta = callAtleta.execute();
        if(responseAtleta.isSuccessful()) {
            System.out.println("- GET Atleta con ID:1" + System.lineSeparator() +
                    "  Code: " + responseAtleta.code() + System.lineSeparator() +
                    "  Atleta: " + responseAtleta.body() );
        }

    }

    public static void getAll(AtletaService atletaService) throws IOException{
        Call<List<Atleta>>call = atletaService.getAllAtletas();
        Response<List<Atleta>> response = call.execute();

        if(response.isSuccessful()) {
            List<Atleta> atletasList = response.body();
            System.out.println("- Get all");
            System.out.println("  Code: " + response.code() + System.lineSeparator() +
                    "  GET Atletas: " + atletasList);
        } else {
            System.out.println("  Code: " + response.code() +
                    "  Error: " + response.errorBody());
        }
    }
 }
