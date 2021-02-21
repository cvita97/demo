package score.alarm.demo.mappers.impl;

import score.alarm.demo.db.models.Squad;
import score.alarm.demo.db.models.Stats;
import score.alarm.demo.dtos.PlayerResponse;
import score.alarm.demo.dtos.SquadResponse;
import score.alarm.demo.dtos.TeamResponse;
import score.alarm.demo.mappers.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper that converts Mongo document to DTO (Data Transfer Object) object.
 * Default mode of converting is english and it could be changed with given
 * language {@link lang} parameter.
 *
 * @see Mapper
 */
public class MongoDTOMapper implements Mapper<Squad, SquadResponse> {

    private final String lang;
    private static final String ENGLISH_LANG = "en";

    public MongoDTOMapper(String lang) {
        this.lang = lang;
    }

    @Override
    public SquadResponse mapTo(Squad mongo) {
        SquadResponse response = new SquadResponse();

        TeamResponse team = new TeamResponse();
        team.id = mongo.getId();
        team.name = getNameForGivenLang(mongo.getTeam().getName(), lang);

        response.team = team;
        response.players = new ArrayList<>();

        mongo.getPlayers().forEach(p -> {
            PlayerResponse player = new PlayerResponse();

            String playerName = getNameForGivenLang(p.getStats(), lang);
            player.name = !playerName.isEmpty() ? playerName : p.getFirst_name() + " " + p.getLast_name();
            player.player_id = p.getPlayer_id();
            player.weight = p.getWeight();
            player.height = p.getHeight();
            player.shirt_number = p.getShirt_number();
            player.birth_date = p.getBirth_date();
            player.first_name = p.getFirst_name();
            player.last_name = p.getLast_name();
            player.country_code = p.getCountry_code();
            player.position = p.getPosition();
            player.preferred_foot = p.getPreferred_foot();

            response.players.add(player);
        });

        return response;
    }

    private String getNameForGivenLang(List<Stats> stats, String lang) {
        String enName = "";
        String name = null;

        for (Stats stat : stats) {
            if (ENGLISH_LANG.equals(stat.getLang())) {
                enName = stat.getName();
                continue;
            }
            if (lang.equals(stat.getLang())) {
                name = stat.getName();
                break;
            }
        }
        if (name == null || name.isEmpty()) {
            name = enName;
        }

        return name;
    }

}
