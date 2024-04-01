
package acme.features.manager.userstory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryController extends AbstractController<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryListService		listService;

	@Autowired
	protected ManagerUserStoryShowService		showService;

	@Autowired
	protected ManagerUserStoryCreateService		createService;

	@Autowired
	protected ManagerUserStoryUpdateService		updateService;

	@Autowired
	protected ManagerUserStoryPublishService	publishService;

	@Autowired
	protected ManagerUserStoryDeleteService		deleteService;

	@Autowired
	protected ManagerUserStoryListAllService	listAllService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("list-all", "list", this.listAllService);

	}
}
