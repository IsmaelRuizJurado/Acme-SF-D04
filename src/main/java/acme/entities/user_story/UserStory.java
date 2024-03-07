
package acme.entities.user_story;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@NotBlank
	@Length(min = 1, max = 75)
	private String				title;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 100)
	private String				description;

	@Valid
	@NotNull
	private Money				estimatedCostPerHour;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 100)
	private String				acceptanceCriteria;

	@Valid
	@NotNull
	private Priority			priority;

	@URL
	private String				link;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Manager				manager;

}
