
package acme.forms;

import acme.client.data.AbstractForm;
import acme.datatypes.Stats;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				numTrainingModulesWithUpdateMoment;

	private Integer				numTrainingSessionsWithLink;

	private Stats				trainingModuleTimeStats;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
