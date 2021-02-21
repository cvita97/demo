package score.alarm.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import score.alarm.demo.db.models.Squad;
import score.alarm.demo.db.repositories.SquadRepository;
import score.alarm.demo.mappers.impl.MongoDTOMapper;


@RestController
@RequestMapping("api")
public class SquadController {

    private final SquadRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(SquadController.class);

    @Autowired
    public SquadController(SquadRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/squad", produces = {"application/json"})
    public ResponseEntity<?> getSquad(@RequestParam String lang, @RequestParam Long team_id) {
        Squad squad = repository.findById(team_id).orElse(null);

        if (squad == null) {
            logger.warn("Squad/team with " + team_id + " ID doesn't exist.");
            return ResponseEntity.notFound().build();
        }

        MongoDTOMapper mapper = new MongoDTOMapper(lang);

        return ResponseEntity.ok().body(mapper.mapTo(squad));
    }

}
