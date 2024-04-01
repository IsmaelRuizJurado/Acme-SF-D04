
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
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService<Employer, Job> -------------------------------------


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
		if (!super.getBuffer().getErrors().hasErrors("estimatedCostPerHour"))
			super.state(this.auxiliarService.validatePrice(object.getEstimatedCostPerHour(), 0, 1000000), "estimatedCostPerHour", "manager.user-story.form.error.estimatedCostPerHour");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "manager.user-story.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("description"))
			super.state(this.auxiliarService.validateTextImput(object.getDescription()), "description", "manager.user-story.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("acceptanceCriteria"))
			super.state(this.auxiliarService.validateTextImput(object.getAcceptanceCriteria()), "acceptanceCriteria", "manager.user-story.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("estimatedCostPerHour"))
			super.state(this.auxiliarService.validateCurrency(object.getEstimatedCostPerHour()), "estimatedCostPerHour", "manager.user-story.form.error.estimatedCostPerHour2");
	}

	@Override
	public void perform(final UserStory object) {
		object.setDraftMode(false);
		this.repository.save(object);
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
		super.getResponse().addData(dataset);
	}
}
