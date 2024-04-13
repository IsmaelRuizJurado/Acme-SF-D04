
package acme.features.developer.training_modules;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.training_module.TrainingModule;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingModuleController extends AbstractController<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleListService	listService;

	@Autowired
	protected DeveloperTrainingModuleCreateService	createService;

	@Autowired
	protected DeveloperTrainingModuleDeleteService	deleteService;

	@Autowired
	protected DeveloperTrainingModuleShowService	showService;

	@Autowired
	protected DeveloperTrainingModulePublishService	publishService;

	@Autowired
	protected DeveloperTrainingModuleUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("publish", this.publishService);
		super.addBasicCommand("update", this.updateService);
	}

}
