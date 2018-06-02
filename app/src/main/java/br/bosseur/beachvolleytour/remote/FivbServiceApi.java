package br.bosseur.beachvolleytour.remote;

import br.bosseur.beachvolleytour.model.MatchesResponse;
import br.bosseur.beachvolleytour.model.TournamentsResponses;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FivbServiceApi {

  @GET("XmlRequest.asmx")
  Call<TournamentsResponses> requestTournaments(@Query("Request") String requestParam);

  @GET("XmlRequest.asmx")
  Call<MatchesResponse> requestMacthes(@Query("Request") String requestParam);

}
