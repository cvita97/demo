package score.alarm.demo.kafka.models;

import java.util.List;
import java.util.Objects;

public class ShortTeamInfo {

    public long id;
    public List<Stats> stats;

    public ShortTeamInfo() {
    }

    public ShortTeamInfo(long id, List<Stats> stats) {
        this.id = id;
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "ShortTeamInfo{" +
                "id=" + id +
                ", stats=" + stats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortTeamInfo that = (ShortTeamInfo) o;
        return id == that.id && Objects.equals(stats, that.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stats);
    }
}
