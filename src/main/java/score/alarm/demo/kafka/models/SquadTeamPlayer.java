package score.alarm.demo.kafka.models;

import java.util.List;
import java.util.Objects;

public class SquadTeamPlayer {

    public ShortTeamInfo team;
    public List<FullPlayerInfo> players;

    public SquadTeamPlayer() {
    }

    public SquadTeamPlayer(ShortTeamInfo team, List<FullPlayerInfo> players) {
        this.team = team;
        this.players = players;
    }

    @Override
    public String toString() {
        return "SquadTeamPlayer{" +
                "team=" + team +
                ", players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadTeamPlayer that = (SquadTeamPlayer) o;
        return Objects.equals(team, that.team) && Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, players);
    }
}
