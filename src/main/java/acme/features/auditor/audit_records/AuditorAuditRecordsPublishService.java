
package acme.features.auditor.audit_records;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.audit_records.AuditRecords;
import acme.entities.audit_records.MarkType;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsPublishService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	protected AuditorAuditRecordsRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


	@Override
	public void authorise() {
		AuditRecords object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getCodeAudits().getAuditor().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		AuditRecords object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordsById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecords object) {
		assert object != null;
		super.bind(object, "id", "code", "startPeriod", "endPeriod", "mark", "link");

	}

	@Override
	public void validate(final AuditRecords object) {
		if (object == null)
			throw new IllegalArgumentException("No Audit Records found");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecords existing;
			existing = this.repository.findAuditRecordsByCode(object.getCode());
			final AuditRecords auditRecord2 = object.getCode().equals("") || object.getCode() == null ? null : this.repository.findAuditRecordById(object.getId());
			super.state(existing == null || auditRecord2.equals(existing), "code", "auditor.audit-records.form.error.code");
		}
		super.state(object.getDraftMode() == true, "code", "auditor.audit-records.form.error.publish-draftMode");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecords existing;
			existing = this.repository.findAuditRecordsByCode(object.getCode());
			final AuditRecords auditRecord2 = object.getCode().equals("") || object.getCode() == null ? null : this.repository.findAuditRecordById(object.getId());
			super.state(existing == null || auditRecord2.equals(existing), "code", "auditor.audit-records.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("period")) {
			Date startPeriod = object.getStartPeriod();
			Date endPeriod = object.getEndPeriod();

			if (startPeriod != null && endPeriod != null) {
				long diffInMillis = endPeriod.getTime() - startPeriod.getTime();
				long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
				super.state(diffInHours >= 1, "endPeriod", "auditor.audit-records.form.error.period");
			}
		}
	}

	@Override
	public void perform(final AuditRecords object) {
		object.setDraftMode(false);
		this.repository.save(object);

	}

	@Override
	public void unbind(final AuditRecords object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "mark", "link", "codeAudits");

		final SelectChoices choices;
		choices = SelectChoices.from(MarkType.class, object.getMark());
		dataset.put("mark", choices.getSelected().getKey());
		dataset.put("marks", choices);

		final SelectChoices choices2 = new SelectChoices();
		CodeAudits codeaudits;
		codeaudits = object.getCodeAudits();
		choices2.add(Integer.toString(codeaudits.getId()), codeaudits.getCode(), false);

		dataset.put("codeauditslist", choices2);
		super.getResponse().addData(dataset);
	}

}
