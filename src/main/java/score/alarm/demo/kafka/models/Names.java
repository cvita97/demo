package score.alarm.demo.kafka.models;

import java.util.List;
import java.util.Objects;

public class Names {

    public List<Object> unified;
    public List<Stats> stats;
    public List<Object> livescout;
    public List<Object> livescore;

    public Names() {
    }

    @Override
    public String toString() {
        return "Names{" +
                "unified=" + unified +
                ", stats=" + stats +
                ", livescout=" + livescout +
                ", livescore=" + livescore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Names names = (Names) o;
        return Objects.equals(unified, names.unified) && Objects.equals(stats, names.stats) && Objects.equals(livescout, names.livescout) && Objects.equals(livescore, names.livescore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unified, stats, livescout, livescore);
    }
}
