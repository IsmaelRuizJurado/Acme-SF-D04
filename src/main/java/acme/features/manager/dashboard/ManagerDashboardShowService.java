
package acme.features.manager.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.user_story.Priority;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Integer managerId = super.getRequest().getPrincipal().getActiveRoleId();
		ManagerDashboard managerDashboard;

		Integer mustNumber;

		Integer shouldNumber;

		Integer couldNumber;

		Integer wontNumber;

		List<Object[]> averageUsCost;

		List<Object[]> desviationUsCost;

		List<Object[]> minUsCost;

		List<Object[]> maxUsCost;

		List<Object[]> averageProjectCost;

		List<Object[]> deviationProjectCost;

		List<Object[]> minProjectCost;

		List<Object[]> maxProjectCost;

		managerDashboard = new ManagerDashboard();

		averageUsCost = this.repository.averageEstimationUserStories(managerId);
		desviationUsCost = this.repository.deviationEstimationUserStories(managerId);
		minUsCost = this.repository.minEstimationUserStories(managerId);
		maxUsCost = this.repository.maxEstimationUserStories(managerId);
		mustNumber = this.repository.countUSbyPriority(Priority.MUST, managerId);
		shouldNumber = this.repository.countUSbyPriority(Priority.SHOULD, managerId);
		couldNumber = this.repository.countUSbyPriority(Priority.COULD, managerId);
		wontNumber = this.repository.countUSbyPriority(Priority.WONT, managerId);

		averageProjectCost = this.repository.averageProjectCost(managerId);
		deviationProjectCost = this.repository.deviationProjectCost(managerId);
		minProjectCost = this.repository.minProjectCost(managerId);
		maxProjectCost = this.repository.maxProjectCost(managerId);

		managerDashboard.setMustNumber(mustNumber);
		managerDashboard.setShouldNumber(shouldNumber);
		managerDashboard.setCouldNumber(couldNumber);
		managerDashboard.setWontNumber(wontNumber);

		managerDashboard.setAverageUsCost(AuxiliarService.removeCommas(averageUsCost));
		managerDashboard.setDesviationUsCost(AuxiliarService.removeCommas(desviationUsCost));
		managerDashboard.setMinUsCost(AuxiliarService.removeCommas(minUsCost));
		managerDashboard.setMaxUsCost(AuxiliarService.removeCommas(maxUsCost));

		managerDashboard.setAverageProjectCost(AuxiliarService.removeCommas(averageProjectCost));
		managerDashboard.setDeviationProjectCost(AuxiliarService.removeCommas(deviationProjectCost));
		managerDashboard.setMaxProjectCost(AuxiliarService.removeCommas(minProjectCost));
		managerDashboard.setMinProjectCost(AuxiliarService.removeCommas(maxProjectCost));

		super.getBuffer().addData(managerDashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"mustNumber", "shouldNumber", "couldNumber", "wontNumber", "averageUsCost", // 
			"desviationUsCost", "minUsCost", //
			"maxUsCost", "averageProjectCost", //
			"deviationProjectCost", "minProjectCost", "maxProjectCost");

		super.getResponse().addData(dataset);
	}

}
