
package acme.features.auditor.audit_records;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.audit_records.AuditRecords;
import acme.entities.audit_records.MarkType;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsUpdateService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	protected AuditorAuditRecordsRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecords object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordsById(id);
		object.setDraftMode(true);
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

		if (!super.getBuffer().getErrors().hasErrors("period")) {
			Date startPeriod = object.getStartPeriod();
			Date endPeriod = object.getEndPeriod();
			if (startPeriod != null && endPeriod != null) {
				long diffInMillis = endPeriod.getTime() - startPeriod.getTime();
				long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
				super.state(diffInHours >= 1, "period", "auditor.audit-records.form.error.period");
			}

		}
	}

	@Override
	public void perform(final AuditRecords object) {
		assert object != null;
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
		Collection<CodeAudits> codeaudits;
		int id = super.getRequest().getPrincipal().getActiveRoleId();
		codeaudits = this.repository.findCodeAuditsByAuditor(id);
		for (final CodeAudits c : codeaudits)
			choices2.add(Integer.toString(c.getId()), c.getCode(), false);

		dataset.put("codeauditslist", choices2);
		super.getResponse().addData(dataset);
	}

}
