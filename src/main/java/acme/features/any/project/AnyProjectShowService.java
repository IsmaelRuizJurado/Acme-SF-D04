
package acme.features.any.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.project.Project;

@Service
public class AnyProjectShowService extends AbstractService<Any, Project> {

	@Autowired
	protected AnyProjectRepository	repository;

	@Autowired
	protected AuxiliarService		auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Project object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);
		super.getResponse().setAuthorised(!object.isDraftMode());
	}

	@Override
	public void load() {
		Project object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstractt", "cost", "link");
		dataset.put("degree", object.getManager().getDegree());
		dataset.put("money", this.auxiliarService.changeCurrency(object.getCost()));
		super.getResponse().addData(dataset);
	}
}
