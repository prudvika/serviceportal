function addNewAttribute() {
	var tableEl = document.getElementById('attributeTable');
	var i=1;
	while(document.getElementsByName("name-"+i).length!=0){
		i++;
	}
	
	var nameEl=document.getElementsByName("name-"+(i-1))[0].cloneNode(true);
	nameEl.name="name-"+i;
	var typeEl=document.getElementsByName("type-"+(i-1))[0].cloneNode(true);
	typeEl.name="type-"+i;
	var keepHistoryEl=document.getElementsByName("keepHistory-"+(i-1))[0].cloneNode(true);
	keepHistoryEl.name="keepHistory-"+i;
	var dataFieldEl=document.getElementsByName("dataField-"+(i-1))[0].cloneNode(true);
	dataFieldEl.name="dataField-"+i;
	
	var x=new XMLSerializer();
	var htmlData='<tr><td>'+x.serializeToString(nameEl)+'</td>'+
	                     '<td>'+x.serializeToString(typeEl)+'</td>'+
	                     '<td>'+x.serializeToString(keepHistoryEl)+'</td>'+
	                     '<td>'+x.serializeToString(dataFieldEl)+'</td></tr>';
	
	tableEl.innerHTML = tableEl.innerHTML + htmlData;
}