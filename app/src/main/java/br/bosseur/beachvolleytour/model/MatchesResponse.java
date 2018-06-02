package br.bosseur.beachvolleytour.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;

@Getter
@Root(strict = false)
public class MatchesResponse {
  @ElementList(inline = true)
  private List<BeachRoundList> rounds;

  @ElementList(inline = true)
  private List<MatchList> matches;

  @Attribute(name = "ServerTime", required = false)
  private BigDecimal servertime;

}
