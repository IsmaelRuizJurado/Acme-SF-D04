
package acme.features.authenticated.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.notice.Notice;

@Service
public class AuthenticatedNoticeCreateService extends AbstractService<Authenticated, Notice> {

	@Autowired
	protected AuthenticatedNoticeRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().isAuthenticated());
	}

	@Override
	public void load() {
		Notice object;
		object = new Notice();
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(actualDate);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice object) {
		assert object != null;
		super.bind(object, "title", "author", "message", "email", "author", "link", "confirmation");

	}

	@Override
	public void validate(final Notice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "authenticated.notice.form.spam");
		if (!super.getBuffer().getErrors().hasErrors("author"))
			super.state(this.auxiliarService.validateTextImput(object.getAuthor()), "author", "authenticated.notice.form.spam");
		if (!super.getBuffer().getErrors().hasErrors("message"))
			super.state(this.auxiliarService.validateTextImput(object.getMessage()), "message", "authenticated.notice.form.spam");
	}
	@Override
	public void perform(final Notice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "author", "message", "email", "author", "link", "confirmation");
		super.getResponse().addData(dataset);

	}

}
