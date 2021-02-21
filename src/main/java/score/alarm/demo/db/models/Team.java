package score.alarm.demo.db.models;

import java.util.ArrayList;
import java.util.List;


public class Team {
    private long id;
    private List<Stats> name;

    public Team() {
    }

    private Team(TeamBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public long getId() {
        return id;
    }

    public List<Stats> getName() {
        return name;
    }

    public static class TeamBuilder {
        private final long id;
        private List<Stats> name = new ArrayList<>();

        public TeamBuilder(long id) {
            this.id = id;
        }

        public TeamBuilder addStat(Stats stat) {
            this.name.add(stat);
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }
}
