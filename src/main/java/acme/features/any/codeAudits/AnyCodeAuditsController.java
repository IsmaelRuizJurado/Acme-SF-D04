
package acme.features.any.codeAudits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.code_audits.CodeAudits;

@Controller
public class AnyCodeAuditsController extends AbstractController<Any, CodeAudits> {

	@Autowired
	protected AnyCodeAuditsListService	listService;

	@Autowired
	protected AnyCodeAuditsShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
