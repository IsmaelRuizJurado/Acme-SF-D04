
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Auditor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				firm;

	@NotNull
	@NotBlank
	@Length(max = 25)
	private String				professionalId;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String				certifications;

	@URL
	private String				link;

}
