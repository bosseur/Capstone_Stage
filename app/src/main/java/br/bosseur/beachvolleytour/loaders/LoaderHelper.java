package br.bosseur.beachvolleytour.loaders;

import br.bosseur.beachvolleytour.model.BeachTournament;

class LoaderHelper {

  public static String[] createSelectionArgs(BeachTournament tournament) {

    if (tournament.getFemaleTournamentCode() != null && tournament.getMaleTournamentCode() != null) {
      return new String[]{tournament.getMaleTournamentCode(), tournament.getFemaleTournamentCode()};
    } else {
      return new String[]{tournament.getNumber().toString()};
    }
  }
}
