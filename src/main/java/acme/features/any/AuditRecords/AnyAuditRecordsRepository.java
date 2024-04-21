
package acme.features.any.AuditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecords;

@Repository
public interface AnyAuditRecordsRepository extends AbstractRepository {

	@Query("SELECT a FROM AuditRecords a WHERE a.draftMode=false and a.codeAudits.id = :id")
	Collection<AuditRecords> findAuditRecordsByCodeAudits(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.id = :id")
	AuditRecords findAuditRecordsById(int id);

}
