
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;
		super.bind(object, "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "slogan", "webLink");

	}
	@Override
	public void validate(final Banner object) {
		assert object != null;
	}
	@Override
	public void perform(final Banner object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "slogan", "webLink");

		final Banner banner = this.repository.findBannerById(object.getId());
		final boolean readonly = !(MomentHelper.getCurrentMoment().before(banner.getStartDisplayPeriod()) && MomentHelper.getCurrentMoment().before(banner.getEndDisplayPeriod()) || MomentHelper.getCurrentMoment().after(banner.getEndDisplayPeriod()));
		dataset.put("readonly", readonly);
		final boolean boton = !readonly;
		dataset.put("boton", !boton);
		super.getResponse().addData(dataset);
	}
}
