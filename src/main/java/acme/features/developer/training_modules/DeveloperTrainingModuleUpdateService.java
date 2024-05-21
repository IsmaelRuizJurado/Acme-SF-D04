
package acme.features.developer.training_modules;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.training_module.Level;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleUpdateService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		TrainingModule object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId && object.getDraftMode());
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		Date moment = MomentHelper.getCurrentMoment();
		object.setUpdateMoment(moment);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;
		super.bind(object, "code", "details", "basicLevel");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("details"))
			super.state(this.auxiliarService.validateTextImput(object.getDetails()), "details", "developer.training_module.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;
			existing = this.repository.findTrainingModuleByCode(object.getCode());
			final TrainingModule module2 = object.getCode().equals("") || object.getCode().equals(null) ? null : this.repository.findTrainingModuleById(object.getId());

			super.state(existing == null || module2.equals(existing), "code", "developer.training_module.form.error.code");
		}
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationTime", "details", "basicLevel", "updateMoment", "optionalLink", "draftMode", "developer");
		final SelectChoices choices;
		choices = SelectChoices.from(Level.class, object.getBasicLevel());
		dataset.put("basicLevel", choices.getSelected().getKey());
		dataset.put("levels", choices);
		final List<TrainingSession> trainingSessions = this.repository.findTrainingSessionsByTrainingModule(object).stream().collect(Collectors.toList());
		dataset.put("hasTrainingSessions", trainingSessions.size() > 0);
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}

}
