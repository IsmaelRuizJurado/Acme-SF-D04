<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.code-audits.form.label.code" path="code"/>	
	<acme:input-moment code="auditor.code-audits.form.label.executionDate" path="executionDate"/>	
	<acme:input-select code="auditor.code-audits.form.label.type" path="type" choices="${types}"/>	
	<acme:input-textbox code="auditor.code-audits.form.label.correctiveActions" path="correctiveActions"/>	
	<acme:input-url code="auditor.code-audits.form.label.link" path="link"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  }">
			<acme:submit code="auditor.code-audits.form.button.update" action="/auditor/code-audits/update" />
			<acme:submit code="auditor.code-audits.form.button.delete" action="/auditor/code-audits/delete"/>
			<acme:submit code="auditor.code-audits.form.button.publish" action="/auditor/code-audits/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  }">
			<acme:submit code="auditor.code-audits.form.button.update" action="/auditor/code-audits/update"/>
			<acme:submit code="auditor.code-audits.form.button.delete" action="/auditor/code-audits/delete"/>
			<acme:submit code="auditor.code-audits.form.button.publish" action="/auditor/code-audits/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.code-audits.form.button.create" action="/auditor/code-audits/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>