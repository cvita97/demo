package score.alarm.demo.kafka.models;

import java.util.Objects;

public class Player {

    public long id;
    public int version;
    public Names names;

    public Player() {
    }

    @Override
    public String toString() {
        return "Player{" +
                "version=" + version +
                ", id=" + id +
                ", names=" + names +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && version == player.version && Objects.equals(names, player.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, names);
    }
}
