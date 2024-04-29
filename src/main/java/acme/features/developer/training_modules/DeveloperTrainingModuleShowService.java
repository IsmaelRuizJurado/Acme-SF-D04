
package acme.features.developer.training_modules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.training_module.Level;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;


	@Override
	public void authorise() {
		TrainingModule object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		final List<TrainingModule> trainingModules = this.repository.findTrainingModulesByDeveloperId(userAccountId).stream().toList();

		for (TrainingModule module : trainingModules) {
			List<TrainingSession> sessions = this.repository.findTrainingSessionsByTrainingModule(module).stream().toList();
			int totalSessionTime = 0;
			for (TrainingSession session : sessions)
				totalSessionTime += (session.getEndPeriod().getTime() - session.getStartPeriod().getTime()) / 1440000;
			module.setEstimatedTotalTime(totalSessionTime);

			super.getBuffer().addData(object);
		}
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "id", "code", "creationTime", "details", "basicLevel", "updateMoment", "optionalLink", "estimatedTotalTime", "draftMode", "developer");
		final SelectChoices choices;
		choices = SelectChoices.from(Level.class, object.getBasicLevel());
		dataset.put("basicLevel", choices.getSelected().getKey());
		dataset.put("levels", choices);
		final List<TrainingSession> trainingSessions = this.repository.findTrainingSessionsByTrainingModule(object).stream().toList();
		dataset.put("hasTrainingSessions", !trainingSessions.isEmpty());
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}

}
