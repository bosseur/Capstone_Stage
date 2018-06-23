package br.bosseur.beachvolleytour.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Root(name = "BeachMatch", strict = false)
public class TournamentMatch implements Comparable<TournamentMatch>, Parcelable {

  @Attribute(name = "NoTournament")
  private String numberTournament;

  @Attribute(name = "NoRound", required = false)
  private int round;

  @Attribute(name = "NoInTournament")
  private int matchNumber;

  @Attribute(name = "NoTeamA")
  private String teamA;

  @Attribute(name = "NoTeamB")
  private String teamB;

  @Attribute(name = "TeamAName", required = false)
  private String teamAName;

  @Attribute(name = "TeamBName", required = false)
  private String teamBName;

  @Attribute(name = "Court", required = false)
  private String court;

  @Attribute(name = "TeamAFederationCode", required = false)
  private String teamAFederationCode;

  @Attribute(name = "TeamBFederationCode", required = false)
  private String teamBFederationCode;

  @Attribute(name = "PointsTeamASet1", empty = "0", required = false)
  private String pointsTeamASet1;

  @Attribute(name = "PointsTeamASet2", empty = "0", required = false)
  private String pointsTeamASet2;

  @Attribute(name = "PointsTeamASet3", empty = "0", required = false)
  private String pointsTeamASet3;

  @Attribute(name = "PointsTeamBSet1", empty = "0", required = false)
  private String pointsTeamBSet1;

  @Attribute(name = "PointsTeamBSet2", empty = "0", required = false)
  private String pointsTeamBSet2;

  @Attribute(name = "PointsTeamBSet3", empty = "0", required = false)
  private String pointsTeamBSet3;

  @Attribute(name = "ResultType", required = false)
  private int resultType;

  @Attribute(name = "TeamAPositionInMainDraw")
  private String teamAPositionInMainDraw;

  @Attribute(name = "TeamBPositionInMainDraw")
  private String teamBPositionInMainDraw;


  @Override
  public String toString() {
    return "TournamentMatch{" +
        "teamAName='" + teamAName + "\' vs " +
        " teamBName='" + teamBName + "\'" +
        "}\n";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TournamentMatch that = (TournamentMatch) o;

    return matchNumber == that.matchNumber;

  }

  @Override
  public int hashCode() {
    return matchNumber;
  }

  @Override
  public int compareTo(TournamentMatch another) {
    if (this.matchNumber < another.matchNumber) {
      return 1;
    }
    if (this.matchNumber > another.matchNumber) {
      return -1;
    }
    return 0;
  }

  public String getTeamA() {
    return this.teamAName + " " + teamAFederationCode + " (" + teamAPositionInMainDraw + ")";
  }

  public String getTeamB() {
    return this.teamBName + " " + teamBFederationCode + " (" + teamBPositionInMainDraw + ")";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.numberTournament);
    dest.writeInt(this.round);
    dest.writeInt(this.matchNumber);
    dest.writeString(this.teamA);
    dest.writeString(this.teamB);
    dest.writeString(this.teamAName);
    dest.writeString(this.teamBName);
    dest.writeString(this.court);
    dest.writeString(this.teamAFederationCode);
    dest.writeString(this.teamBFederationCode);
    dest.writeString(this.pointsTeamASet1);
    dest.writeString(this.pointsTeamASet2);
    dest.writeString(this.pointsTeamASet3);
    dest.writeString(this.pointsTeamBSet1);
    dest.writeString(this.pointsTeamBSet2);
    dest.writeString(this.pointsTeamBSet3);
    dest.writeInt(this.resultType);
    dest.writeString(this.teamAPositionInMainDraw);
    dest.writeString(this.teamBPositionInMainDraw);
  }

  protected TournamentMatch(Parcel in) {
    this.numberTournament = in.readString();
    this.round = in.readInt();
    this.matchNumber = in.readInt();
    this.teamA = in.readString();
    this.teamB = in.readString();
    this.teamAName = in.readString();
    this.teamBName = in.readString();
    this.court = in.readString();
    this.teamAFederationCode = in.readString();
    this.teamBFederationCode = in.readString();
    this.pointsTeamASet1 = in.readString();
    this.pointsTeamASet2 = in.readString();
    this.pointsTeamASet3 = in.readString();
    this.pointsTeamBSet1 = in.readString();
    this.pointsTeamBSet2 = in.readString();
    this.pointsTeamBSet3 = in.readString();
    this.resultType = in.readInt();
    this.teamAPositionInMainDraw = in.readString();
    this.teamBPositionInMainDraw = in.readString();
  }

  public static final Parcelable.Creator<TournamentMatch> CREATOR = new Parcelable.Creator<TournamentMatch>() {
    @Override
    public TournamentMatch createFromParcel(Parcel source) {
      return new TournamentMatch(source);
    }

    @Override
    public TournamentMatch[] newArray(int size) {
      return new TournamentMatch[size];
    }
  };
}
