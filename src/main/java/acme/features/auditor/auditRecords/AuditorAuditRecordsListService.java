
package acme.features.auditor.auditRecords;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecords;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsListService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	protected AuditorAuditRecordsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<AuditRecords> objects;
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		objects = this.repository.findAuditRecordByAuditorId(userAccountId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecords object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "id", "code", "startPeriod", "endPeriod", "mark", "link");
		super.getResponse().addData(dataset);

	}
}
