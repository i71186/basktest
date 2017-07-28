<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"
	integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
	crossorigin="anonymous"></script>
</head>
<body>

<h1>Demo Page for file upload</h1>

<form id="fileUploadForm">
  
  <table>
	  <tr align="left">
	  	<td align="left" width="20%">Environment:</td>
	  	<td align="left" width="80%"><input id="environment" type="text" name="environment" /></td>
	  </tr>
	  <tr align="left">
	  	<td align="left" width="20%">Token:</td>
	  	<td align="left" width="80%"><input id="token" type="text" name="token" /></td>
	  </tr>
	  <tr align="left">
	  	<td align="left" width="20%">Application Id:</td>
	  	<td align="left" width="80%"><input type="text" name="appId" /></td>
	  </tr>
	  <tr align="left">
	  	<td align="left" width="20%">Activity Type:</td>
	  	<td align="left" width="80%"><input type="text" name="activityType" /></td>
	  </tr>
	  <tr align="left">
	  	<td align="left" width="20%">Case Id:</td>
	  	<td align="left" width="80%"><input type="text" name="caseId" /></td>
	  </tr>
	  <tr align="left">
	  	<td align="left" width="20%">Title:</td>
	  	<td align="left" width="80%"><input type="text" name="title" /></td>
	  </tr>
	  <tr align="left">
	  	<td align="left" width="20%">Description:</td>
	  	<td align="left" width="80%"><input type="text" name="description" /></td>
	  </tr>
	  <tr align="left">
	  	<td align="left" width="20%">Select File:</td>
	  	<td align="left" width="80%"><input type="file" name="file" /></td>
	  </tr>

	  <tr align="left">
	  	<td colspan="2" align="center" width="100%"><input type="button" value="submit"  onclick="search123()" /></td>
	  </tr>

  </table>
  

</form>


<div id="response"></div>

	<script type="text/javascript">
    function search123(){
    
       var token = document.getElementById("token").value;
       
       var environment = document.getElementById("environment").value;
    	
         //getOAuthToken();
          var form = $('#fileUploadForm')[0];

          var strUrl ="http://localhost:8099/casemanager/siuc/1.0/uploadattachments";
          if('T' == environment){
        	  strUrl = "https://api1t.iso.com/claimsearch/casemanager/siuc/1.0/uploadattachments";
          } else if('A' == environment){
        	  strUrl = "https://api1a.iso.com/claimsearch/casemanager/siuc/1.0/uploadattachments";
          } else if('P' == environment){
        	  strUrl = "https://api1.verisk.com/claimsearch/casemanager/siuc/1.0/uploadattachments";
          }
          
    var data = new FormData(form);
        
         $.ajax({
        	 type: "POST",
             enctype: 'multipart/form-data',
             data :data,
             processData: false, 
             contentType: false,
             cache: false,
             headers : {
            	 'Authorization': 'bearer' + token
            	 
                 },
             
             url: strUrl ,
             success: function (data) {

                	console.log("SUCCESS : ", data);
                	alert("Successfully Uploaded");

             },
             error: function (e) {

             	    console.log("ERROR : ", e);
             	    alert("Error on Uploaded");
             }
            
            
            });
        }
  
    </script>

</body>
</html>