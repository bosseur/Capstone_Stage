package br.bosseur.beachvolleytour.remote;

import br.bosseur.beachvolleytour.model.BeachTournament;

public enum FivbRequestType implements FivbRequest {

  TOURNAMENMTS {
    @Override
    public String getRequestQuery(Object parameter) {
      return FivbRequestHelper.createFivbTourRequest(Integer.valueOf(parameter.toString()));
    }
  },
  LATEST_TOURNAMENT {
    @Override
    public String getRequestQuery(Object parameter) {
      return null;
    }
  },
  MATCHES {
    @Override
    public String getRequestQuery(Object parameter) {
      return FivbRequestHelper.createMatches((BeachTournament) parameter);
    }
  }
}
