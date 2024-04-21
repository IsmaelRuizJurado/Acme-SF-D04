

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.average-audit-record"/>
		</th>
		<td>
			<acme:print value="${numAuditRecord.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.min-audit-record"/>
		</th>
		<td>
			<acme:print value="${numAuditRecord.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.max-audit-record"/>
		</th>
		<td>
			<acme:print value="${numAuditRecord.getMaximum()}"/>
		</td>
	</tr>
	<tr>
	
	<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.average-audit-record-period"/>
		</th>
		<td>
			<acme:print value="${auditingTimePeriod.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.min-audit-record-period"/>
		</th>
		<td>
			<acme:print value="${auditingTimePeriod.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.max-audit-record-period"/>
		</th>
		<td>
			<acme:print value="${auditingTimePeriod.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.static-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumAudits.get('STATIC')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.dimanic-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumAudits.get('DINAMIC')}"/>
		</td>
	</tr>	
</table>

<jstl:choose>
<jstl:when test="${numAuditRecord.getMaximum()>0.0}">
						
		
	<h3><acme:message code="auditor.auditorDashboard.form.label.projects.information"/></h3>
	<div>
		<canvas id="canvas"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${numAuditRecord.getAverage()}"/>, 
							<jstl:out value="${numAuditRecord.getMaximum()}"/>, 
							<jstl:out value="${numAuditRecord.getMinimum()}"/>,
							<jstl:out value="${numAuditRecord.getDeviation()}"/>
						],
						backgroundColor: [
						      'rgb(40, 180, 99)',
					    	  'rgb(54, 162, 235)',
					    	  'rgb(255, 205, 86)',
					      	  'rgb(230, 170, 243)'
					    ]
					}
				]
			};
			var options = {
				scales : {
					yAxes : [
						{
							ticks : {
								suggestedMin : 0.0,
								suggestedMax : 50.0
							}
						}
					]
				},
				legend : {
					display : false
				}
			};
	
			var canvas, context;
	
			canvas = document.getElementById("canvas");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "bar",
				data : data,
				options : options
			});
		});
	</script>

</jstl:when>
</jstl:choose>

<jstl:choose>
<jstl:when test="${codeAuditsPerTypes.get('STATIC') != 0 || codeAuditsPerTypes.get('DINAMIC') != 0}">

	<h3><acme:message code="auditor.auditorDashboard.form.label.audit-records.type.information"/></h3>
	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"STATIC", "DINAMIC"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${totalNumAudits.get('STATIC')}"/>, 
							<jstl:out value="${totalNumAudits.get('DINAMIC')}"/>
							
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)'
					    ]
					}
				]
			};

	
			var canvas, context;
	
			canvas = document.getElementById("canvas3");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "doughnut",
				data : data,
			});
		});
	</script>
</jstl:when>
</jstl:choose>


<acme:return/>