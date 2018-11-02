//Designed by Chong Xie 21885263
var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var exec = require('child_process').exec;

var con_pool = mysql.createPool({
	//if you deploy your node.js server and db together
	//please remeber to change host into "host:localhost"
	host: "106.14.213.85",  
	//the administrator use root account.
	user: "root",
	password: "",
	database: "uwa_parking",
	port:"3306"

});

var parking_general_query = "SELECT * FROM parkinglots_general";



//current page

router.get('/checking', function(req, res, next){
    con_pool.getConnection(function(err, dbconnection){
    dbconnection.query(parking_general_query, function(err, result){   
	res.render('management',{
		//title:"test json",
		data: result
	    });
	
	dbconnection.release();
    });
    });
 });

//jump back to heat map href

router.get('/adminview', function(req, res, next){
        con_pool.getConnection(function(err, dbconnection){
		if (err) throw err;
		dbconnection.query(parking_general_query, function(err, result){
			if(err){
				console.log('[query]-:'+err);
			}else{
				res.render("adminview",{test:"test",data: result});
				
			}
		dbconnection.release();
		})
	});
});

module.exports = router;

//get history information

router.get("/history", function(req, res, next){
	//current working directory is located in heat_map_visualization instead of static path "public"
	exec('python3 ./public/python/sql_history.py', function(error,stdout,stderr){
		if(error){
			console.log("error, can not execute script");
		}else{
			console.log("successfully execute script");
			//var data = JSON.parse(stdout);
            //console.log(stdout);
		}
	});
	con_pool.getConnection(function(err,dbconnection){
		if(err) throw err;
		var parkinglots_record_query="SELECT * FROM parkinglots_status";
		dbconnection.query(parkinglots_record_query, function(err, result){
			if(err){
				console.log('[query]-:'+err);
			}else{
				function render_function(){res.render("history",{data:result});}
				//Give some time for generating bart charts
				setTimeout(render_function,5000);
				console.log(result);
			}
		dbconnection.release();
		});
	});
});

//delete the parking lots information

router.get("/del", function(req, res, next){
	console.log(req.query);
    var del_index_arr=req.query.parking_name_index;
    
    for (var i=0; i<del_index_arr.length;i++){
    	if (del_index_arr[i].indexOf("del")>-1){
    		var del_index=del_index_arr[i].replace("del","");
    		console.log(del_index);
    	}
    }

	var del_query="DELETE FROM parkinglots_general WHERE id="+del_index;
	
	con_pool.getConnection(function(err, dbconnection){
		if (err) throw err;
		dbconnection.query(del_query, function(err, result){
			if(err){
				console.log('[query]-:'+err);
				res.send("operation failed!<br><a href='checking'>Go back to checking page</a>");
			}else{
				res.send("you have deleted a record!<br><a href='checking'>Go back to checking page</a>");

			}
		dbconnection.release();
		})
	});
});

//edit the parking lots information

router.get("/edit", function(req, res, next){
   var edit_index_arr=req.query.parking_name_index;
   console.log(req.query);
       for (var i=0; i<edit_index_arr.length;i++){
    	        if (edit_index_arr[i].indexOf("edit")>-1){
    	        	    var edit_index=edit_index_arr[i].replace("edit","");
    		       
    	            }
            }
   var edit_query="SELECT * FROM parkinglots_general WHERE id="+edit_index;

    con_pool.getConnection(function(err, dbconnection){
    	if (err) throw err;
    	dbconnection.query(edit_query, function(err,result){
    		if(err){
    			console.log('[query]-:'+err);
				res.send("operation failed!");
    		}else{
    		
    			res.render("update",{data:result});
    			console.log(result);
    		}
    	dbconnection.release();
    	});
    });
});

//create a new parking lots record

router.get("/create", function(req, res, next){
	res.render("create");

});

//handle create a new parking lots request

router.get("/submit", function(req, res, next){ 
	//var admin_insert_query = "insert into parkinglots_general (`Name`,`Capacity`) values (null,?,?,?,?,?,?,?,?,?,?)"
	var admin_insert_query = "INSERT INTO parkinglots_general VALUES (null,?,?,?,?,?,?,?,?,?,?)"
	value = [req.query.p_name, req.query.p_nw,req.query.p_se, req.query.p_sw, req.query.p_ne,req.query.p_capacity,req.query.p_ypermmission,req.query.p_rpermmission,req.query.p_rl,req.query.p_ticket];
	
	con_pool.getConnection(function(err, dbconnection){
		if (err) throw err;
		dbconnection.query(admin_insert_query,value, function(err, result){
			if(err){
				console.log('[query]-:'+err);
				res.send("operation failed!<br><a href='checking'>Go back to checking page</a>");
			}else{
				res.send("you have added a new record!<br><a href='checking'>Go back to checking page</a>");

			}
		dbconnection.release();
		})
	});
});


//handle edit request

router.get("/edit_submit", function(req, res, next){   
	//var admin_insert_query = "insert into parkinglots_general (`Name`,`Capacity`) values (null,?,?,?,?,?,?,?,?,?,?)"
	var admin_update_query = "UPDATE parkinglots_general SET `name`=?, `CoorNW`=?, `CoorSE`=?, `CoorSW`=?, `CoorNe`=?, `Capacity`=?, `yellow_permission`=?, `red_permission`=?, `represent_loc`=?, `tickets`=? WHERE `id`=?";
	value = [req.query.p_name, req.query.p_nw,req.query.p_se, req.query.p_sw, req.query.p_ne,req.query.p_capacity,req.query.p_ypermmission,req.query.p_rpermmission,req.query.p_rl,req.query.p_ticket,req.query.p_id];
	console.log(req.query);
	con_pool.getConnection(function(err, dbconnection){
		if (err) throw err;
		dbconnection.query(admin_update_query,value, function(err, result){
			if(err){
				console.log('[query]-:'+err);
				res.send("operation failed!<br><a href='checking'>Go back to checking page</a>");
			}else{
				res.send("you have updated a record!<br><a href='checking'>Go back to checking page</a>");

			}
		dbconnection.release();
		})
	});
});




module.exports = router;
