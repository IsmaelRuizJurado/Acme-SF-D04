
package acme.features.administrator.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AdministratorDashboard;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final AdministratorDashboard dashboard = new AdministratorDashboard();

		//totalPrincipalsWithEachRole
		final Map<String, Integer> numPrincipalsEachRole = new HashMap<String, Integer>();

		final int findNumAdministrators = this.repository.numAdministrators();
		final int findNumManagers = this.repository.numManagers();
		final int findNumClients = this.repository.numClients();
		final int findNumSponsors = this.repository.numSponsor();
		final int findNumProvider = this.repository.numProvider();
		final int findNumDevelopers = this.repository.numDeveloper();
		final int findNumConsumer = this.repository.numConsumer();
		numPrincipalsEachRole.put("numAdmins", findNumAdministrators);
		numPrincipalsEachRole.put("numManagers", findNumManagers);
		numPrincipalsEachRole.put("numClients", findNumClients);
		numPrincipalsEachRole.put("numSponsors", findNumSponsors);
		numPrincipalsEachRole.put("numProviders", findNumProvider);
		numPrincipalsEachRole.put("numDevs", findNumDevelopers);
		numPrincipalsEachRole.put("numConsumers", findNumConsumer);
		dashboard.setTotalPrincipalsWithEachRole(numPrincipalsEachRole);

		//ratioNoticesWithEmailAndLink
		/*
		 * final double ratioNoticesWithEmailAndLink;
		 * 
		 * Collection<Notice> notices = this.repository.Notices();
		 * int noticesEmailLink = 0;
		 * for (Notice n : notices)
		 * if (n.getEmail() == null && n.getLink() == null)
		 * noticesEmailLink = noticesEmailLink + 1;
		 * ratioNoticesWithEmailAndLink = noticesEmailLink == 0 ? 0 : notices.size() / noticesEmailLink;
		 * dashboard.setRatioNoticesWithEmailAndLink(ratioNoticesWithEmailAndLink);
		 * 
		 * //ratioCriticalNonCriticalObjectives
		 * final Map<String, Double> criticalNonCriticalObjectives = new HashMap<String, Double>();
		 * 
		 * final int critObjectives = this.repository.criticalObjectives();
		 * final int numObjectives = this.repository.numObjectives();
		 * final double critRatio = critObjectives / numObjectives;
		 * criticalNonCriticalObjectives.put("criticalRatio", critRatio);
		 * criticalNonCriticalObjectives.put("nonCriticalRatio", 1 - critRatio);
		 * dashboard.setRatioCriticalNonCriticalObjectives(criticalNonCriticalObjectives);
		 * 
		 * //riskValueStats
		 * Stats riskValueStats = new Stats();
		 * Collection<Risk> risks = this.repository.risks();
		 * double sum = 0;
		 * for (Risk r : risks)
		 * sum = sum + r.value();
		 * 
		 * final double avgValue = sum / risks.size();
		 * double maxValue = 0;
		 * for (Risk r : risks)
		 * if (maxValue < r.value())
		 * maxValue = r.value();
		 * 
		 * double minValue = maxValue;
		 * for (Risk r : risks)
		 * if (minValue > r.value())
		 * minValue = r.value();
		 * 
		 * final double dev;
		 * double squaredSum = 0;
		 * for (Risk r : risks)
		 * squaredSum = squaredSum + Math.pow(r.value() - avgValue, 2);
		 * dev = Math.sqrt(squaredSum / risks.size());
		 * 
		 * riskValueStats.setAverage(avgValue);
		 * riskValueStats.setMaximum(maxValue);
		 * riskValueStats.setMinimum(minValue);
		 * riskValueStats.setDeviation(dev);
		 * 
		 * //numClaimsLastTenWeeksStats
		 * Collection<Claim> numClaims = this.repository.Claims();
		 * //Add Data
		 * 
		 */
		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalPrincipalsWithEachRole", "ratioNoticesWithEmailAndLink", "ratioCriticalNonCriticalObjectives", "riskValueStats", "numClaimsLastTenWeeksStats");

		super.getResponse().addData(dataset);
	}

}
