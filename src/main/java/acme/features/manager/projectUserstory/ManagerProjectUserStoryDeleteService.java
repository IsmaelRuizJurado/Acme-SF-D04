
package acme.features.manager.projectUserstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project_user_story.ProjectUserStory;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryDeleteService extends AbstractService<Manager, ProjectUserStory> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		UserStory object;
		int id;
		id = super.getRequest().getData("userStoryId", int.class);
		object = this.repository.findUserStoryById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		object = new ProjectUserStory();
		final UserStory userstory;
		int userstoryId;
		userstoryId = super.getRequest().getData("userStoryId", int.class);
		userstory = this.repository.findOneUserStoryById(userstoryId);
		object.setUserStory(userstory);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findProjectById(projectId);
		super.bind(object, "id");
		object.setProject(project);
	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(object.getProject() != null, "project", "manager.projectUserStory.form.error.projectNotNull");
		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(object.getProject().isDraftMode(), "project", "manager.projectUserStory.form.error.project");
	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		final ProjectUserStory pus = this.repository.findProjectUserStoryByProjectAndUserStory(object.getProject(), object.getUserStory());

		this.repository.delete(pus);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "userStory", "project");
		final int userstoryId = super.getRequest().getData("userStoryId", int.class);
		dataset.put("userStoryId", super.getRequest().getData("userStoryId", int.class));
		Collection<Project> projects;
		projects = this.repository.findProjectsByUserStory(object.getUserStory());
		final UserStory userstory = this.repository.findOneUserStoryById(userstoryId);
		dataset.put("draftMode", userstory.isDraftMode());
		final SelectChoices choices = new SelectChoices();

		if (object.getProject() == null)
			choices.add("0", "---", true);
		else
			choices.add("0", "---", false);

		for (final Project c : projects)
			if (object.getProject() != null && object.getProject().getId() == c.getId())
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), true);
			else
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), false);

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("proyectos", projects);
		super.getResponse().addData(dataset);
	}

}
