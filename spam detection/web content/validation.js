function isPasswordMatch(newpassword1, newpassword2)
{
	if(newpassword1.value==newpassword2.value){
		return true;
	}else{
		alert("Password Mismatch... Please try again!");
		newpassword2.focus();
		return false;
	}
}
function funDisable(){
	if(document.getElementById("Public").checked){
		var friends=document.getElementsByName("friendname");
		for(var i=0;i<friends.length();i++){
			friends[i].disabled=true;
		}
		var groups=document.getElementsByName("groupname");
		for(var i=0;i<groups.length();i++){
			groups[i].disabled=true;
		}
	}else if(document.getElementById("Private").checked){
		var friends=document.getElementsByName("friendname");
		for(var i=0;i<friends.length();i++){
			friends[i].disabled=false;
		}
		var groups=document.getElementsByName("groupname");
		for(var i=0;i<groups.length();i++){
			groups[i].disabled=false;
		}
	}
}