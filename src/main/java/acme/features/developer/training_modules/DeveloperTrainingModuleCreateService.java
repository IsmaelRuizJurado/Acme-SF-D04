
package acme.features.developer.training_modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
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
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;
		super.bind(object, "code", "creationTime", "details", "basicLevel", "updateMoment", "link");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("creationTime"))
			super.state(this.auxiliarService.validateDate(object.getCreationTime()), "creationTime", "developer.training-module.form.error.creationTime");
		if (!super.getBuffer().getErrors().hasErrors("details"))
			super.state(this.auxiliarService.validateTextImput(object.getDetails()), "details", "developer.training-module.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;
			existing = this.repo.findTrainingModuleByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-module.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(this.auxiliarService.validateDate(object.getUpdateMoment()), "updateMoment", "developer.training-module.form.error.updateMoment");
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
		dataset = super.unbind(object, "code", "creationTime", "details", "basicLevel", "updateMoment", "optionalLink", "estimatedTotalTime", "draftMode", "developer");
		final SelectChoices choices;
		choices = SelectChoices.from(Level.class, object.getBasicLevel());
		dataset.put("basicLevel", choices.getSelected().getKey());
		dataset.put("levels", choices);
		super.getResponse().addData(dataset);
	}

}
