
package acme.forms;

import java.util.List;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						mustNumber;

	Integer						shouldNumber;

	Integer						couldNumber;

	Integer						wontNumber;

	List<Object[]>				averageUsCost;

	List<Object[]>				desviationUsCost;

	List<Object[]>				minUsCost;

	List<Object[]>				maxUsCost;

	List<Object[]>				averageProjectCost;
	List<Object[]>				deviationProjectCost;
	List<Object[]>				minProjectCost;
	List<Object[]>				maxProjectCost;

}
