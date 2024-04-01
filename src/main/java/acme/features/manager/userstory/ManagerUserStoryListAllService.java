
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListAllService extends AbstractService<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		final Manager manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		objects = this.repository.findUserStoriesByManager(manager);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCostPerHour");
		super.getResponse().addGlobal("showCreate", false);
		super.getResponse().addData(dataset);
	}
}
