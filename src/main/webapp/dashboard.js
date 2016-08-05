// Immediately executes the data inside and prevents global namespace issues
	// Residing place for our Canvas' context
	var ctx = null;

	var Dashboard = {
		canvas : document.getElementById('canvas'),
		picture : document.getElementById('china_png'),
		title : 'Device Monitor Dashboard',
		width : 800,
		height : 600,
		selectedObject : null,
		message : [],
		objects : [],
		setup : function() {
			if (this.canvas.getContext) {
				var h=screen.height-40;
				var w=screen.width-20;
				this.canvas.setAttribute('width',w);
				this.canvas.setAttribute('height',h);
				
				// Setup variables
				ctx = this.canvas.getContext('2d');
				this.width = this.canvas.width;
				this.height = this.canvas.height;

				this.draw();

				Ctrl.init();
			}
		},
		
		getRelativeLocationX: function(x){
			return x*this.width/800;			
		},
		
		getRelativeLocationY: function(y){
			return y*this.height/600;			
		},
		

		draw : function() {
			// Draw the background image
			ctx.drawImage(this.picture, 0, 0, this.width, this.height);

			// Draw the border of dashboard
			ctx.strokeStyle = "black";
			ctx.strokeRect(0, 0, this.width, this.height);
			
			// Draw devices
			for (var i = 0; i < this.objects.length; i++) {
				//var x = this.getRelativeLocationX(this.objects[i].locationX);
				//var y = this.getRelativeLocationY(this.objects[i].locationY);
				var x=this.objects[i].locationX;
				var y=this.objects[i].locationY;
				var color = this.objects[i].color;
				ctx.fillStyle = color;
				if (this.objects[i].type == 'Circle') {
					var radius = this.objects[i].radius;

					ctx.beginPath();
					ctx.arc(x, y, radius, 0, 2 * Math.PI);
					ctx.closePath();
					ctx.fill();

					if (this.selectedObject!=null && this.objects[i].deviceId == this.selectedObject.deviceId) {
						// The object might be refreshed so compare id instead of the object
						ctx.strokeRect(x - radius, y - radius, radius * 2,
								radius * 2);
						
						// Draw device attributes
						var jsonAttrNum=0;
						var longAttrLength=0;
						for(var item in this.objects[i].device){
							jsonAttrNum++;
							if(item.length+this.objects[i].device[item].length>longAttrLength){
								longAttrLength=item.length+this.objects[i].device[item].length;
							}
						}
						
						var textY=y-radius-jsonAttrNum*12;
						var textX=x-radius-50;
						//alert(textX+','+textY);
						var index=0;
						for(var item in this.objects[i].device){
							ctx.fillStyle = '#000000';
							ctx.textAlign = 'left';
							ctx.font = '12px helvetica, arial';
							ctx.fillText(item+':'+this.objects[i].device[item], textX, textY+index*12);
							index++;
						}
					}
				} else if (this.objects[i].type == 'Rectangle') {
					var width = this.objects[i].width;
					var height = this.objects[i].height;

					ctx.fillRect(x, y, width, height);

					if (this.selectedObject!=null && this.objects[i].deviceId == this.selectedObject.deviceId) {
						ctx.strokeRect(x, y, width, height);
						
						// Draw device attributes
						var jsonAttrNum=0;
						var longAttrLength=0;
						for(var item in this.objects[i].device){
							jsonAttrNum++;
							if(item.length+this.objects[i].device[item].length>longAttrLength){
								longAttrLength=item.length+this.objects[i].device[item].length;
							}
						}
						
						var textY=y-jsonAttrNum*12;
						var textX=x-50;
						var index=0;
						for(var item in this.objects[i].device){
							ctx.fillStyle = '#000000';
							ctx.textAlign = 'left';
							ctx.font = '12px helvetica, arial';
							ctx.fillText(item+':'+this.objects[i].device[item], textX, textY+index*12);
							index++;
						}
					}
				}
			}

			// Draw title & message
			ctx.fillStyle = '#000000';
			ctx.textAlign = 'left';
			
			ctx.font = '36px helvetica, arial';
			ctx.fillText(this.title,10,40);
			
			ctx.font = '12px helvetica, arial';
			var startIndex = this.message.length - 5 < 0 ? 0
					: this.message.length - 5;
			for (var i = startIndex; i < this.message.length; i++) {
				ctx.fillText(this.message[i], 10, 20 * (i - startIndex + 1)+40);
			}
		},

	};

	/**
	 * Dashboard Control
	 */
	var Ctrl = {
		moveObject : false,
		downMouseX : -1,
		downMouseY : -1,
		init : function() {
			// alert('Initial control!');
			window.addEventListener('mousemove', this.moveHandler, true);
			window.addEventListener('mousedown', this.downHandler, true);
			window.addEventListener('mouseup', this.upHandler, true);
		},

		moveHandler : function(event) {
			var mouseX = event.pageX;
			var mouseY = event.pageY;
			var canvasX = Dashboard.canvas.offsetLeft;
			var canvasY = Dashboard.canvas.offsetTop;
			var selectedObject = null;
			if (mouseX > canvasX && mouseX < canvasX + Dashboard.width
					&& mouseY > canvasY && mouseY < canvasY + Dashboard.height) {
				// Move the selected object
				if (this.moveObject && Dashboard.selectedObject!=null) {
					Dashboard.selectedObject.locationX = Dashboard.selectedObject.locationX
							+ (mouseX - this.downMouseX);
					Dashboard.selectedObject.locationY = Dashboard.selectedObject.locationY
							+ (mouseY - this.downMouseY);
					this.downMouseX = mouseX;
					this.downMouseY = mouseY;
					Dashboard.draw();
					return;
				}
				
				// Move the cursor and select the object
				for (var i = 0; i < Dashboard.objects.length; i++) {
					var x = Dashboard.objects[i].locationX;
					var y = Dashboard.objects[i].locationY;
					if (Dashboard.objects[i].type == 'Circle') {
						var radius = Dashboard.objects[i].radius;
						if (mouseX > canvasX + x - radius
								&& mouseX < canvasX + x + radius
								&& mouseY > canvasY + y - radius
								&& mouseY < canvasY + y + radius) {
								selectedObject = Dashboard.objects[i];
								break;
						}
					}
					if (Dashboard.objects[i].type == "Rectangle") {
						var width = Dashboard.objects[i].width;
						var height = Dashboard.objects[i].height;
						if (mouseX > (canvasX + x)
								&& mouseX < (canvasX + x) + width
								&& mouseY > (canvasY + y)
								&& mouseY < (canvasY + y) + height) {
								selectedObject = Dashboard.objects[i];
								break;
						}
					}
				}

			}
			if (Dashboard.selectedObject != selectedObject) {
				Dashboard.selectedObject = selectedObject;
				/*if(Dashboard.selectedObject!=null){
					Dashboard.message.push(window.JSON.stringify(Dashboard.selectedObject.device));
				}*/
				Dashboard.draw();
			}
		},

		downHandler : function(event) {
			if (Dashboard.selectedObject != null) {
				this.moveObject = true;
				this.downMouseX = event.pageX;
				this.downMouseY = event.pageY;
			}
		},

		upHandler : function(event) {
			if (this.moveObject && Dashboard.selectedObject != null) {
				Session.sendMessage("UpdateDashboardObject:"+window.JSON
						.stringify(Dashboard.selectedObject));
			}
			this.moveObject = false;
		}
	};

	var Session = {
		socket : null,
		connect : function(host) {
			if ('WebSocket' in window) {
				Session.socket = new WebSocket(host);
			} else if ('MozWebSocket' in window) {
				Session.socket = new MozWebSocket(host);
			} else {
				Dashboard.message
						.push('Error: WebSocket is not supported by this browser.');
				Dashboard.draw();
				return;
			}

			Session.socket.onopen = function() {
				Dashboard.message.push('Info: WebSocket connection opened.');
				Dashboard.draw();
			};

			Session.socket.onclose = function() {
				Dashboard.message.push('Info: WebSocket closed.');
				Dashboard.draw();
			};

			Session.socket.onerror = function(event) {
				Dashboard.message.push("Error:" + event);
				Dashboard.draw();
			};

			Session.socket.onmessage = function(message) {
				Dashboard.objects = eval('(' + message.data + ')');
				Dashboard.title = 'Device Monitor Dashboard - '+Dashboard.objects.length+' Devices Monitored';
				Dashboard.draw();
			};
		},
		initialize : function() {
			if (window.location.protocol == 'http:') {
				Dashboard.message.push('Connect to ws://'
						+ window.location.host
						+ '/monitorservice-0.1/websocket/dashboardsession...');
				Session.connect('ws://' + window.location.host
						+ '/monitorservice-0.1/websocket/dashboardsession');
			}
		},
		sendMessage : function(message) {
			Session.socket.send(message);
		}
	};

	/***************************************************************************
	 * Draw dashboard
	 **************************************************************************/
	window.onload = function() {
		Dashboard.setup();
		Session.initialize();
		//Session.sendMessage("SetDashboard:Test");
	};