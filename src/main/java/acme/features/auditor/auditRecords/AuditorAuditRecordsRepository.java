
package acme.features.auditor.auditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecords;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRecordsRepository extends AbstractRepository {

	@Query("SELECT a FROM AuditRecords a WHERE a.codeAudits.auditor.userAccount.id = :id")
	Collection<AuditRecords> findAuditRecordByAuditorId(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.id = :id")
	AuditRecords findAuditRecordsById(int id);

	@Query("select a from Auditor a where a.id  = :id")
	Auditor findOneAuditorById(int id);

}
