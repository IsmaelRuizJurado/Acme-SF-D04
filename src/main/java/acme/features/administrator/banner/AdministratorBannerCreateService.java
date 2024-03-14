
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		object = new Banner();
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(actualDate);
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
		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
			super.state(MomentHelper.isAfter(object.getStartDisplayPeriod(), object.getInstantiationMoment()), "startDisplayPeriod", "administrator.banner.form.error.startDisplayPeriod");

		if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod") && !super.getBuffer().getErrors().hasErrors("startDisplayPeriod")) {
			Date maximumPeriod;
			maximumPeriod = MomentHelper.deltaFromMoment(object.getStartDisplayPeriod(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndDisplayPeriod(), maximumPeriod) && object.getEndDisplayPeriod().after(object.getStartDisplayPeriod()), "endDisplayPeriod", "administrator.banner.form.error.endDisplayPeriod");
		}
		if (!super.getBuffer().getErrors().hasErrors("slogan"))
			super.state(this.auxiliarService.validateTextImput(object.getSlogan()), "slogan", "administrator.banner.form.spam");
	}
	@Override
	public void perform(final Banner object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "slogan", "webLink");
		super.getResponse().addData(dataset);

	}
}
