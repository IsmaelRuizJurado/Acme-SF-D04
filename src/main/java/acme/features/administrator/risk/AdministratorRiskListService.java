
package acme.features.administrator.risk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdministratorRiskListService extends AbstractService<Administrator, Risk> {

	@Autowired
	protected AdministratorRiskRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Risk> objects;
		objects = this.repository.findAllRisks();
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "link");
		super.getResponse().addData(dataset);
	}

}
