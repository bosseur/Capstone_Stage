package br.bosseur.beachvolleytour.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.Getter;


@Root(strict = false, name = "TournamentsResponses")
@Data
@Getter
public class TournamentsResponses {

  @ElementList(name = "BeachTournaments")
  private List<BeachTournament> eventList;

  @Attribute(name = "ServerTime", required = false)
  private BigDecimal servertime;

}
