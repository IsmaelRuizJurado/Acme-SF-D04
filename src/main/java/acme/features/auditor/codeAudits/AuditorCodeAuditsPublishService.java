
package acme.features.auditor.codeAudits;

import java.util.Collection;
import java.util.Map;

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
import acme.entities.code_audits.CodeAuditsType;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsPublishService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	protected AuditorCodeAuditsRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


	@Override
	public void authorise() {
		CodeAudits object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCodeAuditsById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getAuditor().getUserAccount().getId() == userAccountId);

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

		if (!super.getBuffer().getErrors().hasErrors("correctiveActions"))
			super.state(this.auxiliarService.validateTextImput(object.getCorrectiveActions()), "correctiveActions", "auditor.code-audits.form.error.spam");

		super.state(object.isDraftMode() == true, "code", "auditor.code-audits.form.error.publish-draftMode");

		//The mark must be, at least, “C”
		final Collection<AuditRecords> audits = this.repository.findAuditRecordsByCodeAudits(object);
		final Map<MarkType, Integer> auditsPerMark;
		auditsPerMark = this.repository.auditRecordsPerMark(audits);
		MarkType highestMarkType = null;
		int highestCount = Integer.MIN_VALUE;
		for (Map.Entry<MarkType, Integer> entry : auditsPerMark.entrySet())
			if (entry.getValue() > highestCount) {
				highestCount = entry.getValue();
				highestMarkType = entry.getKey();
			}

		if (highestMarkType != null && (highestMarkType.equals(MarkType.F) || highestMarkType.equals(MarkType.FMINUS)))
			super.state(false, "code", "auditor.code-audits.form.error.mark");

	}

	@Override
	public void perform(final CodeAudits object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "auditor");
		final SelectChoices choices;
		choices = SelectChoices.from(CodeAuditsType.class, object.getType());
		dataset.put("type", choices.getSelected().getKey());
		dataset.put("types", choices);

		super.getResponse().addData(dataset);

	}
}
