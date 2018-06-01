package br.bosseur.beachvolleytour.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Root(name = "BeachTournament")
public class BeachTournament implements Comparable<BeachTournament>, Serializable, Parcelable {

  @Attribute(required = false, name = "Name")
  private String name;

  @Attribute(required = false, name = "Title")
  private String tile;

  @Attribute(required = false, name = "CountryCode")
  private String country;

  @Attribute(required = false, name = "StartDateMainDraw", empty = "2014-08-08")
  private Date startDate;

  @Attribute(required = false, name = "EndDateMainDraw", empty = "2014-08-08")
  private Date endDate;

  @Attribute(required = false, name = "Gender")
  private int gender;

  @Attribute(required = false, name = "Type", empty = "-1")
  private Integer type;

  @Attribute(required = false, name = "Code")
  private String tournamentCode;

  private String otherGenderTournamentCode;

  @Attribute(required = false, name = "No")
  private Integer number;

  @Attribute(required = false, name = "NoEvent")
  private int eventNumber;


  public BeachTournament(Parcel in) {
    this.name = in.readString();
    this.tile = in.readString();
    this.country = in.readString();
    this.startDate = new Date(in.readLong());
    this.endDate = new Date(in.readLong());
    this.gender = in.readInt();
    this.type = in.readInt();
    this.tournamentCode = in.readString();
    this.otherGenderTournamentCode = in.readString();
    this.number = in.readInt();
    this.eventNumber = in.readInt();
  }

  public String getMaleTournamentCode() {
    if (this.gender == 0) {
      return number.toString();
    }
    return otherGenderTournamentCode;
  }

  public String getFemaleTournamentCode() {
    if (this.gender == 1) {
      return number.toString();
    }
    return otherGenderTournamentCode;
  }

  public static List<BeachTournament> disintEvent(List<BeachTournament> events) {
    Map<Integer, BeachTournament> eventsWorldTour = new TreeMap<>();

    for (BeachTournament event : events) {

      if (!eventsWorldTour.containsKey(event.getEventNumber())) {
        eventsWorldTour.put(event.getEventNumber(), event);
      } else {
        BeachTournament tournament = eventsWorldTour.get(event.getEventNumber());
        if (event.getType() == 0) {
          tournament.setOtherGenderTournamentCode(event.getNumber().toString());
        } else {
          tournament.setOtherGenderTournamentCode(event.getNumber().toString());
        }

      }
    }

    return new ArrayList<>(eventsWorldTour.values());
  }

  public static final Parcelable.Creator<BeachTournament> CREATOR
      = new Parcelable.Creator<BeachTournament>() {

    public BeachTournament createFromParcel(Parcel in) {
      return new BeachTournament(in);
    }

    public BeachTournament[] newArray(int size) {
      return new BeachTournament[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(tile);
    dest.writeString(country);
    dest.writeLong(startDate.getTime());
    dest.writeLong(endDate.getTime());
    dest.writeInt(gender);
    dest.writeInt(type != null ? type : -1);
    dest.writeString(tournamentCode);
    dest.writeString(otherGenderTournamentCode);
    dest.writeInt(number);
    dest.writeInt(eventNumber);
  }

  @Override
  public String toString() {
    return "BeachTournament{" +
        "name='" + name + '\'' +
        ", country='" + country + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        "}\n";
  }

  @Override
  public int compareTo(BeachTournament another) {
    int compare = this.getStartDate().compareTo(another.getStartDate());
    return compare;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BeachTournament that = (BeachTournament) o;

    if (eventNumber != that.eventNumber) return false;
    if (country != null ? !country.equals(that.country) : that.country != null) return false;
    return !(type != null ? !type.equals(that.type) : that.type != null);

  }

  @Override
  public int hashCode() {
    int result = country != null ? country.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + eventNumber;
    return result;
  }

}
