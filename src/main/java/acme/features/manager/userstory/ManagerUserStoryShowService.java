
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.user_story.Priority;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		UserStory object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findUserStoryById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId);
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
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCostPerHour", "acceptanceCriteria", "priority", "link", "draftMode");
		final SelectChoices choices;
		choices = SelectChoices.from(Priority.class, object.getPriority());
		dataset.put("priority", choices.getSelected().getKey());
		dataset.put("priorities", choices);
		dataset.put("money", this.auxiliarService.changeCurrency(object.getEstimatedCostPerHour()));
		super.getResponse().addData(dataset);
	}
}
