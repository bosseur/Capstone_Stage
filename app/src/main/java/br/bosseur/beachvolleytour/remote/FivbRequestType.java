package br.bosseur.beachvolleytour.remote;

public enum FivbRequestType implements FivbRequest {

  TOURNAMENMTS {
    @Override
    public String getRequestQuery(String parameter) {
      return FivbRequestHelper.createFivbTourRequest(Integer.valueOf(parameter));
    }
  },
  MATCHES {
    @Override
    public String getRequestQuery(String parameter) {
      return null;
    }
  }
}
