
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project_user_story.ProjectUserStory;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select us from UserStory us inner join ProjectUserStory pus on us = pus.userStory inner join Project p on pus.project = p where p.id = :id")
	Collection<UserStory> findUserStoriesByProject(int id);

	@Query("select us from UserStory us where us.manager = :manager")
	Collection<UserStory> findUserStoriesByManager(Manager manager);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select us from UserStory us where us.id = :id")
	UserStory findUserStoryById(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select p from Project p inner join ProjectUserStory pus on p = pus.project inner join UserStory us on pus.userStory = us where us = :userStory")
	Collection<Project> getProjectByUserStory(UserStory userStory);

	@Query("select pus from ProjectUserStory pus where pus.userStory = :userStory")
	Collection<ProjectUserStory> findProjectUserStoriesByUserStory(UserStory userStory);

}
