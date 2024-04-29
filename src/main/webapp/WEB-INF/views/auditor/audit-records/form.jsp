<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.audit-records.form.label.code" path="code"/>	
	<acme:input-moment code="auditor.audit-records.form.label.startPeriod" path="startPeriod"/>	
	<acme:input-moment code="auditor.audit-records.form.label.endPeriod" path="endPeriod"/>	
	<acme:input-select code="auditor.audit-records.form.label.mark" path="mark" choices="${marks}"/>	
	<acme:input-url code="auditor.audit-records.form.label.link" path="link"/>
	<acme:input-select code="auditor.audit-records.form.label.codeAudits" path="codeAudits" choices="${codeauditslist}"/>	
	
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  }">
			<acme:submit code="auditor.audit-records.form.button.update" action="/auditor/audit-records/update" />
			<acme:submit code="auditor.audit-records.form.button.delete" action="/auditor/audit-records/delete"/>
			<acme:submit code="auditor.audit-records.form.button.publish" action="/auditor/audit-records/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  }">
			<acme:submit code="auditor.audit-records.form.button.update" action="/auditor/audit-records/update"/>
			<acme:submit code="auditor.audit-records.form.button.delete" action="/auditor/audit-records/delete"/>
			<acme:submit code="auditor.audit-records.form.button.publish" action="/auditor/audit-records/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-records.form.button.create" action="/auditor/audit-records/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>