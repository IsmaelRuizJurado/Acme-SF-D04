
package acme.features.any.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.user_story.UserStory;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findPublishedProjects();

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select us from UserStory us inner join ProjectUserStory pus on us = pus.userStory inner join Project p on pus.project = p where p.id = :id")
	Collection<UserStory> findUserStoriesByProject(int id);
}
