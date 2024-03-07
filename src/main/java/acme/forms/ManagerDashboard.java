
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.datatypes.Stats;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Map<String, Integer>	totalPriorities;

	private Stats					userStoriesCostStats;

	private Stats					projectCostStats;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
