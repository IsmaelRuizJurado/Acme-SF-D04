
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
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
		int totalTime = 0;
		double sumTotalTimes = 0.;
		double maxTotalTimes = 0.;
		double minTotalTimes = 0.;
		final Stats auditingTimePeriod = new Stats();

		Collection<CodeAudits> codeaudits = this.repository.findCodeAuditsByAuditorId(auditor.getId());
		for (CodeAudits c : codeaudits) {
			List<AuditRecords> audits;
			audits = this.repository.findAuditRecordsByCodeAudits(c);
			for (AuditRecords a : audits) {
				totalTime += MomentHelper.computeDuration(a.getStartPeriod(), a.getEndPeriod()).getSeconds() / 3600.0;
				sumTotalTimes += totalTime;
				if (totalTime > maxTotalTimes)
					maxTotalTimes = totalTime;
				if (totalTime < minTotalTimes)
					minTotalTimes = totalTime;

			}
		}

		double averageTime = sumTotalTimes / codeaudits.size();
		double devTime = 0.;

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
