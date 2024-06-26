<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code" placeholder="ABC-000"/>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-moment code="sponsor.sponsorship.form.label.moment" path="moment" readonly="true"/>	
	</jstl:if>	
	<acme:input-moment code="sponsor.sponsorship.form.label.startPeriod" path="startPeriod"/>	
	<acme:input-moment code="sponsor.sponsorship.form.label.endPeriod" path="endPeriod"/>	
	<acme:input-money code="sponsor.sponsorship.form.label.amount" path="amount"/>	
	<acme:input-select code="sponsor.sponsorship.form.label.type" path="type" choices="${types}"/>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-textbox code="sponsor.sponsorship.form.label.project" path="projectCode" readonly="true"/>	
	</jstl:if>
	<jstl:if test="${_command == 'create'}">
		<acme:input-select code="sponsor.sponsorship.form.label.project" path="project" choices="${projects}"/>	
	</jstl:if>
	<acme:input-url code="sponsor.sponsorship.form.label.link" path="link"/>
	<acme:input-email code="sponsor.sponsorship.form.label.email" path="email"/>	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:input-money code="sponsor.sponsorship.form.label.money" path="money" readonly="true"/>	
			<acme:button code="sponsor.sponsorship.invoice" action="/sponsor/invoice/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
			<acme:input-money code="sponsor.sponsorship.form.label.money" path="money" readonly="true"/>	
			<acme:button code="sponsor.sponsorship.invoice" action="/sponsor/invoice/list?masterId=${id}"/>
			<acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
			<acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
			<acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
			<acme:input-money code="sponsor.sponsorship.form.label.money" path="money" readonly="true"/>
			<acme:button code="sponsor.sponsorship.invoice" action="/sponsor/invoice/list?masterId=${masterId}"/>
			<acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
			<acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
			<acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorship.form.button.create" action="/sponsor/sponsorship/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>