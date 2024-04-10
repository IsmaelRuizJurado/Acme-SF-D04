
package acme.features.client.contract;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.contract.Contract;
import acme.entities.progress_logs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		Contract object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getClient().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;
		super.bind(object, "code", "providerName", "customerName", "goals", "budget", "project", "draftMode");
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
		final Collection<Contract> contracts = this.repository.findContractsFromProject(object.getProject().getId());
		super.state(!contracts.isEmpty(), "*", "manager.project.form.error.noContracts");
		if (!contracts.isEmpty()) {
			boolean overBudget;
			double totalBudget = 0.0;
			for (Contract c : contracts)
				totalBudget = totalBudget + c.getBudget().getAmount();
			if (totalBudget < object.getProject().getCost().getAmount())
				overBudget = false;
			else
				overBudget = true;
			super.state(overBudget, "*", "manager.project.form.error.overBudget");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;
			existing = this.repository.findContractByCode(object.getCode());
			final Contract contract2 = object.getCode().equals("") || object.getCode().equals(null) ? null : this.repository.findContractById(object.getId());
			super.state(existing == null || contract2.equals(existing), "code", "client.contract.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(this.auxiliarService.validatePrice(object.getBudget().getAmount(), 0, object.getProject().getCost().getAmount() / 2), "cost", "client.Contract.form.error.budget");

		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(this.auxiliarService.validateCurrency(object.getBudget()), "budget", "manager.project.form.error.cost2");
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode", "project", "client");
		final List<ProgressLogs> progressLogs = this.repository.findProgressLogsByContract(object.getId()).stream().collect(Collectors.toList());
		dataset.put("hasProgressLogs", progressLogs.size() > 0);
		dataset.put("money", this.auxiliarService.changeCurrency(object.getBudget()));
		super.getResponse().addData(dataset);
	}
}
