
package acme.features.authenticated.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.roles.Client;
import acme.roles.Type;

@Service
public class AuthenticatedClientUpdateService extends AbstractService<Authenticated, Client> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedClientRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Client.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Client object;
		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneClientByUserAccountId(userAccountId);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Client object) {
		assert object != null;

		super.bind(object, "identification", "companyName", "type", "email", "link");
	}

	@Override
	public void validate(final Client object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("identification"))
			super.state(this.auxiliarService.validateTextImput(object.getIdentification()), "identification", "authenticated.client.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("identification")) {
			Client existing;
			existing = this.repository.findClientByIdentification(object.getIdentification());
			Client client2;
			if (object.getIdentification() == "" || object.getIdentification() == null)
				client2 = null;
			else
				client2 = this.repository.findClientByIdentification(object.getIdentification());
			super.state(existing == null || client2.getId() != existing.getId(), "identification", "authenticated.client.form.error.identification");
		}
		if (!super.getBuffer().getErrors().hasErrors("companyName"))
			super.state(this.auxiliarService.validateTextImput(object.getCompanyName()), "companyName", "authenticated.client.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("type"))
			super.state(this.auxiliarService.validateTextImput(object.getType().toString()), "type", "authenticated.client.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("email"))
			super.state(this.auxiliarService.validateTextImput(object.getEmail()), "email", "authenticated.client.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(this.auxiliarService.validateTextImput(object.getLink()), "link", "authenticated.client.form.error.spam");
	}

	@Override
	public void perform(final Client object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Client object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "identification", "companyName", "type", "email", "link");
		final SelectChoices choices;
		choices = SelectChoices.from(Type.class, object.getType());
		dataset.put("type", choices.getSelected().getKey());
		dataset.put("types", choices);
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("PUT"))
			PrincipalHelper.handleUpdate();
	}
}
