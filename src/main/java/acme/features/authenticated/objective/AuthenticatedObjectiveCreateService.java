
package acme.features.authenticated.objective;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.objective.Objective;
import acme.entities.objective.PrioType;

@Service
public class AuthenticatedObjectiveCreateService extends AbstractService<Authenticated, Objective> {

	@Autowired
	protected AuthenticatedObjectiveRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Objective object;
		object = new Objective();
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(actualDate);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;
		super.bind(object, "instantiationMoment", "title", "description", "priority", "critical", "startPeriod", "endPeriod", "link");

	}

	@Override
	public void validate(final Objective object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfter(object.getStartPeriod(), object.getInstantiationMoment()), "startPeriod", "administrator.objective.form.error.startPeriod");

		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(object.getEndPeriod().after(object.getStartPeriod()), "endDisplayPeriod", "administrator.objective.form.error.endPeriod");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "slogan", "administrator.objective.form.spam");
		if (!super.getBuffer().getErrors().hasErrors("description"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "description", "administrator.objective.form.spam");
		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "link", "administrator.objective.form.spam");
	}
	@Override
	public void perform(final Objective object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "critical", "startPeriod", "endPeriod", "link");

		final SelectChoices choices;
		choices = SelectChoices.from(PrioType.class, object.getPriority());
		dataset.put("priority", choices.getSelected().getKey());
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);

	}

}
