
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.AuxiliarService;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	protected SponsorSponsorshipRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Sponsorship object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsor().getUserAccount().getId() == userAccountId);
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
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "id", "code", "moment", "startPeriod", "endPeriod", "amount", "type", "email", "link", "draftMode", "sponsor", "project");
		SelectChoices types = SelectChoices.from(SponsorshipType.class, object.getType());
		dataset.put("projectCode", object.getProject().getCode());
		dataset.put("types", types);
		dataset.put("money", this.auxiliarService.changeCurrency(object.getAmount()));
		super.getResponse().addData(dataset);
	}
}
