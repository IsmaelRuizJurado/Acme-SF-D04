
package acme.features.developer.training_sessions;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select ts from TrainingSession ts where ts.trainingModule = :trainingModule")
	Collection<TrainingSession> findTrainingSessionsByTrainingModule(TrainingModule trainingModule);

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("select ts from TrainingSession ts where ts.developer = :developer")
	Collection<TrainingSession> findTrainingSessionsByDeveloper(Developer developer);

	@Query("select ts from TrainingSession ts where ts.id = :id")
	TrainingSession findTrainingSessionById(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select tm from TrainingModule tm where tm.developer.userAccount.id = :id")
	Collection<TrainingModule> findTrainingModulesByDeveloperId(int id);

	@Query("select tm from TrainingModule tm where tm.developer.userAccount.id = :id and tm.draftMode = true")
	Collection<TrainingModule> findTrainingModulesNotPublishedByDeveloperId(int id);

	@Query("select ts from TrainingSession ts where ts.code = :code")
	TrainingSession findTrainingSessionByCode(String code);

}
