package score.alarm.demo.kafka.models;

import java.util.List;
import java.util.Objects;

public class FullPlayerInfo {

    public long team_id;
    public String weight;
    public String shirt_number;
    public int preferred_foot;
    public int position;
    public long player_id;
    public String last_name;
    public String height;
    public String full_name;
    public String first_name;
    public String country_code;
    public String birth_date;
    public List<Stats> stats;

    public FullPlayerInfo() {
    }

    @Override
    public String toString() {
        return "FullPlayerInfo{" +
                "team_id=" + team_id +
                ", weight='" + weight + '\'' +
                ", shirt_number='" + shirt_number + '\'' +
                ", preferred_foot=" + preferred_foot +
                ", position=" + position +
                ", player_id=" + player_id +
                ", last_name='" + last_name + '\'' +
                ", height='" + height + '\'' +
                ", full_name='" + full_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", country_code='" + country_code + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", stats=" + stats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullPlayerInfo that = (FullPlayerInfo) o;
        return team_id == that.team_id && preferred_foot == that.preferred_foot && position == that.position && player_id == that.player_id && Objects.equals(weight, that.weight) && Objects.equals(shirt_number, that.shirt_number) && Objects.equals(last_name, that.last_name) && Objects.equals(height, that.height) && Objects.equals(full_name, that.full_name) && Objects.equals(first_name, that.first_name) && Objects.equals(country_code, that.country_code) && Objects.equals(birth_date, that.birth_date) && Objects.equals(stats, that.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team_id, weight, shirt_number, preferred_foot, position, player_id, last_name, height, full_name, first_name, country_code, birth_date, stats);
    }
}
