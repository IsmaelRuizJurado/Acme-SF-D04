
package acme.features.client.progresslog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.progress_logs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, ProgressLogs> {

	@Autowired
	protected ClientProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------

}
