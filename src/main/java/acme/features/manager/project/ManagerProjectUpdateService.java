
package acme.features.manager.project;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.project.Project;
import acme.entities.user_story.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerProjectRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		Project object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "abstractt", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(this.auxiliarService.validatePrice(object.getCost(), 0, 1000000), "cost", "manager.project.form.error.cost");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "manager.project.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("abstractt$"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstractt()), "abstractt", "manager.project.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;
			existing = this.repository.findProjectByCode(object.getCode());
			final Project project2 = object.getCode().equals("") || object.getCode().equals(null) ? null : this.repository.findProjectById(object.getId());

			super.state(existing == null || project2.equals(existing), "code", "manager.project.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(this.auxiliarService.validateCurrency(object.getCost()), "cost", "manager.project.form.error.cost2");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstractt", "cost", "link", "draftMode", "manager");
		final List<UserStory> userStories = this.repository.findUserStoriesByProject(object.getId()).stream().collect(Collectors.toList());
		dataset.put("hasUserStories", userStories.size() > 0);
		dataset.put("money", this.auxiliarService.changeCurrency(object.getCost()));
		super.getResponse().addData(dataset);
	}
}
