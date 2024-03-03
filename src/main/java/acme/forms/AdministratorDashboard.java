
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.datatypes.Stats;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Map<String, Integer>	totalPrincipalsWithEachRole;

	private double					ratioNoticesWithEmailAndLink;

	private Map<String, Double>		ratioCriticalNonCriticalObjectives;

	private Stats					riskValueStats;

	private Stats					numClaimsLastTenWeeksStats;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
