
package acme.features.client.contract;

import java.util.List;

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
public class ClientContractShowService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Contract object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getClient().getUserAccount().getId() == userAccountId);
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
	public void unbind(final Contract object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		Dataset dataset;
		dataset = super.unbind(object, "id", "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode", "project", "client");
		final List<ProgressLogs> progressLogs = (List<ProgressLogs>) this.repository.findProgressLogsByContract(object.getId());
		dataset.put("hasProgressLogs", !progressLogs.isEmpty());
		dataset.put("projectTitle", object.getProject().getCode());
		dataset.put("money", this.auxiliarService.changeCurrency(object.getBudget()));
		super.getResponse().addData(dataset);
	}
}
