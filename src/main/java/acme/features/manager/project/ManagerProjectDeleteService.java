
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.project.Project;
import acme.entities.project_user_story.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerProjectRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Project object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "id", "code", "title", "abstractt", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		super.state(this.repository.findSponsorshipsByProject(object).isEmpty(), "*", "manager.project.form.error.deleteSponsorship");
		super.state(this.repository.findTrainingModulesByProject(object).isEmpty(), "*", "manager.project.form.error.deleteTrainingModule");
		super.state(this.repository.findContractsByProject(object).isEmpty(), "*", "manager.project.form.error.deleteContract");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		final Collection<ProjectUserStory> projectUserStories = this.repository.findProjectUserStoriesByProject(object);
		for (final ProjectUserStory pus : projectUserStories)
			this.repository.delete(pus);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstractt", "cost", "link", "draftMode", "manager");
		dataset.put("money", this.auxiliarService.changeCurrency(object.getCost()));
		super.getResponse().addData(dataset);
	}
}
