package cropulse.io.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.Role;
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
}
