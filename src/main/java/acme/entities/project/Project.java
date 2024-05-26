
package acme.entities.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
@Table(indexes = {

	@Index(columnList = "code"), @Index(columnList = "draftMode")
})
public class Project extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@NotNull
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{4}", message = "{validation.project.code}")
	private String				code;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 75)
	private String				title;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 100)
	private String				abstractt;

	@NotNull
	@Transient
	private boolean				indication;

	@Valid
	@NotNull
	private Money				cost;

	@URL
	@Length(max = 255)
	private String				link;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Manager				manager;

}
