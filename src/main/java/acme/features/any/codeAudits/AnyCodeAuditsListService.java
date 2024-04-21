
package acme.features.any.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.code_audits.CodeAudits;

@Service
public class AnyCodeAuditsListService extends AbstractService<Any, CodeAudits> {

	@Autowired
	protected AnyCodeAuditsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudits> objects;
		objects = this.repository.findPublishedCodeAudits();

		super.getBuffer().addData(objects);

	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "auditor");
		super.getResponse().addData(dataset);

	}

}
