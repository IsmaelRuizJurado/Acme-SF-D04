
package acme.features.developer.training_sessions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		TrainingModule object;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findTrainingModuleById(masterId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		TrainingModule object;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findTrainingModuleById(masterId);
		objects = this.repository.findTrainingSessionsByTrainingModule(object);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "location", "instructor");
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", masterId);
		dataset.put("masterId", masterId);
		final TrainingModule tm = this.repository.findTrainingModuleById(masterId);
		final boolean showCreate = tm.getDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<TrainingSession> object) {
		assert object != null;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", masterId);
		final TrainingModule tm = this.repository.findTrainingModuleById(masterId);
		final boolean showCreate = tm.getDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
