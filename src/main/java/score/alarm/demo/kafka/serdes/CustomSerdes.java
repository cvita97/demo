package score.alarm.demo.kafka.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import score.alarm.demo.kafka.models.*;

/**
 * Class represents SerDes factory. It contains public methods which
 * returns appropriate {@link Serde} wrapper. Exposed Serdes are only
 * for JSON data.
 */
public class CustomSerdes {

    static public final class PlayerSerde extends Serdes.WrapperSerde<Player> {
        public PlayerSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(Player.class));
        }
    }

    static public final class TeamSerde extends Serdes.WrapperSerde<Team> {
        public TeamSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(Team.class));
        }
    }

    static public final class SquadSerde extends Serdes.WrapperSerde<Squad> {
        public SquadSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(Squad.class));
        }
    }

    static public final class SquadTeamPlayerSerde extends Serdes.WrapperSerde<SquadTeamPlayer> {
        public SquadTeamPlayerSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(SquadTeamPlayer.class));
        }
    }

    static public final class FullPlayerInfoSerde extends Serdes.WrapperSerde<FullPlayerInfo> {
        public FullPlayerInfoSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(FullPlayerInfo.class));
        }
    }

    public static Serde<Player> Player() {
        return new PlayerSerde();
    }

    public static Serde<Team> Team() {
        return new TeamSerde();
    }

    public static Serde<Squad> Squad() {
        return new SquadSerde();
    }

    public static Serde<SquadTeamPlayer> SquadTeamPlayer() {
        return new SquadTeamPlayerSerde();
    }

    public static Serde<FullPlayerInfo> FullPlayerInfo() {
        return new FullPlayerInfoSerde();
    }

}
