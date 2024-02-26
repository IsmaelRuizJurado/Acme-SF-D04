
package acme.forms;

import acme.client.data.AbstractForm;

public class DeveloperDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				numTrainingModulesWithUpdateMoment;

	private Integer				numTrainingSessionsWithLink;

	private Statistics			statistics;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
