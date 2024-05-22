
package acme.features.any.codeAudits;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.audit_records.AuditRecords;
import acme.entities.code_audits.CodeAudits;

@Service
public class AnyCodeAuditsShowService extends AbstractService<Any, CodeAudits> {

	@Autowired
	protected AnyCodeAuditsRepository	repostory;

	@Autowired
	protected AuxiliarService			auxiliarService;


	@Override
	public void authorise() {
		CodeAudits object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repostory.findCodeAuditById(id);
		super.getResponse().setAuthorised(!object.isDraftMode());
	}

	@Override
	public void load() {
		CodeAudits object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repostory.findCodeAuditById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "auditor");
		final List<AuditRecords> auditrecords = this.repostory.findAudiRecordsByCodeAudits(object.getId());
		dataset.put("hasAuditRecords", !auditrecords.isEmpty());
		super.getResponse().addData(dataset);
	}

}
