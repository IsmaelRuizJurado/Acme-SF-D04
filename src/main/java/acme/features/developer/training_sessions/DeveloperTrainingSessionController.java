
package acme.features.developer.training_sessions;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionListService		listService;

	@Autowired
	protected DeveloperTrainingSessionCreateService		createService;

	@Autowired
	protected DeveloperTrainingSessionShowService		showService;

	@Autowired
	protected DeveloperTrainingSessionDeleteService		deleteService;

	@Autowired
	protected DeveloperTrainingSessionUpdateService		updateService;

	@Autowired
	protected DeveloperTrainingSessionPublishService	publishService;

	@Autowired
	protected DeveloperTrainingSessionListAllService	listAllService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("list-all", "list", this.listAllService);

	}

}
