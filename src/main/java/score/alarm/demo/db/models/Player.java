package score.alarm.demo.db.models;


import java.util.ArrayList;
import java.util.List;

public class Player {

    private String weight;
    private String shirt_number;
    private int preferred_foot;
    private int position;
    private long player_id;
    private String last_name;
    private String height;
    private String full_name;
    private String first_name;
    private String country_code;
    private String birth_date;
    private List<Stats> stats;

    public Player() {
    }

    private Player(PlayerBuilder builder) {
        this.weight = builder.weight;
        this.shirt_number = builder.shirt_number;
        this.preferred_foot = builder.preferred_foot;
        this.position = builder.position;
        this.player_id = builder.player_id;
        this.last_name = builder.last_name;
        this.height = builder.height;
        this.full_name = builder.full_name;
        this.first_name = builder.first_name;
        this.country_code = builder.country_code;
        this.birth_date = builder.birth_date;
        this.stats = builder.stats;
    }

    public String getWeight() {
        return weight;
    }

    public String getShirt_number() {
        return shirt_number;
    }

    public int getPreferred_foot() {
        return preferred_foot;
    }

    public int getPosition() {
        return position;
    }

    public long getPlayer_id() {
        return player_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getHeight() {
        return height;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public static class PlayerBuilder {
        private String weight;
        private String shirt_number;
        private int preferred_foot;
        private int position;
        private final long player_id;
        private String last_name;
        private String height;
        private String full_name;
        private String first_name;
        private String country_code;
        private String birth_date;
        private final List<Stats> stats = new ArrayList<>();

        public PlayerBuilder(long player_id) {
            this.player_id = player_id;
        }

        public PlayerBuilder setWeight(String weight) {
            this.weight = weight;
            return this;
        }

        public PlayerBuilder setShirtNumber(String shirt_number) {
            this.shirt_number = shirt_number;
            return this;
        }

        public PlayerBuilder setPreferredFoot(int preferred_foot) {
            this.preferred_foot = preferred_foot;
            return this;
        }

        public PlayerBuilder setPosition(int position) {
            this.position = position;
            return this;
        }

        public PlayerBuilder setLastName(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public PlayerBuilder setHeight(String height) {
            this.height = height;
            return this;
        }

        public PlayerBuilder setFullName(String full_name) {
            this.full_name = full_name;
            return this;
        }

        public PlayerBuilder setFirstName(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public PlayerBuilder setCountryCode(String country_code) {
            this.country_code = country_code;
            return this;
        }

        public PlayerBuilder setBirthDate(String birth_date) {
            this.birth_date = birth_date;
            return this;
        }

        public PlayerBuilder addStat(Stats stat) {
            this.stats.add(stat);
            return this;
        }

        public Player build() {
            return new Player(this);
        }

    }
}
