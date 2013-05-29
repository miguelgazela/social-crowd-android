<?php
require_once('database.php');
require 'Slim/Slim.php';
$app = new Slim();
$app->get('/events/:id', 'getEvent');
$app->delete('/events/:id', 'deleteEvent');
$app->get('/events', 'getEvents');
$app->post('/events', 'createEvent');
$app->post('/sessions', 'setSession');
$app->delete('/sessions/:id', 'deleteSession');

//$app->get('/subscriptions/:id', 'getSub');
$app->post('/subscriptions', 'createSub');
$app->delete('/subscriptions/:id', 'deleteSub');

$app->post('/votes', 'createVote');
$app->delete('/votes/:id', 'deleteVote');

$app->get('/comments', 'getComments');
$app->post('/comments', 'createComment');
$app->get('/comments/:id', 'getComment');
$app->delete('/comments/:id', 'deleteComment');
$app->put('/comments/:id', 'modifyComment');

$app->post('/ratings', 'createRating');
$app->delete('/ratings/:id', 'deleteRating');


function getRating($id){
	global $db;
	$query = "select * from rating where id = ?";
	$result = $db->prepare($query);
	$result->execute(array($id));
	$result = $result->fetch();
	echo json_encode((array("result" => "success", "data" =>$result)));
}

