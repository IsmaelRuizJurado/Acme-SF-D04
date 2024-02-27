
package acme.forms;

import acme.client.data.AbstractForm;
import acme.datatypes.Stats;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected Integer			numInvoicesWithTaxLessOrEqualThan21;

	protected Integer			numSponsorshipsWithLink;

	protected Stats				sponsorshipAmountStats;

	protected Stats				invoicesQuantityStats;

}
