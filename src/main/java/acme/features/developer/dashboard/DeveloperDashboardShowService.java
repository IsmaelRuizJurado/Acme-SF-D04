
package acme.features.developer.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Stats;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final DeveloperDashboard dashboard = new DeveloperDashboard();

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final Developer developer = this.repository.findOneDeveloperByUserAccountId(userAccountId);

		//trainingModuleTimeStats
		final List<TrainingModule> trainingModules = this.repository.findTrainingModulesByDeveloperId(userAccountId).stream().toList();
		double sumTotalTimes = 0.;
		double maxTotalTimes = Double.MIN_VALUE;
		double minTotalTimes = Double.MAX_VALUE;

		for (TrainingModule module : trainingModules) {
			List<TrainingSession> sessions = this.repository.findTrainingSessionsByTrainingModule(module).stream().toList();
			int totalSessionTime = 0;
			for (TrainingSession session : sessions)
				totalSessionTime += (session.getEndPeriod().getTime() - session.getStartPeriod().getTime()) / 60000;
			module.setEstimatedTotalTime(totalSessionTime);
			int totalTime = module.getEstimatedTotalTime();
			sumTotalTimes += totalTime;
			if (totalTime > maxTotalTimes)
				maxTotalTimes = totalTime;
			if (totalTime < minTotalTimes)
				minTotalTimes = totalTime;
		}

		double averageTrainingModuleTime = sumTotalTimes / trainingModules.size();

		double devTrainingModuleTime = Math.sqrt(trainingModules.stream().mapToDouble(module -> Math.pow(module.getEstimatedTotalTime() - averageTrainingModuleTime, 2)).sum() / trainingModules.size());

		final Stats trainingModuleTimeStats = new Stats();
		trainingModuleTimeStats.setAverage(averageTrainingModuleTime);
		trainingModuleTimeStats.setMinimum(minTotalTimes);
		trainingModuleTimeStats.setMaximum(maxTotalTimes);
		trainingModuleTimeStats.setDeviation(devTrainingModuleTime);
		dashboard.setTrainingModuleTimeStats(trainingModuleTimeStats);

		//numOfTrainingModulesWithUpdateMoment
		final Integer numTrainingModulesWithUpdateMoment = this.repository.findNumOfTrainingModulesWithUpdateMoment(developer).orElse(0);
		dashboard.setNumTrainingModulesWithUpdateMoment(numTrainingModulesWithUpdateMoment);

		//numOfTrainingSessionsWithLink
		final Integer numTrainingSessionsWithLink = this.repository.findNumOfTrainingSessionsWithLink(developer).orElse(0);
		dashboard.setNumTrainingSessionsWithLink(numTrainingSessionsWithLink);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "numTrainingModulesWithUpdateMoment", "numTrainingSessionsWithLink", "trainingModuleTimeStats");
		super.getResponse().addData(dataset);

	}

}
