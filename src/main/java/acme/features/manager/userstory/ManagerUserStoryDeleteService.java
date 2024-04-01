
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project_user_story.ProjectUserStory;
import acme.entities.user_story.Priority;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryDeleteService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		UserStory object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findUserStoryById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		UserStory object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findUserStoryById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;
		super.bind(object, "title", "description", "estimatedCostPerHour", "acceptanceCriteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		final Collection<ProjectUserStory> projectUserStories = this.repository.findProjectUserStoriesByUserStory(object);
		for (final ProjectUserStory pus : projectUserStories)
			this.repository.delete(pus);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCostPerHour", "acceptanceCriteria", "priority", "link", "draftMode", "manager");
		final SelectChoices choices;
		choices = SelectChoices.from(Priority.class, object.getPriority());
		dataset.put("priority", choices.getSelected().getKey());
		dataset.put("priorities", choices);
		super.getResponse().addData(dataset);
	}
}
