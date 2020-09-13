var a = 10;
var b = 20;

function test(){
    var item = document.getElementById("test");
    item.innerHTML = "testing";
}

var local = "http://localhost:8080/mailer/";



function loadCategories() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var categories = JSON.parse(this.responseText);
	    	var categoryDropdown = document.getElementById("recipient_categories");
	    	
	    	for(var i=0; i < categories.length; i++){
	    		var option = document.createElement("option");
		    	option.text = categories[i];
		    	categoryDropdown.add(option);
	    	}
	     
	    }
	  };
	  xhttp.open("GET", local + "category", true);
	  xhttp.send();
}

function loadRetrievedEmails (){
	
	  var xmlhttp, myObj, x, txt = "";
	  
	  xmlhttp = new XMLHttpRequest();
	  xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      myObj = JSON.parse(this.responseText);
	      txt += "<table border='1'>"
	    	    
	      //for (x in myObj) {
	    	  for(var i=0; i < myObj.length; i++){
	        txt += "<tr><td>" + myObj[i].subject + "</td><td>" + myObj[i].recipientCategory + "</td></tr>"+
	        		"<tr><td colspan = 2>" + myObj[i].emailText+ "</td></tr>";
	      }
	      txt += "</table>"
	      document.getElementById("list").innerHTML = txt;
	    }
	  };
	  xmlhttp.open("GET", local +"email", true);
	  xmlhttp.send();
	  
}

