package b.rcom.hunter.Hunter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;

import java.util.Optional;

public interface GamerRepository extends CrudRepository<Gamer, String> {

    Gamer findByEmail(String email);
}
