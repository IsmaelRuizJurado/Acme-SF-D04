
package acme.features.any.training_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.training_module.TrainingModule;

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
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationTime", "details", "basicLevel", "updateMoment", "optionalLink");
		dataset.put("degree", object.getDeveloper().getDegree());
		super.getResponse().addData(dataset);
	}
}
