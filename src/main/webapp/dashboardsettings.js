function addMapping(dashAttribute) {
	var mappingTableEl = document.getElementById(dashAttribute + '-table');
	var i=0;
	while(document.getElementsByName("device-"+dashAttribute+"-"+i).length!=0){
		i++;
	}
	
	var deviceAttributeEl=document.getElementsByName("device-"+dashAttribute+"-"+(i-1))[0].cloneNode(true);
	deviceAttributeEl.name="device-"+dashAttribute+"-"+i;
	var operatorEl=document.getElementsByName("operator-"+dashAttribute+"-"+(i-1))[0].cloneNode(true);
	operatorEl.name="operator-"+dashAttribute+"-"+i;
	var valueEl=document.getElementsByName("value-"+dashAttribute+"-"+(i-1))[0].cloneNode(true);
	valueEl.name="value-"+dashAttribute+"-"+i;
	var constantEl=document.getElementsByName("constant-"+dashAttribute+"-"+(i-1))[0].cloneNode(true);
	constantEl.name="constant-"+dashAttribute+"-"+i;
	
	var x=new XMLSerializer();
	var htmlData='<tr><td>'+x.serializeToString(deviceAttributeEl)+'</td>'+
	                     '<td>'+x.serializeToString(operatorEl)+'</td>'+
	                     '<td>'+x.serializeToString(valueEl)+'</td>'+
	                     '<td>'+x.serializeToString(constantEl)+'</td></tr>';
	
	mappingTableEl.innerHTML = mappingTableEl.innerHTML + htmlData;
}