function createRating(){
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->post();
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select id from social_user where session = ? and id not in(select social_user from rating where eventid=?)");
		$result->execute(array($_SERVER['HTTP_SESSION_ID'],$data['eventid']));
		$user = $result->fetch();
		if(!empty($user)){
			$result = $db->prepare("insert into rating (id,value,eventid,social_user) values(default,?,?,?) returning id");
			$result->execute(array($data['value'],$data['eventid'],$user['id']));
			$result = $result->fetch();
			getRating($result['id']);
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function deleteRating($id){
	global $db;
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select id from social_user where session = ?");
		$result->execute(array($_SERVER['HTTP_SESSION_ID']));
		$user = $result->fetch();
		if(!empty($result)){
			$result = $db->prepare("delete from rating where id=? and social_user=?");
			$result->execute(array($id,$user['id']));
			$result = $result->fetch();
			echo json_encode(array("result" => "success"));
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function getComments(){
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->get();
	$args = array();
	$query = "select * from comment where id > 0";
	if(!empty($data['created_at'])){
		$query .=" and created_at > ?";
		$args[] = $data['created_at'];
	}
	
	if(!empty($data['eventid'])){
		$query .=" and eventid = ?";
		$args[] = $data['eventid'];
	}
	
	$result = $db->prepare($query);
	$result->execute($args);
	$result = $result->fetchAll();
	echo json_encode((array("result" => "success", "data" =>$result)));
}


function getComment($id){
	global $db;
	$query = "select * from comment where id = ?";
	$result = $db->prepare($query);
	$result->execute(array($id));
	$result = $result->fetch();
	echo json_encode((array("result" => "success", "data" =>$result)));
}

function createComment(){
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->post();
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select id from social_user where session = ?");
		$result->execute(array($_SERVER['HTTP_SESSION_ID']));
		$id = $result->fetch();
		if(!empty($result)){
			$result = $db->prepare("insert into comment (id,input,created_at,eventid,social_user) values(default,?,?,?,?) returning id");
			$result->execute(array($data['input'],$data['created_at'],$data['eventid'],$id['id']));
			$result = $result->fetch();
			getComment($result['id']);
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function modifyComment($id){
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->put();
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select id from social_user where session = ?");
		$result->execute(array($_SERVER['HTTP_SESSION_ID']));
		$user_id = $result->fetch();
		if(!empty($result)){
			$result = $db->prepare("update comment set input = ? where id=? and social_user =?");
			$result->execute(array($data['input'],$id,$user_id['id']));
			echo getComment($id);
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function deleteComment($id){
	global $db;
	$request = Slim::getInstance()->request();
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select id from social_user where session = ?");
		$result->execute(array($_SERVER['HTTP_SESSION_ID']));
		$user_id = $result->fetch();
		if(!empty($result)){
			$result = $db->prepare("delete from comment where social_user=? and id=?");
			$result->execute(array($user_id['id'],$id));
			echo json_encode(array("result" => "success"));
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}


function getVote($id){
	global $db;
	$result = $db->prepare("select * from vote where id = ?");
	$result->execute(array($id));
	echo json_encode(array("result" => "success", "data" => $result->fetch()));
}

function createVote(){
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->post();
	if(isset($_SERVER['HTTP_SESSION_ID'])){
			$result = $db->prepare("select id from social_user where session = ?");
			$result->execute(array($_SERVER['HTTP_SESSION_ID']));
			$user = $result->fetch();
		if(!empty($result)){
			try {
				$result = $db->prepare("insert into vote (id, type,comment,social_user) values(DEFAULT,?,?,?) returning id");
				$result->execute(array($data['type'],$data['comment'],$user['id']));
				$result = $result->fetch();
			}catch (PDOException $e) {
				echo json_encode(array("result" => "error", "data" => "already voted!"));
				die();
			}	
			getVote($result['id']);
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	
}

function deleteVote($id){
	global $db;
	if(isset($_SERVER['HTTP_SESSION_ID'])){
			$result = $db->prepare("select id from social_user  where session = ?");
			$result->execute(array($_SERVER['HTTP_SESSION_ID']));
			$user_id = $result->fetch();
		if(!empty($user_id)){
				$result = $db->prepare("delete from vote where id=? and social_user=?");
				$result->execute(array($id,$user_id['id']));
				echo json_encode(array("result" => "success"));
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function getSub($id){
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->get();
	$result = $db->prepare("select * from subscription where id = ?");
	$result->execute(array($id));
	$result = $result->fetch();
	if(!empty($result))
		echo json_encode(array("result" => "success","data"=> $result));
	else echo json_encode(array("result" => "error", "data" => "subscription not found!")); 	
}

function createSub(){
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->post();
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select id from social_user where id = ? and session = ?");
	    $result->execute(array($data['user_id'],$_SERVER['HTTP_SESSION_ID']));
		$result = $result->fetch();
		if(!empty($result)){
			$result = $db->prepare("select id from subscription where eventid = ? and user_id = ?");
			$result->execute(array($data['eventid'],$data['user_id']));
			$result = $result->fetch();	
			if(empty($result)){
				$result = $db->prepare("insert into subscription (id,user_id,created_at,eventid) values(DEFAULT,?,?,?) returning id");
				$result->execute(array($data['user_id'],$data['created_at'],$data['eventid']));
				$id = $result->fetch();
				getSub($id['id']);
			}
			else echo json_encode(array("result" => "error", "data" => "subscription already made"));
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}


function deleteSub($id){
	global $db;
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select id from subscription WHERE id =? and id in(select subscription.id from subscription,social_user where user_id = social_user.id and social_user.session = ?)");
	    $result->execute(array($id,$_SERVER['HTTP_SESSION_ID']));
		$result = $result->fetch();
		if(!empty($result)){
			$result = $db->prepare("delete from subscription where id =?");
	    $result->execute(array($id));
		$result = $result->fetch();
			echo json_encode(array("result" => "success"));
		}
		else echo json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function getEvent($id) {
	global $db;
	$result = $db->prepare("select * from event where id=?");
	$result->execute(array($id));
    $result = $result->fetch();
	if(!empty($result)){
		$type = $db->prepare("select * from private where id=?");
	    $type->execute(array($id));
        $type = $type->fetch();
		if(!empty($type))
			 $result['type'] = "private";
		
 	$type = $db->prepare("select * from public where id=?");
	$type->execute(array($id));
    $type = $type->fetch();		
	if(!empty($type))
			 $result['type'] = "public";
	else  $result['type'] = "geolocated";
	
	$location = $db->prepare("select * from location where id=?");
	$location->execute(array($result['location']));
    $location = $location->fetch();	
	
	$gps = $db->prepare("select * from gps where id=?");
	$gps->execute(array($location['gps']));
    $gps = $gps->fetch();		 
	if(empty($gps))
		$gps = NULL;
	$tags = $db->prepare("select name from tag where eventid=?");
	$tags->execute(array($id));
    $tags = $tags->fetchAll();
    $new_tags = array();
	foreach($tags as $tag){
		$new_tags[] = $tag['name'];
	}
	$subs = $db->prepare("select id, eventid, user_id,created_at from subscription where eventid=?");
	$subs->execute(array($id));
	$subs = $subs->fetchAll();
	$rating = $db->prepare("select avg(value) from rating where eventid=?");
	$rating->execute(array($id));
	$rating = $rating->fetch();
	$ratings = $db->prepare("select social_user,value, id from rating where eventid=?");
	$ratings->execute(array($id));
	$ratings = $ratings->fetchAll();		
	$comments = $db->prepare("select * from comment where eventid=?");
	$comments->execute(array($id));
    $comments = $comments->fetchAll();
	$new_comments = array();
	foreach ($comments as &$comment) {
		$upvotes = $db->prepare("select id, social_user from vote where id=? and type=1");
		$upvotes->execute(array($comment['id']));
		$upvotes = $upvotes->fetchAll();

		$downvotes = $db->prepare("select id, social_user from vote where id=? and type=-1");
		$downvotes->execute(array($comment['id']));
		$downvotes = $downvotes->fetchAll();
		$new_comment = array("id" => $comment['id'],"social_user" => $comment['social_user'],"eventid" => $comment['eventid'], "input" => $comment['input'] ,"created_at" => $comment[	'created_at'], "upvotes" => $upvotes, "downvotes" => $downvotes);
			$new_comments[] = $new_comment;
	}
	
	echo json_encode(array("result" => "success", "data" => array("type" => $result['type'], "id" =>$result['id'], "author_id" =>$result['author_id'], "name" =>$result['name'], "description" =>$result['description'], "start_date" =>$result['start_date'],"end_date" =>$result['end_date'],"location" =>array("id" => $location['id'],"name" => $location['name'],'gps'=>$gps) , "tags" =>$new_tags, "category" =>$result['category'], "rating" =>$rating['avg'], "ratings" => $ratings,  "subscriptions" => $subs, "comments" => $new_comments)));
	}
	else echo json_encode(array("result" => "error", "data" => "event not found!"));
}


function getEvents() {
	global $db;
	$args = array();
	$query = "select event.* from ";
	$tables = "event";
	$cond = " where event.id>0";
	$request = Slim::getInstance()->request();
	$data = $request->get();
	if(!empty($data)){
		if(!empty($data['owner_id'])){
			$cond .= " and event.author_id = ?";
			$args[] = $data['owner_id'];
		}
		if(!empty($data['subscriber_id'])){
			$tables .= ",subscription";
			$cond .= " and subscription.user_id = ? and subscription.eventid = event.id";
			$args[] = $data['subscriber_id'];
		}
		
		if(!empty($data['category'])){
			$cond .= " and event.category = ?";
			$args[] = $data['category'];
		}
		
		if(!empty($data['name'])){
			$cond .= " and event.name = ?";
			$args[] = $data['name'];
		}
		
		if(!empty($data['tags'])){
			$tags = json_decode($data['tags'],true);
			$cond.= " and event.id in (select event.id from event where  event.id not in ("; 

			$first = true;
			foreach($tags['tags'] as $tag){
				if($first){
					$cond .= "select event.id from event where ('".$tag."',event.id) not in (select name, eventid from tag)";
					$first = false;
				}
				else{
					$cond .= " or('".$tag."',event.id) not in (select name, eventid from tag)";
				}
			}
			$cond.="))";
		}
		
		if(!empty($data['type'])){
			if($data['type'] == "public"){
				$tables.=",public";
				$cond .= " and event.id = public.id"; 
			}
			
			if($data['type'] == "private"){
				$tables.=",private";
				$cond .= " and event.id = private.id"; 
			}
			
			if($data['type'] == "geolocated"){
				$tables.=",geolocated";
				$cond .= " and event.id = geolocated.id"; 
			}
		}
		
	}
	
	$query.=$tables . $cond;
	$result = $db->prepare($query);
	$result->execute($args);

    $result = $result->fetchAll();
	$events = array();
	foreach($result as $event){
		$type = $db->prepare("select * from private where id=?");
	    $type->execute(array($event['id']));
        $type = $type->fetch();
		if(!empty($type))
			 $event['type'] = "private";
		
 $type = $db->prepare("select * from public where id=?");
	    $type->execute(array($event['id']));
        $type = $type->fetch();		
	if(!empty($type))
			 $event['type'] = "public";
	else  $event['type'] = "geolocated";
	
	$location = $db->prepare("select * from location where id=?");
	$location->execute(array($event['location']));
    $location = $location->fetch();			 
	
	$gps = $db->prepare("select * from gps where id=?");
	$gps->execute(array($location['gps']));
    $gps = $gps->fetch();
	if(empty($gps))
		$gps = NULL;
	$tags = $db->prepare("select name from tag where eventid=?");
	    $tags->execute(array($event['id']));
        $tags = $tags->fetchAll();
		$new_tags = array();
		foreach($tags as $tag){
		$new_tags[] = $tag['name'];	
		
}	$rating = $db->prepare("select avg(value) from rating where eventid=?");
	    $rating->execute(array($event['id']));
        $rating = $rating->fetch();
		
	$events[] = array("type" => $event['type'], "id" =>$event['id'], "author_id" =>$event['author_id'], "name" =>$event['name'], "description" =>$event['description'], "start_date" =>$event['start_date'],
	"end_date" =>$event['end_date'],"location" =>array("id" => $location['id'],"name" => $location['name'],'gps'=>$gps), "tags" =>$new_tags, "category" =>$event['category'], "rating" =>$rating['avg']);
	}
	echo json_encode(array("result" => "success", "data" => $events));
}




function createEvent() {
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->post();
	if(isset($_SERVER['HTTP_SESSION_ID'])){
	 //public static DetailedEvent createEvent(type, name, description, start_date, end_date,  location, tags, category)
		 $user = $db->prepare("select id from social_user where session = ?");
		 $user->execute(array($_SERVER['HTTP_SESSION_ID']));
		 $user = $user->fetch();
		 if(empty($user['id'])){
			 echo json_encode(array("result" => "error", "data" => "unauthorized"));
			 die();
		 }	
		 $user = $user['id'];
		 $result = $db->prepare("insert into category values(?)");
		 $result->execute(array($data['category']));
		 
		 $location = json_decode($data['location'],true);
		 if(isset($location['gps'])){
			$gps = json_decode($location['gps'],true);
			$result = $db->prepare("insert into gps (id,latitude,longitude) values(DEFAULT,?,?) returning id");
			$result->execute(array($gps['latitude'], $gps['longitude']));
			$gps = $result->fetch();
			 
			$result = $db->prepare("insert into location values(DEFAULT,?,?) returning id");
			$result->execute(array($location['name'], $gps['id']));
			$result = $result->fetch();
			$location = $result['id'];
		 }
		 else{
			$result = $db->prepare("insert into location (id,name) values(DEFAULT,?) returning id");
			$result->execute(array($location['name']));
			$result = $result->fetch();
			$location = $result['id'];
		 }
		 
		 $result = $db->prepare("insert into event (id,name,description,start_date,end_date,category,author_id,location) values(DEFAULT,?,?,?,?,?,?,?) returning id");
		 $result->execute(array($data['name'], $data['description'], $data['start_date'] , $data['end_date'], $data['category'], $user, $location));
		 $result = $result->fetch();
		 $id = $result['id'];
		if(!empty($data['tags'])){
			$tags = json_decode($data['tags'],true);
			foreach($tags['tags'] as $tag){
				$result = $db->prepare("insert into tag values(?,?)");
				$result->execute(array($tag,$id));
			}
		}
		switch ($data['type']) {
		case "public":
			$query = "insert into public values (?)";
			break;
		case "private":
			$query = "insert into private values (?)";
			break;
		case "geolocated":
			$query = "insert into geolocated values (?)";
		  	break;
		default:
			echo json_encode(array("result" => "error", "data" => "no event type"));
			die();
			break;
		}
		$result = $db->prepare($query);
		$result->execute(array($id));
		getEvent($id);
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function deleteEvent($id) {
	global $db;
	if(isset($_SERVER['HTTP_SESSION_ID'])){
		$result = $db->prepare("select event.id from event where event.id =? and id in(select event.id from event,social_user where author_id = social_user.id and social_user.session = ?)");
	    $result->execute(array($id,$_SERVER['HTTP_SESSION_ID']));
		$result = $result->fetch();
		if(!empty($result)){
			$result = $db->prepare("delete from event where event.id =?");
	    $result->execute(array($id));
		$result = $result->fetch();
			echo json_encode(array("result" => "success"));
		}
		else json_encode(array("result" => "error", "data" => "unauthorized"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}


function setSession() {
	$session = session_id();
	global $user_id;
	global $db;
	$request = Slim::getInstance()->request();
	$data = $request->post();
	if(user_signin($data)){
		$result = $db->prepare("insert into social_user (id) values(?)");
		$result->execute(array($user_id));
		$result = $db->prepare("update social_user set session = ? where id = ?");
		$result->execute(array($session,$user_id));
		echo json_encode(array("result" => "success", "data" => array("sid" => $session, "user_id" => $user_id)));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function deleteSession($id) {
	global $db;
	if(user_signout()){
		$result = $db->prepare("update social_user set session=null where session=?");
	    $result->execute(array($id));
		echo json_encode(array("result" => "success"));
	}
	else echo json_encode(array("result" => "error", "data" => "unauthorized"));
}

function user_signin($info){
	global $user_id;
	$url = 'http://api.quickblox.com/login.json';
	$data = array('login' => $info['login'], 'password' => $info['password']);
	$curlHandle = curl_init();  
	curl_setopt($curlHandle, CURLOPT_TIMEOUT, 10);  
    curl_setopt($curlHandle, CURLOPT_URL, $url);  
    curl_setopt($curlHandle, CURLOPT_RETURNTRANSFER, true);  
    curl_setopt($curlHandle, CURLOPT_HTTPHEADER, array ("QuickBlox-REST-API-Version: 0.1.0","QB-Token: " . $_SERVER['HTTP_TOKEN']));
    curl_setopt($curlHandle, CURLOPT_POSTFIELDS, http_build_query($data));  
    curl_setopt($curlHandle, CURLOPT_POST, 1);
	$result = curl_exec($curlHandle);
	$http_status = curl_getinfo($curlHandle, CURLINFO_HTTP_CODE);
	if($http_status == 202){
		$user = json_decode($result,true);
		$user_id = $user['user']['id'];
		return true;
	}
	else return false;
}

function user_signout(){
	global $user_id;
	$url = 'http://api.quickblox.com/login.json';
	$curlHandle = curl_init();  
	curl_setopt($curlHandle, CURLOPT_TIMEOUT, 10);  
    curl_setopt($curlHandle, CURLOPT_URL, $url);  
    curl_setopt($curlHandle, CURLOPT_RETURNTRANSFER, true);  
    curl_setopt($curlHandle, CURLOPT_HTTPHEADER, array ("QuickBlox-REST-API-Version: 0.1.0","QB-Token: " . $_SERVER['HTTP_TOKEN']));
    curl_setopt($curlHandle, CURLOPT_CUSTOMREQUEST, 'DELETE');
	curl_exec($curlHandle);
	$http_status = curl_getinfo($curlHandle, CURLINFO_HTTP_CODE);
	if($http_status == 200)
		return true;
	else return false;
}

$app->run();

?>
