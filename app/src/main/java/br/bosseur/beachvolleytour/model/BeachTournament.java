package br.bosseur.beachvolleytour.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
@Root(name = "BeachTournament")
public class BeachTournament implements Comparable<BeachTournament>, Serializable {

    @Attribute(required = false, name = "Name")
    private String name;

    @Attribute(required = false, name = "Title")
    private String tile;

    @Attribute(required = false, name = "CountryCode")
    private String country;

    @Attribute(required = false, name = "StartDateMainDraw", empty = "")
    private Date startDate;

    @Attribute(required = false, name = "EndDateMainDraw")
    private Date endDate;

    @Attribute(required = false, name="Gender")
    private int gender;

    @Attribute(required = false, name = "Type")
    private Integer type;

    @Attribute(required = false, name = "Code")
    private String tournamentCode;

    private String otherGenderTournamentCode;

    @Attribute(required = false, name = "No")
    private Integer number;

    @Attribute(required = false, name = "Version")
    private String version;

    @Attribute(required = false, name = "NoEvent")
    private int eventNumber;

    @Attribute(required = false, name = "Status")
    private int status;

    public String getMaleTournamentCode() {
        if(this.gender == 0 ) {return number.toString();}
        return otherGenderTournamentCode;
    }

    public String getFemaleTournamentCode() {
        if(this.gender == 1) {return number.toString();}
        return otherGenderTournamentCode;
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
