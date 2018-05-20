package br.bosseur.beachvolleytour.remote;

import br.bosseur.beachvolleytour.model.TournamentsResponses;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FivbServiceApi {

  @GET("XmlRequest.asmx")
  Observable<TournamentsResponses> requestTournaments(@Query("Request") String requestParam);

}
