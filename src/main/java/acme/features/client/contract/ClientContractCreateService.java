
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract object;
		object = new Contract();
		final Client client = this.repository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setClient(client);
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		super.bind(object, "code", "providerName", "instantiationMoment", "customerName", "goals", "budget", "project");
	}

	@Override
	public void validate(final Contract object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;
			existing = this.repository.findContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("providerName"))
			super.state(this.auxiliarService.validateTextImput(object.getProviderName()), "title", "client.contract.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("instantiationMoment"))
			super.state(MomentHelper.isBefore(object.getInstantiationMoment(), MomentHelper.getCurrentMoment()), "instantiationMoment", "client.contract.form.error.moment");

		if (!super.getBuffer().getErrors().hasErrors("customerName"))
			super.state(this.auxiliarService.validateTextImput(object.getCustomerName()), "customerName", "client.contract.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("goals"))
			super.state(this.auxiliarService.validateTextImput(object.getGoals()), "goals", "client.contract.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(this.auxiliarService.validatePrice(object.getBudget().getAmount(), 0, object.getProject().getCost().getAmount() / 2), "budget", "client.contract.form.error.budget");
		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(this.auxiliarService.validateCurrency(object.getBudget()), "budget", "client.contract.form.error.budget2");
	}

	@Override
	public void perform(final Contract object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project", "client", "draftMode");

		final SelectChoices choices = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.findPublishedProjects();
		for (final Project p : projects)
			choices.add(Integer.toString(p.getId()), p.getCode() + " - " + p.getTitle(), false);

		dataset.put("projects", choices);
		super.getResponse().addData(dataset);
	}
}
