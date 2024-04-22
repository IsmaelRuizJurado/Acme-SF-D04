
package acme.features.auditor.audit_records;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecords;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsDeleteService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	protected AuditorAuditRecordsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecords object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecords object) {
		assert object != null;
		super.bind(object, "id", "code", "startPeriod", "endPeriod", "mark", "link");

	}

	@Override
	public void validate(final AuditRecords object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "auditor.audit-records.form.error.draftMode");
	}

	@Override
	public void perform(final AuditRecords object) {
		assert object != null;
		this.repository.delete(object);

	}

	@Override
	public void unbind(final AuditRecords object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "id", "code", "startPeriod", "endPeriod", "mark", "link");
		super.getResponse().addData(dataset);
	}

}
