package score.alarm.demo.db.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "squads")
public class Squad {

    @Id
    private long id;
    private Team team;
    private List<Player> players;

    // this constructor is necessary for mongo repository to work
    public Squad() {
    }

    private Squad(SquadBuilder builder) {
        this.id = builder.id;
        this.team = builder.team;
        this.players = builder.players;
    }

    public long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static class SquadBuilder {
        private final long id;
        private Team team;
        private final List<Player> players = new ArrayList<>();

        public SquadBuilder(long id) {
            this.id = id;
        }

        public SquadBuilder setTeam(Team team) {
            this.team = team;
            return this;
        }

        public SquadBuilder addPlayer(Player player) {
            players.add(player);
            return this;
        }

        public Squad build() {
            return new Squad(this);
        }
    }

}
