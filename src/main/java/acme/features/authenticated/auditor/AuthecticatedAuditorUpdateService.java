
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.roles.Auditor;

@Service
public class AuthecticatedAuditorUpdateService extends AbstractService<Authenticated, Auditor> {

	@Autowired
	protected AuthenticatedAuditorRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Auditor object;
		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneAuditorByUserAccountId(userAccountId);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Auditor object) {
		assert object != null;
		super.bind(object, "firm", "professionalId", "certifications", "link");
	}

	@Override
	public void validate(final Auditor object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("firm"))
			super.state(this.auxiliarService.validateTextImput(object.getFirm()), "firm", "authenticated.auditor.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("professionalId"))
			super.state(this.auxiliarService.validateTextImput(object.getProfessionalId()), "professionalId", "authenticated.auditor.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("certifications"))
			super.state(this.auxiliarService.validateTextImput(object.getCertifications()), "certifications", "authenticated.auditor.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(this.auxiliarService.validateTextImput(object.getLink()), "link", "authenticated.client.form.error.spam");
	}

	@Override
	public void perform(final Auditor object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Auditor object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "firm", "professionalId", "certifications", "link");
		super.getResponse().addData(dataset);
	}

}
