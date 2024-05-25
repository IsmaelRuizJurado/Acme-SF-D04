
package acme.forms;

import java.util.List;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected Integer			numInvoicesWithTaxLessOrEqualThan21;

	protected Integer			numSponsorshipsWithLink;

	List<Object[]>				averageSponsorshipAmount;
	List<Object[]>				desviationSponsorshipAmount;
	List<Object[]>				minSponsorshipAmount;
	List<Object[]>				maxSponsorshipAmount;

	List<Object[]>				averageInvoiceQuantity;
	List<Object[]>				desviationInvoiceQuantity;
	List<Object[]>				minInvoiceQuantity;
	List<Object[]>				maxInvoiceQuantity;

}
