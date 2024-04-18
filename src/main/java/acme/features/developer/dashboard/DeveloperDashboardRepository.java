
package acme.features.developer.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select count(tm) from TrainingModule tm where tm.developer = :developer and tm.updateMoment is not null and tm.draftMode=false")
	Optional<Integer> findNumOfTrainingModulesWithUpdateMoment(Developer developer);

	@Query("select d from Developer d where d.userAccount.id = :id")
	Developer findOneDeveloperByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select count(ts) from TrainingSession ts where ts.developer = :developer and ts.optionalLink is not null and ts.draftMode=false")
	Optional<Integer> findNumOfTrainingSessionsWithLink(Developer developer);

	@Query("select ts from TrainingSession ts where ts.trainingModule = :trainingModule")
	Collection<TrainingSession> findTrainingSessionsByTrainingModule(TrainingModule trainingModule);

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("select tm from TrainingModule tm")
	List<TrainingModule> findAllModules();

}
