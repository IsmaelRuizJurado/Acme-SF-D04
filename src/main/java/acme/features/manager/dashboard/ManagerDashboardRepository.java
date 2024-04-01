
package acme.features.manager.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.user_story.Priority;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(us) from UserStory us where us.manager = :manager and us.priority = :priority and us.draftMode=false")
	Optional<Integer> findNumOfUserStoriesByPriority(Manager manager, Priority priority);

	@Query("select avg(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Double> findAverageProjectCost(Manager manager);

	@Query("select max(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Double> findMaxProjectCost(Manager manager);

	@Query("select min(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Double> findMinProjectCost(Manager manager);

	@Query("select stddev(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Double> findLinearDevProjectCost(Manager manager);

	@Query("select m from Manager m where m.userAccount.id = :id")
	Manager findOneManagerByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select avg(us.estimatedCostPerHour.amount) from UserStory us where us.manager = :manager and us.draftMode=false")
	Optional<Double> findAverageUserStoryCost(Manager manager);

	@Query("select max(us.estimatedCostPerHour.amount) from UserStory us where us.manager = :manager and us.draftMode=false")
	Optional<Double> findMaxUserStoryCost(Manager manager);

	@Query("select min(us.estimatedCostPerHour.amount) from UserStory us where us.manager = :manager and us.draftMode=false")
	Optional<Double> findMinUserStoryCost(Manager manager);

	@Query("select stddev(us.estimatedCostPerHour.amount) from UserStory us where us.manager = :manager and us.draftMode=false")
	Optional<Double> findLinearDevUserStoryCost(Manager manager);

}
