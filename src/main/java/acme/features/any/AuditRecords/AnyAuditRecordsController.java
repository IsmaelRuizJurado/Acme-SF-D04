
package acme.features.any.AuditRecords;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.audit_records.AuditRecords;

@Controller
public class AnyAuditRecordsController extends AbstractController<Any, AuditRecords> {

	@Autowired
	protected AnyAuditRecordsListService	listService;

	@Autowired
	protected AnyAuditRecordsShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

	}
}
