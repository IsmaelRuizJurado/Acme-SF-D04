
package acme.features.any.training_module;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.training_module.TrainingModule;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.draftMode = false")
	Collection<TrainingModule> findPublishedTrainingModules();

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);
}
