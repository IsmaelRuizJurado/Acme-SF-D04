
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project_user_story.ProjectUserStory;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.userAccount.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select pus from ProjectUserStory pus where pus.project = :project")
	Collection<ProjectUserStory> findProjectUserStoriesByProject(Project project);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select us from UserStory us inner join ProjectUserStory pus on us = pus.userStory inner join Project p on pus.project = p where p.id = :id")
	Collection<UserStory> findUserStoriesByProject(int id);

	@Query("select p from Project p where p.code = :code")
	Project findProjectByCode(String code);
}
