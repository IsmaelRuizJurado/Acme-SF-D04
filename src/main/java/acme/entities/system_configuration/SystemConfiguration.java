
package acme.entities.system_configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^[A-Z]{3}$", message = "{validation.systemConfiguration.systemCurrency}")
	@NotNull
	protected String			systemCurrency;

	@Pattern(regexp = "^([A-Z]{3},)*[A-Z]{3}$", message = "{validation.systemConfiguration.aceptedCurrencies}")
	@NotNull
	protected String			acceptedCurrencies;
}
