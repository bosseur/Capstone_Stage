package br.bosseur.beachvolleytour.remote;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import br.bosseur.beachvolleytour.model.TournamentsResponses;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import timber.log.Timber;

public class FivbNetworkService {
  private static final String BASE_FIVB_API_URL = "https://www.fivb.org/vis2009/";

  private static Retrofit retrofit;

  private FivbNetworkService() {
  }

  private static void createRetrofit() {
    if (retrofit == null) {

      retrofit = new Retrofit.Builder()
          .baseUrl(BASE_FIVB_API_URL)
          .client(buildClient())
          .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
              new Persister(new AnnotationStrategy()
              )))
          .build();
    }
  }

  private static OkHttpClient buildClient() {
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    httpClientBuilder.addInterceptor(chain -> {
      Request original = chain.request();

      Request request = original.newBuilder()
          .header("Accept-charset", StandardCharsets.UTF_8.name())
          .header("Accept", "application/xml")
          .method(original.method(), original.body())
          .build();

      return chain.proceed(request);

    });

    return httpClientBuilder.build();
  }

  public static Response<TournamentsResponses> requestTournaments(String parameter)
      throws IOException {
    createRetrofit();
    try {
      return retrofit.create(FivbServiceApi.class).request(parameter).execute();
    }catch (Exception ex){
      Timber.e(ex);
    }
    return null;
  }
}
