import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();

        String urlBase = "http://localhost:8080/Predictions/hello";
        Request request = new Request.Builder().url(urlBase).build();
        Call call  = okHttpClient.newCall(request);

        try {
            final Response response = call.execute();
            System.out.println(response.body().string());
        }
        catch(Exception exception){

        }
    }
}