package br.bosseur.beachvolleytour.remote;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.bosseur.beachvolleytour.model.MatchesResponse;
import br.bosseur.beachvolleytour.model.TournamentsResponses;
import br.bosseur.beachvolleytour.xml.transforme.DateTransformer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FivbNetworkService {
  private static final String BASE_FIVB_API_URL = "https://www.fivb.org/vis2009/";
  private static final String YEAR_MONTH_DATE_FORMAT = "yyyy-MM-dd";

  private static Retrofit retrofit;

  private FivbNetworkService() {
  }

  private static void createRetrofit() {
    if (retrofit == null) {

      RegistryMatcher matcher = new RegistryMatcher();
      Persister serializer = new Persister(matcher);
      matcher.bind(Date.class, new DateTransformer(YEAR_MONTH_DATE_FORMAT));

      retrofit = new Retrofit.Builder()
          .baseUrl(BASE_FIVB_API_URL)
          .client(buildClient())
          .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(serializer))
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

    })
        .readTimeout(5000, TimeUnit.MILLISECONDS);

    return httpClientBuilder.build();
  }

  public static Response<TournamentsResponses> requestTournaments(String parameter) throws Exception {
    createRetrofit();
    return retrofit.create(FivbServiceApi.class).requestTournaments(parameter).execute();

  }

  public static Response<MatchesResponse> requestMatches(String parameter) throws Exception {
    createRetrofit();
    return retrofit.create(FivbServiceApi.class).requestMacthes(parameter).execute();
  }
}
