
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
public class Sponsor extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@NotBlank
	@Length(max = 75)
	protected String			name;

	@NotNull
	@NotBlank
	@Length(max = 100)
	protected String			benefits;

	@URL
	protected String			link;

	@Email
	protected String			email;

}
