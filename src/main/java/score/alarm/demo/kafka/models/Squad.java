package score.alarm.demo.kafka.models;

import java.util.List;
import java.util.Objects;

public class Squad {

    public int version;
    public long team_id;
    public List<FullPlayerInfo> players;

    public Squad() {
    }

    @Override
    public String toString() {
        return "Squad{" +
                "version=" + version +
                ", team_id=" + team_id +
                ", players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squad squad = (Squad) o;
        return version == squad.version && team_id == squad.team_id && Objects.equals(players, squad.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, team_id, players);
    }
}
