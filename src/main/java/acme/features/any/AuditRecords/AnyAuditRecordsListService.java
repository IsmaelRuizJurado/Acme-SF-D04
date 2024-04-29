
package acme.features.any.AuditRecords;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecords;

@Service
public class AnyAuditRecordsListService extends AbstractService<Any, AuditRecords> {

	@Autowired
	protected AnyAuditRecordsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<AuditRecords> objects;
		int masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findAuditRecordsByCodeAudits(masterId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecords object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "mark", "link");
		super.getResponse().addData(dataset);

	}

}
