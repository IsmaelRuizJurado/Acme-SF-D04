
package acme.features.client.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
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
		assert object != null;
		super.bind(object, "code", "providerName", "customerName", "goals", "budget", "draftMode");
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Project project = this.repository.findprojectByCode(object.getProject().getCode());
			object.setProject(project);
		}
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;
			existing = this.repository.findContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(this.auxiliarService.validatePrice(object.getBudget().getAmount(), 0, object.getProject().getCost().getAmount() / 2), "cost", "client.Contract.form.error.budget");

		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(this.auxiliarService.validateCurrency(object.getBudget()), "budget", "manager.project.form.error.cost2");

	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project", "client", "draftMode");
		//final SelectChoices choices;
		//choices = SelectChoices.from(Project.class, object.getProject());
		//dataset.put("type", choices.getSelected().getKey());
		//dataset.put("types", choices);
		super.getResponse().addData(dataset);
	}
}
