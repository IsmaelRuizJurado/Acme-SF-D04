
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipUpdateService extends AbstractService<Sponsor, Sponsorship> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorSponsorshipRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		Sponsorship object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsor().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		Sponsorship object2;
		int id;

		id = super.getRequest().getData("id", int.class);
		object2 = this.repository.findSponsorshipById(id);
		object.setProject(object2.getProject());
		object.setSponsor(object2.getSponsor());
		super.bind(object, "code", "amount", "startPeriod", "endPeriod", "type", "email", "link");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("amount"))
			super.state(this.auxiliarService.validatePrice(object.getAmount(), 0, 1000000), "amount", "sponsor.sponsorship.form.error.amount");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;
			existing = this.repository.findSponsorshipByCode(object.getCode());
			final Sponsorship sponsorship2 = object.getCode().equals("") || object.getCode().equals(null) ? null : this.repository.findSponsorshipById(object.getId());
			super.state(existing == null || sponsorship2.equals(existing), "code", "sponsor.sponsorship.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("amount"))
			super.state(this.auxiliarService.validateCurrency(object.getAmount()), "amount", "sponsor.sponsorship.form.error.amount2");
		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), MomentHelper.getCurrentMoment()), "startPeriod", "sponsor.sponsorship.form.error.start-period");
			super.state(this.auxiliarService.validateDate(object.getStartPeriod()), "startPeriod", "sponsor.sponsorship.form.error.dates");
		}
		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date minimumEndDate;
			super.state(this.auxiliarService.validateDate(object.getEndPeriod()), "endPeriod", "sponsor.sponsorship.form.error.dates");
			if (object.getStartPeriod() != null) {
				minimumEndDate = MomentHelper.deltaFromMoment(object.getStartPeriod(), 30, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minimumEndDate), "endPeriod", "sponsor.sponsorship.form.error.end-period");
			}
		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		object.setMoment(MomentHelper.getCurrentMoment());
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startPeriod", "endPeriod", "type", "amount", "link", "email", "draftMode", "project");
		SelectChoices types = SelectChoices.from(SponsorshipType.class, object.getType());
		final Sponsorship sponsorship = this.repository.findSponsorshipById(object.getId());
		final boolean readonly = !(MomentHelper.getCurrentMoment().before(sponsorship.getStartPeriod()) && MomentHelper.getCurrentMoment().before(sponsorship.getEndPeriod()) || MomentHelper.getCurrentMoment().after(sponsorship.getEndPeriod()));
		dataset.put("readonly", readonly);
		dataset.put("projectCode", object.getProject().getCode());
		dataset.put("types", types);
		dataset.put("money", this.auxiliarService.changeCurrency(object.getAmount()));
		super.getResponse().addData(dataset);
	}
}
