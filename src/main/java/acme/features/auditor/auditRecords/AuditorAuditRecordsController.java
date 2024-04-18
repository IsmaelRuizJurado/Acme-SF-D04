
package acme.features.auditor.auditRecords;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audit_records.AuditRecords;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordsController extends AbstractController<Auditor, AuditRecords> {

	@Autowired
	protected AuditorAuditRecordsListService listAllService;

	/*
	 * @Autowired
	 * protected AuditorAuditRecordsShowService showService;
	 * 
	 * @Autowired
	 * protected AuditorAuditRecordsCreateService createService;
	 * 
	 * @Autowired
	 * protected AuditorAuditRecordsUpdateService updateService;
	 */


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		/*
		 * super.addBasicCommand("show", this.showService);
		 * super.addBasicCommand("create", this.createService);
		 * super.addBasicCommand("update", this.updateService);
		 */

	}

}
