
package acme.features.any.claim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimPublishService extends AbstractService<Any, Claim> {

	@Autowired
	protected AnyClaimRepository	repository;

	@Autowired
	protected AuxiliarService		auxiliarService;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim object;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object = new Claim();
		object.setInstantiationMoment(moment);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "description", "department");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("instantiationMoment"))
			super.state(this.auxiliarService.validateDate(object.getInstantiationMoment()), "instantiationMoment", "any.claim.form.error.instantiationMoment");

		if (!super.getBuffer().getErrors().hasErrors("department"))
			super.state(this.auxiliarService.validateTextImput(object.getDepartment()), "department", "any.claim.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("description"))
			super.state(this.auxiliarService.validateTextImput(object.getDescription()), "description", "any.claim.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("heading"))
			super.state(this.auxiliarService.validateTextImput(object.getHeading()), "heading", "any.claim.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("email"))
			super.state(this.auxiliarService.validateTextImput(object.getEmail()), "email", "any.claim.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(this.auxiliarService.validateTextImput(object.getLink()), "link", "any.claim.form.error.spam");

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Claim object) {
		assert object != null;

		this.repository.save(object);

	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "email", "link");

		super.getResponse().addData(dataset);
	}

}
