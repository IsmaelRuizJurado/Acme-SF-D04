
package acme.features.auditor.codeAudits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditsController extends AbstractController<Auditor, CodeAudits> {

	@Autowired
	protected AuditorCodeAuditsListService		listService;

	@Autowired
	protected AuditorCodeAuditsShowService		showService;

	@Autowired
	protected AuditorCodeAuditsUpdateService	updateService;

	@Autowired
	protected AuditorCodeAuditsDeleteService	deleteService;

	@Autowired
	protected AuditorCodeAuditsCreateService	createService;

	@Autowired
	protected AuditorCodeAuditsPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
