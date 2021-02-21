package score.alarm.demo.kafka.models;

import java.util.List;
import java.util.Objects;

public class Team {

    public int version;
    public List<Object> tournaments;
    public List<Object> sub_teams;
    public long sport_id;
    public Object manager_names;
    public Object jerseys;
    public long id;
    public String gender;
    public List<Object> features;
    public Object country_code;
    public Object country;
    public Names names;

    public Team() {
    }

    @Override
    public String toString() {
        return "Team{" +
                "version=" + version +
                ", tournaments=" + tournaments +
                ", sub_teams=" + sub_teams +
                ", sport_id=" + sport_id +
                ", manager_names=" + manager_names +
                ", jerseys=" + jerseys +
                ", id=" + id +
                ", gender='" + gender + '\'' +
                ", features=" + features +
                ", country_code=" + country_code +
                ", country=" + country +
                ", names=" + names +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return version == team.version && sport_id == team.sport_id && id == team.id && Objects.equals(tournaments, team.tournaments) && Objects.equals(sub_teams, team.sub_teams) && Objects.equals(manager_names, team.manager_names) && Objects.equals(jerseys, team.jerseys) && Objects.equals(gender, team.gender) && Objects.equals(features, team.features) && Objects.equals(country_code, team.country_code) && Objects.equals(country, team.country) && Objects.equals(names, team.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, tournaments, sub_teams, sport_id, manager_names, jerseys, id, gender, features, country_code, country, names);
    }
}
