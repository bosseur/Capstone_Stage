package br.bosseur.beachvolleytour.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Root(name = "BeachRounds", strict = false)
public class BeachRoundList implements Tournament {

  @ElementList(inline = true, required = false)
  private List<BeachRound> rounds;

  public String getTournament(){
    if( rounds == null || rounds.isEmpty()) {
      return "";
    }
    return rounds.get(0).getNumberTournament();
  }

}
