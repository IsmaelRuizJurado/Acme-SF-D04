
package acme.features.authenticated.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.objective.Objective;

@Service
public class AuthenticatedObjectiveShowService extends AbstractService<Authenticated, Objective> {

	@Autowired
	protected AuthenticatedObjectiveRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().isAuthenticated());
	}

	@Override
	public void load() {
		Objective object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findObjectiveById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Objective object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "critical", "startPeriod", "endPeriod", "link");
		dataset.put("priorityTitle", object.getPriority());
		super.getResponse().addData(dataset);
	}
}
