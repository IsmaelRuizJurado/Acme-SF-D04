
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.claim.Claim;

@Controller
public class AnyClaimController extends AbstractController<Any, Claim> {

	@Autowired
	protected AnyClaimListService	listService;

	@Autowired
	AnyClaimShowService				showService;


	void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
