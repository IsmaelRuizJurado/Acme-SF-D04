

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.average-project-cost"/>
		</th>
		<td>
			<acme:print value="${projectCostStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.min-project-cost"/>
		</th>
		<td>
			<acme:print value="${projectCostStats.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.max-project-cost"/>
		</th>
		<td>
			<acme:print value="${projectCostStats.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.lin-dev-project-cost"/>
		</th>
		<td>
			<acme:print value="${projectCostStats.getDeviation()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.average-userstory-cost"/>
		</th>
		<td>
			<acme:print value="${userStoriesCostStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.max-userstory-cost"/>
		</th>
		<td>
			<acme:print value="${userStoriesCostStats.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.min-userstory-cost"/>
		</th>
		<td>
			<acme:print value="${userStoriesCostStats.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.lin-dev-userstory-cost"/>
		</th>
		<td>
			<acme:print value="${userStoriesCostStats.getDeviation()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.must-userstory"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesByPriority.get('MUST')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.should-userstory"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesByPriority.get('SHOULD')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.could-userstory"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesByPriority.get('COULD')}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.managerDashboard.form.label.wont-userstory"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesByPriority.get('WONT')}"/>
		</td>
	</tr>	
</table>

<jstl:choose>
<jstl:when test="${projectCostStats.getMaximum()>0.0}">
						
		
	<h3><acme:message code="manager.managerDashboard.form.label.projects.information"/></h3>
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
							<jstl:out value="${projectCostStats.getAverage()}"/>, 
							<jstl:out value="${projectCostStats.getMaximum()}"/>, 
							<jstl:out value="${projectCostStats.getMinimum()}"/>,
							<jstl:out value="${projectCostStats.getDeviation()}"/>
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
								suggestedMax : 100.0
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
<jstl:when test="${userStoriesCostStats.getMaximum()>0.0}">

	<h3><acme:message code="manager.managerDashboard.form.label.userstories.information"/></h3>
	<div>
		<canvas id="canvas2"></canvas>
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
							<jstl:out value="${userStoriesCostStats.getAverage()}"/>, 
							<jstl:out value="${userStoriesCostStats.getMaximum()}"/>, 
							<jstl:out value="${userStoriesCostStats.getMinimum()}"/>,
							<jstl:out value="${userStoriesCostStats.getDeviation()}"/>
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
									suggestedMax : 100.0
								}
							}
						]
					},
					legend : {
						display : false
					}
				};
			
			var canvas, context;
			canvas = document.getElementById("canvas2");
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
<jstl:when test="${totalUserStoriesByPriority.get('MUST') != 0 || totalUserStoriesByPriority.get('SHOULD') != 0 || totalUserStoriesByPriority.get('COULD') != 0 || totalUserStoriesByPriority.get('WONT') != 0}">

	<h3><acme:message code="manager.managerDashboard.form.label.userstories.type.information"/></h3>
	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"MUST", "SHOULD", "COULD", "WONT"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${totalUserStoriesByPriority.get('MUST')}"/>, 
							<jstl:out value="${totalUserStoriesByPriority.get('SHOULD')}"/>,
							<jstl:out value="${totalUserStoriesByPriority.get('COULD')}"/>,
							<jstl:out value="${totalUserStoriesByPriority.get('WONT')}"/>,
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