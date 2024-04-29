
package acme.features.auditor.audit_records;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audit_records.AuditRecords;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordsController extends AbstractController<Auditor, AuditRecords> {

	@Autowired
	protected AuditorAuditRecordsListService	listAllService;

	@Autowired
	protected AuditorAuditRecordsCreateService	createService;

	@Autowired
	protected AuditorAuditRecordsShowService	showService;

	@Autowired
	protected AuditorAuditRecordsDeleteService	deleteService;

	@Autowired
	protected AuditorAuditRecordsUpdateService	updateService;

	@Autowired
	protected AuditorAuditRecordsPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);

	}

}
