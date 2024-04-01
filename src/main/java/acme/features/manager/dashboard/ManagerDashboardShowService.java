
package acme.features.manager.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Stats;
import acme.entities.user_story.Priority;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final ManagerDashboard dashboard = new ManagerDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Manager manager = this.repository.findOneManagerByUserAccountId(userAccountId);

		//projectCostStats
		final double averageProjectCost = this.repository.findAverageProjectCost(manager).orElse(0.0);
		final double maxProjectCost = this.repository.findMaxProjectCost(manager).orElse(0.0);
		final double minProjectCost = this.repository.findMinProjectCost(manager).orElse(0.0);
		final double devProjectCost = this.repository.findLinearDevProjectCost(manager).orElse(0.0);
		final Stats projectCostStats = new Stats();
		projectCostStats.setAverage(averageProjectCost);
		projectCostStats.setMinimum(minProjectCost);
		projectCostStats.setMaximum(maxProjectCost);
		projectCostStats.setDeviation(devProjectCost);
		dashboard.setProjectCostStats(projectCostStats);

		//userStoryCostStats
		final double averageUserStoryCost = this.repository.findAverageUserStoryCost(manager).orElse(0.0);
		final double maxUserStoryCost = this.repository.findMaxUserStoryCost(manager).orElse(0.0);
		final double minUserStoryCost = this.repository.findMinUserStoryCost(manager).orElse(0.0);
		final double devUserStoryCost = this.repository.findLinearDevUserStoryCost(manager).orElse(0.0);
		final Stats userStoryCostStats = new Stats();
		userStoryCostStats.setAverage(averageUserStoryCost);
		userStoryCostStats.setMinimum(minUserStoryCost);
		userStoryCostStats.setMaximum(maxUserStoryCost);
		userStoryCostStats.setDeviation(devUserStoryCost);
		dashboard.setUserStoriesCostStats(userStoryCostStats);

		//numOfUserStoriesByPriority
		final Map<String, Integer> UserStoriesByPriority = new HashMap<String, Integer>();
		final Integer mustUserStories = this.repository.findNumOfUserStoriesByPriority(manager, Priority.MUST).orElse(0);
		final Integer shouldUserStories = this.repository.findNumOfUserStoriesByPriority(manager, Priority.SHOULD).orElse(0);
		final Integer couldUserStories = this.repository.findNumOfUserStoriesByPriority(manager, Priority.COULD).orElse(0);
		final Integer wontUserStories = this.repository.findNumOfUserStoriesByPriority(manager, Priority.WONT).orElse(0);
		UserStoriesByPriority.put("MUST", mustUserStories);
		UserStoriesByPriority.put("SHOULD", shouldUserStories);
		UserStoriesByPriority.put("COULD", couldUserStories);
		UserStoriesByPriority.put("WONT", wontUserStories);
		dashboard.setTotalUserStoriesByPriority(UserStoriesByPriority);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "projectCostStats", "userStoriesCostStats", "totalUserStoriesByPriority");

		super.getResponse().addData(dataset);
	}
}
