
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.datatypes.Stats;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//Total number of code audits for “Static” and “Dynamic” types
	private Map<String, Integer>	totalNumAudits;

	//Average, deviation, minimum, and maximum number of audit records in their audits
	private Stats					numAuditRecord;

	//Average, deviation, minimum, and maximum time of the period lengths in their audit records
	private Stats					auditingTimePeriod;

}
