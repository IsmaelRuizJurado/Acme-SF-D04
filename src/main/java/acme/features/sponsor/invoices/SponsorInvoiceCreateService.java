
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorInvoiceRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Sponsorship object;
		int sponsorshipId;
		sponsorshipId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findSponsorshipById(sponsorshipId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsor().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Invoice object;
		int sponsorshipId;
		Sponsorship sponsorship;
		sponsorshipId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", sponsorshipId);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		object = new Invoice();
		object.setDraftMode(true);
		object.setSponsorship(sponsorship);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, "code", "dueDate", "quantity", "tax", "link");
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setRegistrationTime(actualDate);
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(this.auxiliarService.validatePrice(object.getQuantity(), 0, 1000000), "quantity", "sponsor.invoice.form.error.quantity");
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(this.auxiliarService.validateCurrency(object.getQuantity()), "quantity", "sponsor.invoice.form.error.quantity2");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;
			existing = this.repository.findInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.invoice.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date minimumEndDate;
			super.state(this.auxiliarService.validateDate(object.getDueDate()), "dueDate", "sponsor.sponsorship.form.error.dates");
			if (object.getRegistrationTime() != null) {
				minimumEndDate = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 30, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), minimumEndDate), "dueDate", "sponsor.sponsorship.form.error.end-period");

			}
		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode", "sponsorship");
		super.getResponse().addData(dataset);
	}
}
