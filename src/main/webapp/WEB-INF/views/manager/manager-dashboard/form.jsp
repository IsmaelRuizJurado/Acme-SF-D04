<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="manager.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.average-project-cost"/>
		</th>
		<td>
			<acme:print value="${averageProjectCost[0]}"/>
		</td>
		<td>
			<acme:print value="${averageProjectCost[1]}"/>
		</td>
		<td>
			<acme:print value="${averageProjectCost[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.max-project-cost"/>
		</th>
		<td>
			<acme:print value="${maxProjectCost[0]}"/>
		</td>
		<td>
			<acme:print value="${maxProjectCost[1]}"/>
		</td>
		<td>
			<acme:print value="${maxProjectCost[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.min-project-cost"/>
		</th>
		<td>
			<acme:print value="${minProjectCost[0]}"/>
		</td>
		<td>
			<acme:print value="${minProjectCost[1]}"/>
		</td>
		<td>
			<acme:print value="${minProjectCost[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviation-project-cost"/>
		</th>
		<td>
			<acme:print value="${deviationProjectCost[0]}"/>
		</td>
		<td>
			<acme:print value="${deviationProjectCost[1]}"/>
		</td>
		<td>
			<acme:print value="${deviationProjectCost[2]}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.average-us-cost"/>
		</th>
		<td>
			<acme:print value="${averageUsCost[0]}"/>
		</td>
		<td>
			<acme:print value="${averageUsCost[1]}"/>
		</td>
		<td>
			<acme:print value="${averageUsCost[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.max-us-cost"/>
		</th>
		<td>
			<acme:print value="${maxUsCost[0]}"/>
		</td>
		<td>
			<acme:print value="${maxUsCost[1]}"/>
		</td>
		<td>
			<acme:print value="${maxUsCost[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.min-us-cost"/>
		</th>
		<td>
			<acme:print value="${minUsCost[0]}"/>
		</td>
		<td>
			<acme:print value="${minUsCost[1]}"/>
		</td>
		<td>
			<acme:print value="${minUsCost[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviation-us-cost"/>
		</th>
		<td>
			<acme:print value="${desviationUsCost[0]}"/>
		</td>
		<td>
			<acme:print value="${desviationUsCost[1]}"/>
		</td>
		<td>
			<acme:print value="${desviationUsCost[2]}"/>
		</td>
	</tr>		
</table>

<h2>
	<acme:message code="manager.dashboard.form.title.priority"/>
</h2>

<div>
	<canvas id="myCanvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"Must", "Should", "Could", "Wont"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${mustNumber}"/>, 
						<jstl:out value="${shouldNumber}"/>, 
						<jstl:out value="${couldNumber}"/>,
						<jstl:out value="${wontNumber}"/>
					],
		            backgroundColor: [
		                'rgba(255, 99, 132, 0.2)',   // Color for "must"
		                'rgba(54, 162, 235, 0.2)',   // Color for "should"
		                'rgba(255, 206, 86, 0.2)',   // Color for "could"
		                'rgba(75, 192, 192, 0.2)'    // Color for "wont"
		            ],
		            borderColor: [
		                'rgba(255, 99, 132, 1)',     // Border color for "must"
		                'rgba(54, 162, 235, 1)',     // Border color for "should"
		                'rgba(255, 206, 86, 1)',     // Border color for "could"
		                'rgba(75, 192, 192, 1)'      // Border color for "wont"
		            ],
		            borderWidth: 1
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("myCanvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>


<acme:return/>