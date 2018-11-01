//Designed by Chong Xie 21885263
//This is for checking input of forms
//Three patterns are defined for testing input values
//parking lots name can only be made by space and letters
//capacity and permissions can only be positive intergers
//coordinators can only be a two decimals or intergers, and there is comma between them
var name_test_pattern= /^[\w\s]+$/;
var capacity_test_pattern= /^[1-9]\d*$/;
var coor_test_pattern= /^(\-|\+)?\d+(\.\d+)?(,)(\-|\+)?\d+(\.\d+)?$/;


function check_insert_form(){
	var target_form= document.getElementById("new_parking");
    //boolean value array store checking results
	arr_bool=[]
    arr_bool.push(name_test_pattern.test(target_form[0].value));
    arr_bool.push(capacity_test_pattern.test(target_form[1].value));
    arr_bool.push(capacity_test_pattern.test(target_form[2].value));
    arr_bool.push(capacity_test_pattern.test(target_form[3].value));
    arr_bool.push(coor_test_pattern.test(target_form[5].value));
    arr_bool.push(coor_test_pattern.test(target_form[6].value));
    arr_bool.push(coor_test_pattern.test(target_form[7].value));
    arr_bool.push(coor_test_pattern.test(target_form[8].value));
    arr_bool.push(coor_test_pattern.test(target_form[9].value));

    //if count_ture is equal to from.length, that mean all pass, submit the form.
    //else alert checking windows.
    var count_true=0;

    for (var i=0; i<arr_bool.length; i++){
    	if (arr_bool[i]==false){
    		break;
    	}else{
    		count_true++;
    	}
    }


    

    if (count_true==arr_bool.length){
    	target_form.submit();
    }else{
    	alert("please check your input!");
    }
}	

function check_update_form(){
	var target_form= document.getElementById("updated_parking");
	arr_bool=[]
    arr_bool.push(name_test_pattern.test(target_form[0].value));
    arr_bool.push(capacity_test_pattern.test(target_form[1].value));
    arr_bool.push(capacity_test_pattern.test(target_form[2].value));
    arr_bool.push(capacity_test_pattern.test(target_form[3].value));
    arr_bool.push(coor_test_pattern.test(target_form[5].value));
    arr_bool.push(coor_test_pattern.test(target_form[6].value));
    arr_bool.push(coor_test_pattern.test(target_form[7].value));
    arr_bool.push(coor_test_pattern.test(target_form[8].value));
    arr_bool.push(coor_test_pattern.test(target_form[9].value));

    var count_true=0;

    for (var i=0; i<arr_bool.length; i++){
    	if (arr_bool[i]==false){
    		break;
    	}else{
    		count_true++;
    	}
    }
    

    if (count_true==arr_bool.length){
    	target_form.submit();
    }else{
    	alert("please check your input!");
    }
}	