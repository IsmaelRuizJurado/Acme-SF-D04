
package acme.features.developer.training_modules;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.project.Project;
import acme.entities.training_module.Level;
import acme.entities.training_module.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository	repo;

	@Autowired
	protected AuxiliarService					auxiliarService;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		object = new TrainingModule();
		final Developer developer = this.repo.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setDeveloper(developer);
		object.setDraftMode(true);
		Date moment = MomentHelper.getCurrentMoment();
		object.setCreationTime(moment);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;
		super.bind(object, "code", "details", "basicLevel", "link", "project");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("details"))
			super.state(this.auxiliarService.validateTextImput(object.getDetails()), "details", "developer.training_module.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;
			existing = this.repo.findTrainingModuleByCode(object.getCode());
			super.state(existing == null, "code", "developer.training_module.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Project published;
			published = this.repo.findPublishedProjectById(object.getProject().getId());
			super.state(published != null, "project", "developer.training_module.form.error.project");
		}

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		this.repo.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationTime", "details", "basicLevel", "updateMoment", "optionalLink", "estimatedTotalTime", "draftMode", "developer", "project");
		final SelectChoices choices;
		choices = SelectChoices.from(Level.class, object.getBasicLevel());
		dataset.put("basicLevel", choices.getSelected().getKey());
		dataset.put("levels", choices);
		final SelectChoices choices2;
		choices2 = SelectChoices.from(this.repo.findPublishedProjects(), "code", object.getProject());
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices2);
		super.getResponse().addData(dataset);
	}

}
