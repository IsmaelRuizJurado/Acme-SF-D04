
package acme.features.any.training_module;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {

	@Autowired
	protected AnyTrainingModuleRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		TrainingModule object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		super.getResponse().setAuthorised(!object.getDraftMode());
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		final List<TrainingModule> trainingModules = this.repository.findPublishedTrainingModules().stream().toList();

		for (TrainingModule module : trainingModules) {
			List<TrainingSession> sessions = this.repository.findTrainingSessionsByTrainingModule(module).stream().toList();
			int totalSessionTime = 0;
			for (TrainingSession session : sessions)
				totalSessionTime += (session.getEndPeriod().getTime() - session.getStartPeriod().getTime()) / 1440000;
			module.setEstimatedTotalTime(totalSessionTime);
		}
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationTime", "details", "basicLevel", "updateMoment", "optionalLink", "estimatedTotalTime");
		dataset.put("projectCode", object.getProject().getCode());
		dataset.put("degree", object.getDeveloper().getDegree());
		super.getResponse().addData(dataset);
	}
}
