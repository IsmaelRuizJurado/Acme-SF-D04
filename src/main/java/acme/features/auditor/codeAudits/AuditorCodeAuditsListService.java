
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsListService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	protected AuditorCodeAuditsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudits> objects;
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		objects = this.repository.findCodeAuditsByAuditorId(userAccountId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link");
		super.getResponse().addData(dataset);
	}
}
