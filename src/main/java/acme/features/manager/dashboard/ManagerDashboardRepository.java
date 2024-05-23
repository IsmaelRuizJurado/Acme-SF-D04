
package acme.features.manager.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.user_story.Priority;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	//User Story
	@Query("select count(u) from UserStory u where u.priority = :priority and u.draftMode =false and u.manager.id = :id")
	Integer countUSbyPriority(Priority priority, int id);

	@Query("select u.estimatedCostPerHour.currency, avg(u.estimatedCostPerHour.amount) from UserStory u where  u.manager.id = :id and u.draftMode =false group by u.estimatedCostPerHour.currency")
	List<Object[]> averageEstimationUserStories(int id);

	@Query("select u.estimatedCostPerHour.currency, STDDEV(u.estimatedCostPerHour.amount) from UserStory u where u.manager.id = :id and u.draftMode =false group by u.estimatedCostPerHour.currency")
	List<Object[]> deviationEstimationUserStories(int id);

	@Query("select u.estimatedCostPerHour.currency, min(u.estimatedCostPerHour.amount)from UserStory u where u.manager.id = :id and u.draftMode =false group by u.estimatedCostPerHour.currency")
	List<Object[]> minEstimationUserStories(int id);

	@Query("select u.estimatedCostPerHour.currency, max(u.estimatedCostPerHour.amount) from UserStory u where u.manager.id = :id and u.draftMode =false group by u.estimatedCostPerHour.currency")
	List<Object[]> maxEstimationUserStories(int id);

	//Project
	@Query("select p.cost.currency, avg(p.cost.amount) from Project p where  p.manager.id = :id and p.draftMode =false group by p.cost.currency")
	List<Object[]> averageProjectCost(int id);

	@Query("select p.cost.currency, STDDEV(p.cost.amount) from Project p where p.manager.id = :id and p.draftMode =false  group by p.cost.currency")
	List<Object[]> deviationProjectCost(int id);

	@Query("select s.cost.currency, min(s.cost.amount) from Project s where s.manager.id = :id and s.draftMode = false group by s.cost.currency")
	List<Object[]> minProjectCost(int id);

	@Query("select s.cost.currency, max(s.cost.amount) from Project s where s.manager.id = :id and s.draftMode = false group by s.cost.currency")
	List<Object[]> maxProjectCost(int id);

	@Query("select p from Project p where p.manager.id = :managerId and p.draftMode =false")
	Collection<Project> findManyProjectsByManagerId(int managerId);

}
