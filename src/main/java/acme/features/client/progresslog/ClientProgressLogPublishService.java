
package acme.features.client.progresslog;

import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.progress_logs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogPublishService extends AbstractService<Client, ProgressLogs> {

}
