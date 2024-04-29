
package acme.features.developer.training_sessions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListAllService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		final Developer developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());
		objects = this.repository.findTrainingSessionsByDeveloper(developer);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "location", "instructor");
		dataset.put("module", object.getTrainingModule().getCode());
		super.getResponse().addGlobal("showCreate", false);
		super.getResponse().addData(dataset);
	}

}
