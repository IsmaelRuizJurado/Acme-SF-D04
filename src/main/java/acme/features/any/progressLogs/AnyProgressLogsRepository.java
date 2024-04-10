
package acme.features.any.progressLogs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.progress_logs.ProgressLogs;

@Repository
public interface AnyProgressLogsRepository extends AbstractRepository {

	@Query("select pl from ProgressLogs pl where pl.draftMode = false and pl.contract.id = :id")
	Collection<ProgressLogs> findPublishedProgressLogsByContract(int id);

	@Query("select pl from ProgressLogs pl where pl.id = :id")
	ProgressLogs findProgressLogsById(int id);
}
