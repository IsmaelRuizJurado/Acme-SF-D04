
package acme.features.auditor.codeAudits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.code_audits.CodeAudits;
import acme.entities.code_audits.CodeAuditsType;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsCreateService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	protected AuditorCodeAuditsRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudits object;
		object = new CodeAudits();
		final Auditor auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setAuditor(auditor);
		object.setDraftMode(true);
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
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudits existing;
			existing = this.repository.findCodeAuditsByCode(object.getCode());
			super.state(existing == null, "code", "auditor.code-audits.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("correctiveActions"))
			super.state(this.auxiliarService.validateTextImput(object.getCorrectiveActions()), "correctiveActions", "developer.training_module.form.error.spam");

	}

	@Override
	public void perform(final CodeAudits object) {
		assert object != null;
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
