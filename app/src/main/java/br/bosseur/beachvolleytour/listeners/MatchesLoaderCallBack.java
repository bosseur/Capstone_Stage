package br.bosseur.beachvolleytour.listeners;

import java.util.List;

import br.bosseur.beachvolleytour.model.TournamentMatch;

public interface MatchesLoaderCallBack {

  void matchesLoaded(List<TournamentMatch> matches);

}
