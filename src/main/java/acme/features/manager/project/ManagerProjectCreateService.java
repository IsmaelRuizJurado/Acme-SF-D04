
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerProjectRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Project object;
		object = new Project();
		final Manager manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setManager(manager);
		object.setDraftMode(true);
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
		if (!super.getBuffer().getErrors().hasErrors("abstractt"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstractt()), "abstractt", "manager.project.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;
			existing = this.repository.findProjectByCode(object.getCode());
			super.state(existing == null, "code", "manager.project.form.error.code");
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
		super.getResponse().addData(dataset);
	}
}
