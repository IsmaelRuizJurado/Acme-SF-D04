
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.user_story.Priority;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		UserStory object;
		object = new UserStory();
		object.setDraftMode(true);
		final Manager manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setManager(manager);
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
			super.state(this.auxiliarService.validatePrice(object.getEstimatedCostPerHour(), 0, 1000000), "estimatedCostPerHour", "manager.userstory.form.error.estimatedCostPerHour");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "manager.userstory.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("description"))
			super.state(this.auxiliarService.validateTextImput(object.getDescription()), "description", "manager.userstory.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("acceptanceCriteria"))
			super.state(this.auxiliarService.validateTextImput(object.getAcceptanceCriteria()), "acceptanceCriteria", "manager.userstory.form.error.spam");
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		this.repository.save(object);
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
