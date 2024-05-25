
package acme.features.sponsor.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final SponsorDashboard dashboard = new SponsorDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Sponsor sponsor = this.repository.findOneSponsorByUserAccountId(userAccountId);

		List<Object[]> averageSponsorshipAmount;
		List<Object[]> desviationSponsorshipAmount;
		List<Object[]> minSponsorshipAmount;
		List<Object[]> maxSponsorshipAmount;

		List<Object[]> averageInvoiceQuantity;
		List<Object[]> desviationaverageInvoiceQuantity;
		List<Object[]> minaverageInvoiceQuantity;
		List<Object[]> maxaverageInvoiceQuantity;

		averageSponsorshipAmount = this.repository.findAverageSponsorshipCost(sponsor);
		desviationSponsorshipAmount = this.repository.findMaxSponsorshipCost(sponsor);
		minSponsorshipAmount = this.repository.findMinSponsorshipCost(sponsor);
		maxSponsorshipAmount = this.repository.findLinearDevSponsorshipCost(sponsor);

		averageInvoiceQuantity = this.repository.findAverageInvoiceQuantity(sponsor);
		desviationaverageInvoiceQuantity = this.repository.findMaxInvoiceQuantity(sponsor);
		minaverageInvoiceQuantity = this.repository.findMinInvoiceQuantity(sponsor);
		maxaverageInvoiceQuantity = this.repository.findLinearDevInvoiceQuantity(sponsor);

		dashboard.setAverageSponsorshipAmount(AuxiliarService.removeCommas(averageSponsorshipAmount));
		dashboard.setDesviationSponsorshipAmount(AuxiliarService.removeCommas(desviationSponsorshipAmount));
		dashboard.setMinSponsorshipAmount(AuxiliarService.removeCommas(minSponsorshipAmount));
		dashboard.setMaxSponsorshipAmount(AuxiliarService.removeCommas(maxSponsorshipAmount));

		dashboard.setAverageInvoiceQuantity(AuxiliarService.removeCommas(averageInvoiceQuantity));
		dashboard.setMaxInvoiceQuantity(AuxiliarService.removeCommas(desviationaverageInvoiceQuantity));
		dashboard.setMinInvoiceQuantity(AuxiliarService.removeCommas(minaverageInvoiceQuantity));
		dashboard.setDesviationInvoiceQuantity(AuxiliarService.removeCommas(maxaverageInvoiceQuantity));

		final Integer numInvoicesWithTaxLessOrEqualThan21 = this.repository.findNumOfInvoicesWithTax21less(sponsor).orElse(0);
		final Integer numSponsorshipsWithLink = this.repository.findNumOfSponsorshipsWithLink(sponsor).orElse(0);
		dashboard.setNumInvoicesWithTaxLessOrEqualThan21(numInvoicesWithTaxLessOrEqualThan21);
		dashboard.setNumSponsorshipsWithLink(numSponsorshipsWithLink);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "averageSponsorshipAmount", "desviationSponsorshipAmount", "minSponsorshipAmount", "maxSponsorshipAmount", "averageInvoiceQuantity", "desviationInvoiceQuantity", "minInvoiceQuantity", "maxInvoiceQuantity",
			"numInvoicesWithTaxLessOrEqualThan21", "numSponsorshipsWithLink");

		super.getResponse().addData(dataset);
	}
}
