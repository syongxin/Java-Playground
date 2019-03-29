/**
 * 
 */
var user_temp;
$("#addnew").click(function(){
	
	
	if($(this).next().attr('id') === 'new record' ){
		$(this).next().remove();
	}else{
		$(this).after("<div id='new record'><form method='post' action='addnew.jsp'>" +
				"<input type='text' name='username' placeholder='username'/>" +
				"<input type='password' name='password' placeholder='password'/>" +
				"<input type='submit' value='Add'/></form></div>");
	}
	

})

$(document).on('click','img[id^="edit_"]',function(){
	var currentTD = $(this).parents('tr').find('td');
	
	for(var i=1;i<3;i++){
		$(currentTD[i]).prop('contenteditable', true);
	}
	
	user_temp = currentTD[1].innerHTML;
	
	$(this).hide();
	$(this).siblings('.save').show();
	
});

$(document).on('click','img[id^="save_"]',function(){
	var id = parseInt($(this).attr('id').split('_')[1]);
	var currentTD = $(this).parents('tr').find('td');
	var username = currentTD[1].innerHTML;
	var password = currentTD[2].innerHTML;
	var sameuser = false;
	if(username === user_temp){
		sameuser = true;
	}
	for(var i=1;i<3;i++){
		$(currentTD[i]).prop('contenteditable', false);
	}
	$(this).hide();
	$(this).siblings('.edit').show();
	
	window.location="update.jsp?id="+id+"&username="+username+"&password="+password+"&sameuser="+sameuser;
	
});
