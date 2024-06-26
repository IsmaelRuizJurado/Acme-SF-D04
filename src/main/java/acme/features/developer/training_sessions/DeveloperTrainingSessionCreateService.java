
package acme.features.developer.training_sessions;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionRepository	repository;

	@Autowired
	protected AuxiliarService						auxiliarService;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingSession object;
		object = new TrainingSession();
		object.setDraftMode(true);
		final Developer developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setDeveloper(developer);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;
		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "optionalLink", "trainingModule");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			super.state(this.auxiliarService.validateDate(object.getStartPeriod()), "startPeriod", "developer.training-session.form.error.startPeriod");
			super.state(object.getTrainingModule() == null || object.getStartPeriod().after(object.getTrainingModule().getCreationTime()), "startPeriod", "developer.training-session.form.error.startPeriod.beforeCreationTime");
		}

		if (!super.getBuffer().getErrors().hasErrors("location"))
			super.state(this.auxiliarService.validateTextImput(object.getLocation()), "location", "developer.training-session.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("instructor"))
			super.state(this.auxiliarService.validateTextImput(object.getInstructor()), "instructor", "developer.training-session.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			super.state(this.auxiliarService.validateDate(object.getEndPeriod()), "endPeriod", "developer.training-session.form.error.endPeriod");
			if (object.getStartPeriod() != null && object.getEndPeriod() != null)
				super.state(TimeUnit.DAYS.convert(Math.abs(object.getEndPeriod().getTime() - object.getStartPeriod().getTime()), TimeUnit.MILLISECONDS) >= 7, "endPeriod", "developer.training-session.form.error.endPeriod");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;
			existing = this.repository.findTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("trainingModule")) {
			TrainingModule published;
			published = this.repository.findTrainingModuleNotPublishedByDeveloperIdAndModuleId(super.getRequest().getPrincipal().getActiveRoleId(), object.getTrainingModule().getId());
			super.state(published == null, "trainingModule", "developer.training-session.form.error.module");
		}

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "optionalLink", "draftMode", "trainingModule", "developer");
		final SelectChoices choices;
		choices = SelectChoices.from(this.repository.findTrainingModulesNotPublishedByDeveloperId(super.getRequest().getPrincipal().getAccountId()), "code", object.getTrainingModule());
		dataset.put("module", choices.getSelected().getKey());
		dataset.put("modules", choices);
		super.getResponse().addData(dataset);
	}
}
