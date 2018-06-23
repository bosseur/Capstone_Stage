package br.bosseur.beachvolleytour.remote;

import org.junit.Test;

import java.io.IOException;

import br.bosseur.beachvolleytour.model.TournamentsResponses;
import retrofit2.Response;


public class FivbNetworkServiceTest {

  @Test
  public void testRequestTournaments() throws IOException {
    Response<TournamentsResponses> observable = FivbNetworkService.requestTournaments
        (FivbRequestHelper.createFivbTourRequest(2018));


  }
}
