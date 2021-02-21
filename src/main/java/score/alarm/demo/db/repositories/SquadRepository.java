package score.alarm.demo.db.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import score.alarm.demo.db.models.Squad;

public interface SquadRepository extends MongoRepository<Squad, Long> {
}
