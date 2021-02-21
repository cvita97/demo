package score.alarm.demo;


import score.alarm.demo.kafka.models.Names;
import score.alarm.demo.kafka.models.Player;
import score.alarm.demo.kafka.models.Stats;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<Player> getSamePlayer() {
        List<Player> players = new ArrayList<>();

        Player player = new Player();
        player.version = 1;
        player.names = new Names();
        player.names.unified = new ArrayList<>();
        player.names.stats = new ArrayList<>();
        Stats playerStats = new Stats();
        playerStats.name = "John Norman";
        playerStats.lang = "en";
        player.names.stats.add(playerStats);
        player.names.livescout = new ArrayList<>();
        player.names.livescore = new ArrayList<>();
        player.id = 133704L;

        Player player2 = new Player();
        player2.version = 2;
        player2.names = new Names();
        player2.names.unified = new ArrayList<>();
        player2.names.stats = new ArrayList<>();
        Stats player2Stats = new Stats();
        player2Stats.name = "John Norman";
        player2Stats.lang = "en";
        player2.names.stats.add(player2Stats);
        player2.names.livescout = new ArrayList<>();
        player2.names.livescore = new ArrayList<>();
        player2.id = 133704L;

        Player player3 = new Player();
        player3.version = 3;
        player3.names = new Names();
        player3.names.unified = new ArrayList<>();
        player3.names.stats = new ArrayList<>();
        Stats player3Stats = new Stats();
        player3Stats.name = "John Norman";
        player3Stats.lang = "en";
        player3.names.stats.add(player3Stats);
        player3.names.livescout = new ArrayList<>();
        player3.names.livescore = new ArrayList<>();
        player3.id = 133704L;

        players.add(player);
        players.add(player2);
        players.add(player3);

        return players;
    }

}
