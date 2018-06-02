package br.bosseur.beachvolleytour.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import lombok.Getter;

@Getter
@Root(name = "BeachMatches", strict = false)
public class MatchList implements Tournament {

  @ElementList(inline = true, required = false)
  private List<TournamentMatch> matches;

  public String getTournament(){
    if( matches == null || matches.isEmpty()) {
      return "";
    }
    return matches.get(0).getNumberTournament();
  }

}
