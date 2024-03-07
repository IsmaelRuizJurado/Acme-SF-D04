
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@NotNull
	@Length(min = 1, max = 75)
	private String				degree;

	@NotBlank
	@NotNull
	@Length(min = 1, max = 100)
	private String				specialisation;

	@NotBlank
	@NotNull
	@Length(min = 1, max = 100)
	private String				skills;

	@NotBlank
	@NotNull
	@Email
	private String				email;

	@URL
	private String				link;
}
