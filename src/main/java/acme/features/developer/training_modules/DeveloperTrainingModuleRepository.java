
package acme.features.developer.training_modules;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.developer.userAccount.id = :id")
	Collection<TrainingModule> findTrainingModulesByDeveloperId(int id);

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select tm from TrainingModule tm where tm.code = :code")
	TrainingModule findTrainingModuleByCode(String code);

	@Query("select ts from TrainingSession ts where ts.trainingModule = :trainingModule")
	Collection<TrainingSession> findTrainingSessionsByTrainingModule(TrainingModule trainingModule);

}
