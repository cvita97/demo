package score.alarm.demo.db.models;

public class Stats {

    private String name;
    private String lang;

    public Stats() {
    }

    private Stats(StatsBuilder builder) {
        this.name = builder.name;
        this.lang = builder.lang;
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
    }

    public static class StatsBuilder {
        private String name;
        private String lang;

        public StatsBuilder() {
        }

        public StatsBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StatsBuilder setLang(String lang) {
            this.lang = lang;
            return this;
        }

        public Stats build() {
            return new Stats(this);
        }
    }

}
