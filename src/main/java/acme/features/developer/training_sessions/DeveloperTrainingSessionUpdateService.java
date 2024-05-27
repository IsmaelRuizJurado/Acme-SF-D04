
package acme.features.developer.training_sessions;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionRepository	repository;

	@Autowired
	protected AuxiliarService						auxiliarService;


	@Override
	public void authorise() {
		TrainingSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingSessionById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingSessionById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;
		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "optionalLink");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			super.state(this.auxiliarService.validateDate(object.getStartPeriod()), "startPeriod", "developer.training-session.form.error.startPeriod");
			super.state(object.getStartPeriod().after(object.getTrainingModule().getCreationTime()), "startPeriod", "developer.training-session.form.error.startPeriod");
		}
		if (!super.getBuffer().getErrors().hasErrors("location"))
			super.state(this.auxiliarService.validateTextImput(object.getLocation()), "location", "developer.training-session.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("instructor"))
			super.state(this.auxiliarService.validateTextImput(object.getInstructor()), "instructor", "developer.training-session.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			super.state(this.auxiliarService.validateDate(object.getEndPeriod()), "endPeriod", "developer.training-session.form.error.endPeriod");
			super.state(TimeUnit.DAYS.convert(Math.abs(object.getEndPeriod().getTime() - object.getStartPeriod().getTime()), TimeUnit.MILLISECONDS) >= 7, "endPeriod", "developer.training-session.form.error.endPeriod");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;
			existing = this.repository.findTrainingSessionByCode(object.getCode());
			final TrainingSession ts2 = object.getCode().equals("") || object.getCode() == null ? null : this.repository.findTrainingSessionById(object.getId());
			super.state(existing == null || ts2.equals(existing), "code", "developer.training_session.form.error.code");
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
		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "optionalLink", "draftMode");
		dataset.put("trainingModuleCode", object.getTrainingModule().getCode());
		super.getResponse().addData(dataset);
	}

}
