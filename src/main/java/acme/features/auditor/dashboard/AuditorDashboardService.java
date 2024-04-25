
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Stats;
import acme.entities.audit_records.AuditRecords;
import acme.entities.code_audits.CodeAudits;
import acme.entities.code_audits.CodeAuditsType;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardService extends AbstractService<Auditor, AuditorDashboard> {

	@Autowired
	protected AuditorDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final AuditorDashboard dashboard = new AuditorDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Auditor auditor = this.repository.findOneAuditorByUserAccountId(userAccountId);

		//NumOfAuditRecordsByTypes
		final Map<String, Integer> codeAuditsPerTypes = new HashMap<String, Integer>();
		final Integer staticCodeAudits = this.repository.findCodeAuditByType(auditor.getId(), CodeAuditsType.STATIC).orElse(0);
		final Integer dinamicCodeAudits = this.repository.findCodeAuditByType(auditor.getId(), CodeAuditsType.DINAMIC).orElse(0);
		codeAuditsPerTypes.put("STATIC", staticCodeAudits);
		codeAuditsPerTypes.put("DINAMIC", dinamicCodeAudits);
		dashboard.setTotalNumAudits(codeAuditsPerTypes);

		//AuditRecordsStats
		final Stats numAuditRecord = new Stats();
		final double averageAuditRecord = this.repository.findAverageNumOfAuditRecords(auditor.getId()).orElse(0.0);
		final double maxAuditRecord = this.repository.findMaxNumOfAuditRecords(auditor.getId()).orElse(0.0);
		final double minAuditRecords = this.repository.findMinNumOfAuditRecords(auditor.getId()).orElse(0.0);
		//final double devAuditRecords = this.repository.findDevNumOfAuditRecords(auditor.getId()).orElse(0.0);
		numAuditRecord.setAverage(averageAuditRecord);
		numAuditRecord.setMaximum(maxAuditRecord);
		numAuditRecord.setMinimum(minAuditRecords);
		//numAuditRecord.setDeviation(devAuditRecords);
		dashboard.setNumAuditRecord(numAuditRecord);

		//AuditRecordsTimePeriod

		final Collection<CodeAudits> codeaudits = this.repository.findCodeAuditsByAuditorId(auditor.getId());
		double sumTotalTimes;
		double maxTotalTimes;
		double minTotalTimes;
		double averageTime;
		double devTime;
		if (codeaudits.isEmpty()) {
			sumTotalTimes = 0.;
			maxTotalTimes = 0.;
			minTotalTimes = 0.;
			averageTime = 0.;
			devTime = 0.;

		} else {
			sumTotalTimes = 0.;
			maxTotalTimes = Double.NEGATIVE_INFINITY;
			minTotalTimes = Double.POSITIVE_INFINITY;

			for (CodeAudits c : codeaudits) {
				List<AuditRecords> audits;
				audits = this.repository.findAuditRecordsByCodeAudits(c);
				int totalTime = 0;
				for (AuditRecords a : audits)
					totalTime += (a.getEndPeriod().getTime() - a.getStartPeriod().getTime()) / 1440000;
				sumTotalTimes += totalTime;
				if (totalTime > maxTotalTimes)
					maxTotalTimes = totalTime;
				if (totalTime < minTotalTimes)
					minTotalTimes = totalTime;
			}

			averageTime = sumTotalTimes / codeaudits.size();
			devTime = 0.;
		}

		final Stats auditingTimePeriod = new Stats();
		auditingTimePeriod.setAverage(averageTime);
		auditingTimePeriod.setMaximum(maxTotalTimes);
		auditingTimePeriod.setMinimum(minTotalTimes);
		auditingTimePeriod.setDeviation(devTime);
		dashboard.setAuditingTimePeriod(auditingTimePeriod);

		super.getBuffer().addData(dashboard);

	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "totalNumAudits", "numAuditRecord", "auditingTimePeriod");

		super.getResponse().addData(dataset);
	}

}
