package br.bosseur.beachvolleytour.utils;

import android.content.Context;

import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.model.BeachRound;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.model.TournamentMatch;

public class DataUtil {

  public static Map<String, ArrayList<BeachRound>> getRoundsPerGender(final BeachTournament tournament,
                                                                      final List<BeachRound> beachRounds,
                                                                      final List<TournamentMatch> matches,
                                                                      final Context context) {
    Map<String, ArrayList<BeachRound>> roundsPerGender = new HashMap<>();
    if(tournament.getFemaleTournamentCode() != null){
      Collection<BeachRound> filteredRounds = filterRounds(beachRounds, tournament.getFemaleTournamentCode());
      addMatchesToRounds(matches, filteredRounds);
      roundsPerGender.put(context.getString(R.string.female), new ArrayList<>(filteredRounds));
    }

    if(tournament.getMaleTournamentCode() != null){
      Collection<BeachRound> filteredRounds = filterRounds(beachRounds, tournament.getMaleTournamentCode());
      addMatchesToRounds(matches, filteredRounds);
      roundsPerGender.put(context.getString(R.string.male), new ArrayList<>(filteredRounds));
    }
    return roundsPerGender;
  }

  private static void addMatchesToRounds(final List<TournamentMatch> matches, final Collection<BeachRound> filteredRounds) {
    for(BeachRound round : filteredRounds){
      Collection<TournamentMatch> matchesRound = Collections2.filter(matches, (match) -> match.getRound() == round.getNumber());
      round.addMatches(new ArrayList<>(matchesRound));
    }
  }

  private static Collection<BeachRound> filterRounds(List<BeachRound> beachRounds, String tournamentCode) {
    return Collections2.filter(beachRounds, round -> round.getNumberTournament().equals(tournamentCode));
  }
}
