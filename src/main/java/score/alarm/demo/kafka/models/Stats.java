package score.alarm.demo.kafka.models;

import java.util.Objects;

public class Stats {

    public String name;
    public String lang;

    public Stats() {
    }

    @Override
    public String toString() {
        return "Stats{" +
                "name='" + name + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return Objects.equals(name, stats.name) && Objects.equals(lang, stats.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lang);
    }
}
