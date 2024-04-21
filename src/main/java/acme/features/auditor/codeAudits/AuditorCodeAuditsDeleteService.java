
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecords;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsDeleteService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	protected AuditorCodeAuditsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudits object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCodeAuditsById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudits object) {
		assert object != null;
		super.bind(object, "code", "executionDate", "type", "correctiveActions", "link");
	}

	@Override
	public void validate(final CodeAudits object) {
		assert object != null;
	}

	@Override
	public void perform(final CodeAudits object) {
		assert object != null;
		final Collection<AuditRecords> auditRecords = this.repository.findAuditRecordsByCodeAudits(object);
		for (final AuditRecords ar : auditRecords)
			this.repository.delete(ar);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link");
		super.getResponse().addData(dataset);
	}

}
