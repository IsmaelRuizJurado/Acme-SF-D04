
package acme.features.any.progress_logs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.progress_logs.ProgressLogs;

@Controller
public class AnyProgressLogsController extends AbstractController<Any, ProgressLogs> {

	@Autowired
	protected AnyProgressLogsListService	listService;

	@Autowired
	protected AnyProgressLogsShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
