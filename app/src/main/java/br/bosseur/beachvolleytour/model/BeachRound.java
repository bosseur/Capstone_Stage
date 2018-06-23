package br.bosseur.beachvolleytour.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Root(name = "BeachRound", strict = false)
public class BeachRound implements Comparable<BeachRound>, Parcelable {

  @Attribute(name = "NoTournament")
  private String numberTournament;

  @Attribute(name = "StartDate", required = false)
  private Date startDate;

  @Attribute(name = "Name")
  private String name;

  @Attribute(name = "Phase")
  private int phase;

  @Attribute(name = "No")
  private int number;

  @Attribute(required = false)
  @Builder.Default private ArrayList<TournamentMatch> matches = new ArrayList<>();


  public void addMatch(TournamentMatch match) {
    this.matches.add(match);
  }

  public void addMatches(ArrayList<TournamentMatch> matches) {
    this.matches = matches;
  }

  @Override
  public String toString() {
    return "BeachRound{" +
        "name='" + name + '\'' +
        '}' + this.matches.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BeachRound that = (BeachRound) o;

    return number == that.number;

  }

  @Override
  public int hashCode() {
    return number;
  }

  @Override
  public int compareTo(BeachRound another) {
    if (this.number < another.number) {
      return 1;
    }
    if (this.number > another.number) {
      return -1;
    }
    return 0;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.numberTournament);
    dest.writeLong(this.startDate.getTime());
    dest.writeString(this.name);
    dest.writeInt(this.phase);
    dest.writeInt(this.number);
    dest.writeList(this.matches != null ? matches : new ArrayList());
  }

  protected BeachRound(Parcel in) {
    this.numberTournament = in.readString();
    this.startDate = new Date(in.readLong());
    this.name = in.readString();
    this.phase = in.readInt();
    this.number = in.readInt();
    this.matches = new ArrayList<>();
    in.readList(this.matches, TournamentMatch.class.getClassLoader());
  }

  public static final Parcelable.Creator<BeachRound> CREATOR = new Parcelable.Creator<BeachRound>() {
    @Override
    public BeachRound createFromParcel(Parcel source) {
      return new BeachRound(source);
    }

    @Override
    public BeachRound[] newArray(int size) {
      return new BeachRound[size];
    }
  };
}
