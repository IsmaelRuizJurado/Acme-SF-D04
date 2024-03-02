
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

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

	@NotBlank
	@Length(max = 75)
	private String				firm;

	@NotBlank
	@Length(max = 25)
	@Column(unique = true)
	private Integer				professionalId;

	@NotBlank
	@Length(max = 100)
	private String				certifications;

	@NotBlank
	@URL
	private String				link;

}
