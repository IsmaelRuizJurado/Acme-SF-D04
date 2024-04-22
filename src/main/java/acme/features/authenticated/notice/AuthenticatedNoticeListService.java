
package acme.features.authenticated.notice;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class AuthenticatedNoticeListService extends AbstractService<Authenticated, Notice> {

	@Autowired
	protected AuthenticatedNoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().isAuthenticated());
	}

	@Override
	public void load() {
		Collection<Notice> objects;
		objects = this.repository.findAllNoticesNotOlderThan1Month().stream().filter(x -> x.getInstantiationMoment().before(MomentHelper.deltaFromMoment(x.getInstantiationMoment(), 30, ChronoUnit.DAYS))).collect(Collectors.toList());
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "message", "link");
		super.getResponse().addData(dataset);
	}
}
